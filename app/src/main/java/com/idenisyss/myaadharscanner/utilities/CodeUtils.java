package com.idenisyss.myaadharscanner.utilities;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.idenisyss.myaadharscanner.activities.GenerateQRorBarActivity;

import java.io.ByteArrayOutputStream;
import java.util.EnumMap;
import java.util.Map;

public class CodeUtils {

    private static final String TAG_NAME = GenerateQRorBarActivity.class.getName();
    public static final int width = 300; // Width of the QR code image
    public static final int height = 300; // Height of the QR code image
    public static final int width2 = 450; // Width of the QR and Bar code image
    public static final int height2 = 150; // Height of the Bar code image

    //QR or Barcode Scan types...
    public static final BarcodeFormat[] QRformats = new BarcodeFormat[]{
            BarcodeFormat.QR_CODE,
            BarcodeFormat.DATA_MATRIX,
            BarcodeFormat.AZTEC,
            BarcodeFormat.MAXICODE,
            BarcodeFormat.PDF_417
    };
    public static final BarcodeFormat[] Barcodeformats = new BarcodeFormat[]{
            BarcodeFormat.CODE_128,
            BarcodeFormat.CODE_93,
            BarcodeFormat.CODE_39,
            BarcodeFormat.CODABAR,
            BarcodeFormat.EAN_8,
            BarcodeFormat.EAN_13,
            BarcodeFormat.ITF,
            BarcodeFormat.UPC_A,
            BarcodeFormat.UPC_E,
            BarcodeFormat.UPC_EAN_EXTENSION};
    // Generate a barcode from text
    public static BitMatrix generateBarcode(String text, int width, int height) throws Exception {
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // Character set

        Code128Writer code128Writer = new Code128Writer();
        BitMatrix bitMatrix = code128Writer.encode(text, BarcodeFormat.CODE_128, width, height, hints);

        return bitMatrix;
    }

    // Generate a QR code from text
    public static BitMatrix generateQRCode(String text, int width, int height) throws Exception {
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // Character set

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        return bitMatrix;
    }

    public static Bitmap ScanqrBarCodeString(Context context,String data_string, String codetype) {
        Log.d(TAG_NAME, "method : qrBarcodeString");
        Bitmap qrBitmap = null;
        BitMatrix bitMatrix;
        if (data_string != null && !data_string.isEmpty()) {
            try {
                if (codetype.equals(AppConstants.QR_CODE)) {
                    bitMatrix = CodeUtils.generateQRCode(data_string, width, height);
                } else {
                    bitMatrix = CodeUtils.generateBarcode(data_string, width2, height2);
                }
                // Convert the BitMatrix to a Bitmap
                qrBitmap = createBitmap(bitMatrix);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return qrBitmap;
    }

    public static byte[] convertBitTobyte(Bitmap scannedImage){
        // Convert the Bitmap to a byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        scannedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static  void  qrBarcodeString(Context context,String data_string, String codetype){
        Log.d(TAG_NAME,"method : qrBarcodeString");
        BitMatrix bitMatrix;
        if(data_string != null && !data_string.isEmpty()) {
            try {
                if(codetype.equals(AppConstants.QR_CODE)) {
                    bitMatrix = CodeUtils.generateQRCode(data_string, width, height);
                }
                else {
                    bitMatrix = CodeUtils.generateBarcode(data_string, width2, height2);
                }
                // Convert the BitMatrix to a Bitmap
                Bitmap qrBitmap = createBitmap(bitMatrix);
                byte[] bytesArray = convertBitTobyte(qrBitmap);

                Intent l = new Intent(context, GenerateQRorBarActivity.class);
                l.addFlags(FLAG_ACTIVITY_NEW_TASK);
                l.putExtra(AppConstants.GENERATE,bytesArray);
                l.putExtra(AppConstants.GENERATE_CODE_TYPE,codetype);
                l.putExtra(AppConstants.GENERATE_STRING,data_string);
                context.startActivity(l);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {

            Toast.makeText(context, AppConstants.PLEASE_ENTER_TEXT, Toast.LENGTH_SHORT).show();

        }
    }

    // Converts a BitMatrix to a Bitmap
    public static Bitmap createBitmap(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }

    // Attempt to decode the QR code
    public static String decodeQRCode(Bitmap bitmap) {
        try {
            int[] intArray = new int[bitmap.getWidth() * bitmap.getHeight()];
            // Copy pixel data from the Bitmap into the intArray
            bitmap.getPixels(intArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
            LuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), intArray);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

            MultiFormatReader reader = new MultiFormatReader();
            Result result = reader.decode(binaryBitmap);

            for (BarcodeFormat format : CodeUtils.QRformats) {
                if (result.getBarcodeFormat() == format) {
                    // Return the decoded QR code data
                    return result.getText();
                }
            }
            // The scanned code is not a QR code
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Attempt to decode the Barcode
        public static String decodeBarcode(Bitmap bitmap) {
            try {
                int[] intArray = new int[bitmap.getWidth() * bitmap.getHeight()];
                // Copy pixel data from the Bitmap into the intArray
                bitmap.getPixels(intArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
                LuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), intArray);
                BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

                MultiFormatReader reader = new MultiFormatReader();
                Result result = reader.decode(binaryBitmap);

                for (BarcodeFormat format : CodeUtils.Barcodeformats) {
                    if (result.getBarcodeFormat() == format) {
                        // Return the decoded barcode data
                        return result.getText();
                    }
                }
                // The scanned code is not a barcode
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
}
