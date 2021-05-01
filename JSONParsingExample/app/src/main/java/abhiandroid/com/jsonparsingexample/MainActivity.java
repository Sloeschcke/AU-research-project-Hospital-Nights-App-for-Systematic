package abhiandroid.com.jsonparsingexample;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    String patientName ="";
    String patientCPR ="";
    private String backgroundColor;
    private String itemColor;
    private String toolbarColor;
    private String toolbarSubtitleColor;
    private String toolbarTitleColor;
    private String iconColor;
    private String clickableItemColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        JSONArray layer1 =  new JSONArray();
        try {
            // get JSONObject from JSON file
            JSONObject obj = new JSONObject(loadJSONFromAsset(getApplicationContext()));

            // Get patient Name and CPR
            JSONObject specificationDetails = obj.getJSONArray("specs").getJSONObject(0);
            // fetch name and cpr and store it in arraylist
            patientName = specificationDetails.getString("name");
            patientCPR = specificationDetails.getString("cpr");
            backgroundColor = specificationDetails.getString("backgroundColor");
            itemColor = specificationDetails.getString("itemColor");
            clickableItemColor = specificationDetails.getString("clickableItemColor");
            toolbarColor = specificationDetails.getString("toolbarColor");
            toolbarTitleColor = specificationDetails.getString("toolbarTitleColor");
            toolbarSubtitleColor = specificationDetails.getString("toolbarSubtitleColor");
            iconColor = specificationDetails.getString("iconColor");

            // fetch JSONArray named diseases
            layer1 = obj.getJSONArray("layer1");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapterJsonObjects customAdapter = new CustomAdapterJsonObjects(MainActivity.this, layer1, patientName,patientCPR, backgroundColor, itemColor, toolbarColor, toolbarTitleColor, toolbarSubtitleColor, iconColor, clickableItemColor);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
        // create toolbar with description of patient
        makeToolBar(patientName, patientCPR, toolbarColor, toolbarTitleColor, toolbarSubtitleColor);
    }

    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void makeToolBar(String title, String subTitle, String toolBarColor, String titleColor, String subtitleColor){
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle((Html.fromHtml("<font color=\"" + titleColor + " \">" + title + "</font>")));
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(toolBarColor));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setSubtitle(Html.fromHtml("<font color=\"" +subtitleColor+ "\">" + subTitle  + "</font>" ));

    }
}
