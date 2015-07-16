package com.example.yinhangfeng.xmlresourceparsertest;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;

import com.example.commonlibrary.BaseTestActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class MainActivity extends BaseTestActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    static String [] TYPES = {
            "START_DOCUMENT",
            "END_DOCUMENT",
            "START_TAG",
            "END_TAG",
            "TEXT",
            "CDSECT",
            "ENTITY_REF",
            "IGNORABLE_WHITESPACE",
            "PROCESSING_INSTRUCTION",
            "COMMENT",
            "DOCDECL"
    };

    private static String eventTypeToString(int eventType) {
        return TYPES[eventType];
    }

    private void parse() {
        int id = this.getResources().getIdentifier("config", "xml", getPackageName());
        if(id == 0) {
            Log.e(TAG, "parse id==0");
            return;
        }
        XmlResourceParser parser = getResources().getXml(id);
        StringBuilder sb = new StringBuilder();
        int eventType;
        try {
            while((eventType = parser.next()) != XmlPullParser.END_DOCUMENT) {
                Log.i(TAG, "eventType: " + eventTypeToString(eventType));
                sb.setLength(0);
                sb.append("name=");
                try {
                    sb.append(parser.getName());
                } catch(Exception e) {
                    sb.append("ERROR");
                }

                sb.append(" attr:{");
                try {
                    for(int i = 0; i < parser.getAttributeCount(); ++i) {
                        sb.append(parser.getAttributeName(i)).append(" : ").append(parser.getAttributeValue(i)).append(", ");
                    }
                } catch(Exception e) {
                    sb.append("ERROR");
                }
                sb.append("}");

                sb.append(" text: ");
                try {
                    sb.append(parser.getText());
                } catch(Exception e) {
                    sb.append("ERROR");
                }

                sb.append(" prefix:");
                try {
                    sb.append(parser.getPrefix());
                } catch(Exception e) {
                    sb.append("ERROR");
                }

                switch(eventType) {
                case XmlPullParser.START_TAG:
                    break;
                case XmlPullParser.TEXT:
                    break;
                case XmlPullParser.END_TAG:
                    break;
                }
                Log.i(TAG, sb.toString());
            }
        } catch(XmlPullParserException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void test1() {
        parse();
    }
}
