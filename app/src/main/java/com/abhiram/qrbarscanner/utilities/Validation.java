package com.abhiram.qrbarscanner.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Patterns;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Validation {
    private static final String TAG_NAME = Validation.class.getName();

    public static boolean isStringValidBitmap(String base64EncodedBitmap) {
        try {
            byte[] decodedBytes = Base64.decode(base64EncodedBitmap, Base64.DEFAULT);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true; // Only decode bounds, not the full bitmap
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length, options);

            // If options.outWidth and options.outHeight are non-zero, it's a valid bitmap
            return options.outWidth > 0 && options.outHeight > 0;
        } catch (Exception e) {
            return false; // Decoding or other exception occurred, not a valid bitmap
        }
    }

    public static boolean isStringValidXML(String xmlString) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlString));
            Document document = builder.parse(is);
            return true; // Parsing succeeded, so it's valid XML
        } catch (Exception e) {
            return false; // Parsing failed, so it's not valid XML
        }
    }

    public static File saveBitmapToFile(Context context, Bitmap bitmap) {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("temp_image", ".jpg",context.getCacheDir());
            FileOutputStream outStream = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }
    //validate PhoneNumber
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return Patterns.PHONE.matcher(phoneNumber).matches();
    }

    //Validate email address
    public static boolean isValidEmail(String email) {
        // Use Android's Patterns class to check if the email matches a valid pattern
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // validate URL
    public static boolean isValidURL(String urlString) {
        try {
            // Attempt to create a URL object from the input string
            new URL(urlString);
            return true; // URL is valid
        } catch (MalformedURLException e) {
            return false; // URL is not valid
        }
    }
}
