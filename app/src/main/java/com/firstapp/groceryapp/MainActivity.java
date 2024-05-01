package com.firstapp.groceryapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity{
    Button button_capture;
    Button button_copy;
    TextView textview_data;

    Bitmap bitmap;


    private static final int REQUEST_CAMERA_CODE = 100;
    private TextView textView;
    private String stringURLEndPoint = "https://api.openai.com/v1/chat/completions";
    private String stringAPIKey = "API KEY";
    private String stringOutput = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_capture = findViewById(R.id.button_capture);
        button_copy = findViewById(R.id.button_copy);
        textview_data = findViewById(R.id.text_data);

        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    android.Manifest.permission.CAMERA
            }, REQUEST_CAMERA_CODE);
        }

        button_capture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(MainActivity.this);

            }
        });

        button_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StrictMode.ThreadPolicy gfgPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(gfgPolicy);

                String scanned_text = textview_data.getText().toString();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("model", "gpt-3.5-turbo");
                    JSONArray jsonArrayMessage = new JSONArray();
                    JSONObject jsonObjectMessage = new JSONObject();
                    jsonObjectMessage.put("role", "user");
                    jsonObjectMessage.put("content", "Using the grocery receipt given, place each item from the receipt in one of the following categories: Beverages, Bread/Bakery, Canned/Jarred Goods, Dairy, Dry/Baking Goods, Frozen Foods, Meat, Produce, Personal Care, and Other. Format each item in the form of: Category, item, price. If the price is unknown, put price as UNKNOWN. :" + scanned_text + " Do not include anything else in your response besides the items");
                    jsonArrayMessage.put(jsonObjectMessage);
                    jsonObject.put("messages", jsonArrayMessage);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        stringURLEndPoint, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String stringText = null;
                        try {
                            stringText = response.getJSONArray("choices")
                                    .getJSONObject(0)
                                    .getJSONObject("message")
                                    .getString("content");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        stringOutput = stringOutput + stringText;
                        //textView.setText(stringOutput);

                        // Store the API response as a string
                        String apiResponseAsString = stringOutput;
                        Log.d("API Response", apiResponseAsString);
                        GroceryReceipt groceryReceipt = new GroceryReceipt();
                        String[] lines = apiResponseAsString.split("\n");
                        for (String line : lines) {
                            String [] items = line.split(",");
                            groceryReceipt.addItem(new GroceryItem(items[1], items[2], items[0]));
                        }
                        Log.d("GroceryList:", groceryReceipt.toString());
                        Intent intent = new Intent(MainActivity.this, ReceiptNameActivity.class);
                        intent.putExtra("grocery_receipt", groceryReceipt);
                        startActivity(intent);
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> mapHeader = new HashMap<>();
                        mapHeader.put("Authorization", "Bearer " + stringAPIKey);
                        mapHeader.put("Content-Type", "application/json");
                        return mapHeader;
                    }
                    @Override
                    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                        return super.parseNetworkResponse(response);
                    }
                };
                int intTimeoutPeriod = 60000; // 60 seconds timeout duration defined
                RetryPolicy retryPolicy = new DefaultRetryPolicy(intTimeoutPeriod,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                jsonObjectRequest.setRetryPolicy(retryPolicy);
                Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    getTextFromImage(bitmap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    private void getTextFromImage(Bitmap bitmap){
        TextRecognizer recognizer = new TextRecognizer.Builder(this).build();
        if (!recognizer.isOperational()){
            Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
        }
        else{
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> textBlockSparseArray = recognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < textBlockSparseArray.size(); i++){
                TextBlock textBlock = textBlockSparseArray.valueAt(i);
                stringBuilder.append(textBlock.getValue());
                stringBuilder.append("\n");
            }
            textview_data.setText(stringBuilder.toString());
            button_capture.setText("Retake");
            button_copy.setVisibility(View.VISIBLE);
        }
    }
}


