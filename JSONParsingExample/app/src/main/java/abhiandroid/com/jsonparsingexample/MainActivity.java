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

    // ArrayList for person names, email Id's and mobile numbers
    ArrayList<String> diseaseNames= new ArrayList<>();
    ArrayList<String> treatments= new ArrayList<>();
    ArrayList<String> medications= new ArrayList<>();
    ArrayList<String> plannedSurgeries= new ArrayList<>();
    ArrayList<ArrayList<SubDiseaseItem>> subDiseases = new ArrayList<ArrayList<SubDiseaseItem>>();

    ArrayList<ArrayList<String>> listListItem= new ArrayList<ArrayList<String>>();
    String patientName ="";
    String patientCPR ="";
    private String backgroundColor;
    private String itemColor;
    private String toolbarColor;
    private String toolbarSubtitleColor;
    private String toolbarTitleColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get the reference of RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //getSupportActionBar().hide();

        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        JSONArray jsonInnerArray = new JSONArray();
        String arrayName = "";
        JSONArray layer1 =  new JSONArray();
        try {
            // get JSONObject from JSON file
            JSONObject obj = new JSONObject(loadJSONFromAsset(getApplicationContext()));

            // Get patient Name and CPR
            JSONObject patientDetail = obj.getJSONArray("patients").getJSONObject(0);
            // fetch name and cpr and store it in arraylist
            patientName = patientDetail.getString("name");
            patientCPR = patientDetail.getString("cpr");
            backgroundColor = patientDetail.getString("backgroundColor");
            itemColor = patientDetail.getString("itemColor");
            toolbarColor = patientDetail.getString("toolbarColor");
            toolbarTitleColor = patientDetail.getString("toolbarTitleColor");
            toolbarSubtitleColor = patientDetail.getString("toolbarSubtitleColor");

            // fetch JSONArray named diseases
            layer1 = obj.getJSONArray("layer1");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapterJsonObjects customAdapter = new CustomAdapterJsonObjects(MainActivity.this, layer1, patientName,patientCPR, backgroundColor, itemColor, toolbarColor, toolbarTitleColor, toolbarSubtitleColor);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
        // create toolbar with description of patient
        makeToolBar(patientName, patientCPR, toolbarColor, toolbarTitleColor, toolbarSubtitleColor);
    }

    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("patient_list1.json");
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
        System.out.println(title);
        System.out.println(subTitle);
        actionBar.setTitle((Html.fromHtml("<font color=\"" + titleColor + " \">" + title + "</font>")));
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(toolBarColor));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setSubtitle(Html.fromHtml("<font color=\"" +subtitleColor+ "\">" + subTitle  + "</font>" ));

    }
}
