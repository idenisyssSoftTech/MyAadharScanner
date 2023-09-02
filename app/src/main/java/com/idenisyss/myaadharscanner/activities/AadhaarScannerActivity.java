package com.idenisyss.myaadharscanner.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.idenisyss.myaadharscanner.R;
import com.idenisyss.myaadharscanner.XMLValidation;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.xml.sax.InputSource;

import java.io.StringReader;
import java.math.BigInteger;

import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class AadhaarScannerActivity extends AppCompatActivity {

    private TextView textview;
    private Button scanButton,updateButton;

    private static final int CAMERA_PERMISSION_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhaar_scanner);


       /* AadhaarParser aadhaarParser=AadhaarParser.getInstance(this);

        aadhaarParser.parse("YOUR_AADHAAR_CARD_SCAN_STRING", new OnAadhaarResponse() {
            @Override
            public void onAadhaarResponse(AadhaarUser aadhaarCard) {

                // aadhaarCard is your user model object
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("card", aadhaarCard);
                startActivity(intent);
            }
        });*/

        boolean isInternetAvailable = isInternetAvailable(this);
        if (isInternetAvailable) {
            // Internet is available
        } else {
            // Internet is not available
        }

        textview = findViewById(R.id.textview1);
        scanButton = findViewById(R.id.scanButton);
        updateButton = findViewById(R.id.updateButton);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(AadhaarScannerActivity.this);
                integrator.setPrompt("Place the code in the center of the square.\n         It will be scanned automatically.");
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("Scan Any Qr Code");
                integrator.setBeepEnabled(true);
                integrator.setCameraId(0);  // Use the rear camera
                integrator.initiateScan();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://uidai.gov.in/en/my-aadhaar/update-aadhaar.html#:~:text=You%20can%20update%20your%20Address%20online%20in%20Self%20Service%20Update,to%20visit%20Permanent%20Enrolment%20Center"));

                startActivity(browserIntent);
            }
        });

    }

    private boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Get the scanned result
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("AadharScannerActivity", "Cancelled scan");
            } else {
                String scannedData = result.getContents();
                String scanFormat = result.getFormatName();

                // Process the scanned data (Aadhar QR code contents)
                // Here you can extract the required details from the scanned data

                if(scannedData != null && !scannedData.isEmpty()){

                    if (XMLValidation.isStringValidXML(scannedData)) {
                        try {
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document document = builder.parse(new InputSource(new StringReader(scannedData)));

                            Element rootElement = document.getDocumentElement();

                            String uid = rootElement.getAttribute("uid");
                            String name = rootElement.getAttribute("name");
                            String gender = rootElement.getAttribute("gender");
                            String yob = rootElement.getAttribute("yob");
                            String co = rootElement.getAttribute("co");
                            String house = rootElement.getAttribute("house");
                            String street = rootElement.getAttribute("street");
                            String vtc = rootElement.getAttribute("vtc");
                            String po = rootElement.getAttribute("po");
                            String dist = rootElement.getAttribute("dist");
                            String subdist = rootElement.getAttribute("subdist");
                            String state = rootElement.getAttribute("state");
                            String pc = rootElement.getAttribute("pc");
                            System.out.println("UID: " + uid);
                            System.out.println("Name: " + name);
                            System.out.println("Gender: " + gender);
                            System.out.println("Year of Birth: " + yob);
                            System.out.println("Co: " + co);
                            System.out.println("House: " + house);
                            System.out.println("Street: " + street);
                            System.out.println("VTC: " + vtc);
                            System.out.println("PO: " + po);
                            System.out.println("District: " + dist);
                            System.out.println("Subdistrict: " + subdist);
                            System.out.println("State: " + state);
                            System.out.println("Postal Code: " + pc);

                            textview.setText(" Name : " + name + "\n Date of Birth : " + yob + "\n Gender : " + gender + "\n Aadhar No : " + uid
                                    + "\n Street : " + street + "\n Local : " + vtc + "\n Dist : " + dist + "\n State : " + state + "\n Pincode : " + pc);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Qrcode data is Not in XML -UTF-8", Toast.LENGTH_SHORT).show();
                        BigInteger bigInt = new BigInteger(scannedData);
                        Log.d("AadharScannerActivity", "Scanned: " + bigInt);
                        byte[] compressedData = bigInt.toByteArray();
                        byte delimiter = (byte) 255;

                        // Decompress the data
                        byte[] decompressedData = decompressData(compressedData);
                        Log.d("AadharScannerActivity", "decompressedData: " + decompressedData);
                        int currentIndex = 0;
                        while (currentIndex < decompressedData.length && decompressedData[currentIndex] != delimiter) {
                            System.out.println("Value: " + decompressedData[currentIndex]);
                            currentIndex++;
                        }


                       /* XmlPullParserFactory pullParserFactory;

                        try {
                            // init the parserfactory
                            pullParserFactory = XmlPullParserFactory.newInstance();
                            // get the parser
                            XmlPullParser parser = pullParserFactory.newPullParser();

                            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                            parser.setInput(new StringReader(scannedData));
                            aadharData = new AadharCard();

                            // parse the XML
                            int eventType = parser.getEventType();
                            while (eventType != XmlPullParser.END_DOCUMENT) {
                                if(eventType == XmlPullParser.START_DOCUMENT) {
                                    Log.d("Rajdeol","Start document");
                                } else if(eventType == XmlPullParser.START_TAG && DataAttributes.AADHAAR_DATA_TAG.equals(parser.getName())) {
                                    // extract data from tag
                                    //uid
                                    aadharData.setUuid(parser.getAttributeValue(null,DataAttributes.AADHAR_UID_ATTR));
                                    //name
                                    aadharData.setName(parser.getAttributeValue(null,DataAttributes.AADHAR_NAME_ATTR));
                                    //gender
                                    aadharData.setGender(parser.getAttributeValue(null,DataAttributes.AADHAR_GENDER_ATTR));
                                    // year of birth
                                    aadharData.setDateOfBirth(parser.getAttributeValue(null,DataAttributes.AADHAR_DOB_ATTR));
                                    // care of
                                    aadharData.setCareOf(parser.getAttributeValue(null,DataAttributes.AADHAR_CO_ATTR));
                                    // village Tehsil
                                    aadharData.setVtc(parser.getAttributeValue(null,DataAttributes.AADHAR_VTC_ATTR));
                                    // Post Office
                                    aadharData.setPostOffice(parser.getAttributeValue(null,DataAttributes.AADHAR_PO_ATTR));
                                    // district
                                    aadharData.setDistrict(parser.getAttributeValue(null,DataAttributes.AADHAR_DIST_ATTR));
                                    // state
                                    aadharData.setState(parser.getAttributeValue(null,DataAttributes.AADHAR_STATE_ATTR));
                                    // Post Code
                                    aadharData.setPinCode(parser.getAttributeValue(null,DataAttributes.AADHAR_PC_ATTR));

                                } else if(eventType == XmlPullParser.END_TAG) {
                                    Log.d("Rajdeol","End tag "+parser.getName());

                                } else if(eventType == XmlPullParser.TEXT) {
                                    Log.d("Rajdeol","Text "+parser.getText());

                                }
                                // update eventType
                                eventType = parser.next();
                            }

                            // display the data on screen
                            textview.setText(aadharData.getUuid());
                            return;
                        } catch (XmlPullParserException e) {
                            showErrorPrompt("Error in processing QRcode XML");
                            e.printStackTrace();
                            return;
                        } catch (IOException e) {
                            showErrorPrompt(e.toString());
                            e.printStackTrace();
                            return;
                        }*/
                    }

                    }
                }


            }

        }

    public void showErrorPrompt(String message){
       Log.d("Aadaract","Error"+message);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted
            } else {
                // Camera permission denied
            }
        }
    }

    public static byte[] decompressData(byte[] compressedData) {
        Inflater inflater = new Inflater();
        inflater.setInput(compressedData);

        byte[] decompressedBuffer = new byte[1024]; // Adjust buffer size as needed
        int decompressedLength;

        try {
            decompressedLength = inflater.inflate(decompressedBuffer);
        } catch (DataFormatException e) {
            e.printStackTrace();
            return new byte[0]; // Return empty array on failure
        } finally {
            inflater.end();
        }

        // Create a new array with the exact length of the decompressed data
        byte[] decompressedData = new byte[decompressedLength];
        System.arraycopy(decompressedBuffer, 0, decompressedData, 0, decompressedLength);

        return decompressedData;
    }


}