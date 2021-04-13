package abhiandroid.com.jsonparsingexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // ArrayList for person names, email Id's and mobile numbers
    ArrayList<String> diseaseNames= new ArrayList<>();
    ArrayList<String> treatments= new ArrayList<>();
    ArrayList<String> medications= new ArrayList<>();
    ArrayList<String> plannedSurgeries= new ArrayList<>();
    ArrayList<ArrayList<SubDiseaseItem>> subDiseases = new ArrayList<ArrayList<SubDiseaseItem>>();
    String patientName ="";
    String patientCPR ="";

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

        try {
            // get JSONObject from JSON file
            JSONObject obj = new JSONObject(loadJSONFromAsset());

            // Get patient Name and CPR
            JSONObject patientDetail = obj.getJSONArray("patients").getJSONObject(0);
            // fetch name and cpr and store it in arraylist
            patientName =  patientDetail.getString("name");
            patientCPR =  patientDetail.getString("cpr");
            System.out.println(patientName);
            System.out.println(patientCPR);


            // fetch JSONArray named diseases
            JSONArray diseaseArray = obj.getJSONArray("diseases");
            // implement for loop for getting list data
            for (int i = 0; i < diseaseArray.length(); i++) {
                // create a JSONObject for fetching single user data
                JSONObject userDetail = diseaseArray.getJSONObject(i);
                // fetch data and store it in arraylist
                diseaseNames.add(userDetail.getString("diseaseName"));
                treatments.add(userDetail.getString("treatment"));
                medications.add(userDetail.getString("medication"));
                plannedSurgeries.add(userDetail.getString("plannedSurgery"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapterDisease customAdapter = new CustomAdapterDisease(MainActivity.this, diseaseNames, treatments, medications,plannedSurgeries, subDiseases);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

        // create toolbar with description of patient
        makeToolBar(patientName, patientCPR);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("patient_list.json");
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

    public void makeToolBar(String title, String subTitle){
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        System.out.println(title);
        System.out.println(subTitle);
        actionBar.setTitle(title);
        actionBar.setSubtitle(Html.fromHtml("<font color='#FFBF00'>"+ subTitle  + "</font>"));
    }
}
