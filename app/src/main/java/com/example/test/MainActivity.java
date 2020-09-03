package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    //String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
              GetData();
            }
        });


        thread.start();

    }

    public void GetData(){
        try {

            final String NAMESPACE = "http://tempuri.org/";
            final String URL_SEARCH = "http://192.168.1.194:8089/SiteWeb.asmx";
            final String SOAP_ACTION = "http://tempuri.org/GetClient";
            final String OPERATION_NAME = "GetClient";

            SoapObject request = new SoapObject(NAMESPACE, OPERATION_NAME);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("login");
            pi.setValue("CROWN");
            pi.setType(String.class);
            request.addProperty(pi);
            pi = new PropertyInfo();
            pi.setName("password");
            pi.setValue("aO!dHsEd4");
            pi.setType(String.class);
            request.addProperty(pi);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(URL_SEARCH);
            SoapPrimitive response = null;


            httpTransport.call(SOAP_ACTION, envelope);
            response = (SoapPrimitive) envelope.getResponse();
            String json_res = response.toString();

            Log.e(TAG, "ok: " + json_res);


        } catch (Exception ex) {
            Log.e(TAG, "Exception: " + ex);
        }
    }
}


