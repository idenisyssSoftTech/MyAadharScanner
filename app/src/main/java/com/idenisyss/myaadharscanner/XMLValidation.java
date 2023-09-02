package com.idenisyss.myaadharscanner;

import android.graphics.BitmapFactory;
import android.util.Base64;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import java.io.StringReader;

public class XMLValidation {



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
}
