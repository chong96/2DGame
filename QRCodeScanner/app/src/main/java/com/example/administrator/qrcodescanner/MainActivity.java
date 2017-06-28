package com.example.administrator.qrcodescanner;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class MainActivity extends Activity implements ZXingScannerView.ResultHandler {
    public static final int CAMERA_REQUEST_CODE = 10;
    private ZXingScannerView mScanner;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public class JSONTask extends AsyncTask<String,String,String> { //read JSON input

        @Override
        protected String doInBackground(String...params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String finalJson = "";

            if (Patterns.WEB_URL.matcher(params[0]).matches()) { //cehck to see if it's a website
                Intent visitUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(params[0]));
                startActivity(visitUrl);

            } else {

                try { //try to run while catching exceptions
                    URL url = new URL(params[0]);
                    connection = (HttpURLConnection) url.openConnection(); //open URL
                    connection.connect();

                    InputStream stream = connection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    finalJson = buffer.toString();
                    //JSONObject parentObject = new JSONObject(finalJson);
                    //JSONArray parentArray = new JSONArray(finalJson);

                    return buffer.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    //} catch (JSONException e) {
                    //e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
                return null;

        }


        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder
                    .setMessage("Code scanned!")
                    .setCancelable(false)
                    .setPositiveButton("Scan another code", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            invokeCamera(); //restart camera if they want to scan again
                        }
                    })
                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            finish();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onClick(View v) {

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) { //check if they gave camera permissions
            invokeCamera();
        } else {
            String[] permissionsRequested = {Manifest.permission.CAMERA};
            requestPermissions(permissionsRequested, CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                invokeCamera();
            } else {
                Toast.makeText(this, R.string.unable_to_invoke, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void invokeCamera() { //start camera
        mScanner = new ZXingScannerView(this);
        setContentView(mScanner);
        mScanner.setResultHandler(this);
        mScanner.startCamera();

    }

    protected void onPause() {
        super.onPause();
        mScanner.stopCamera();
    }


    @Override
    public void handleResult(Result result) { //send the url to JSONTask method
        String url = result.getText();
        new JSONTask().execute(url);

    }
}
