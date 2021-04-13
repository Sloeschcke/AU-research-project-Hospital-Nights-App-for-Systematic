package abhiandroid.com.jsonparsingexample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SubDiseaseActivity extends AppCompatActivity {
    public ArrayList<SubDiseaseItem> subDiseases = new ArrayList<>();
    public int position;
    String patientName ="";
    String patientCPR ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_disease);
        position = getIntent().getIntExtra("position", 0);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);



        makeToolBar();


        try {
            // get JSONObject from JSON file
            JSONObject obj = new JSONObject(loadJSONFromAsset());

            // Get patient Name and CPR
            JSONObject patientDetail = obj.getJSONArray("patients").getJSONObject(0);
            // fetch name and cpr and store it in arraylist
            patientName =  patientDetail.getString("name");
            patientCPR =  patientDetail.getString("cpr");


            // fetch JSONArray named diseases
            JSONArray diseaseArray = obj.getJSONArray("diseases");
            // create a JSONObject for fetching single user data at position received from intent
            JSONObject userDetail = diseaseArray.getJSONObject(position);
            JSONArray subD = userDetail.getJSONArray("subDiseases");
            // fetch subDiseases and store it in arraylist
            for (int j = 0; j < subD.length(); j++) {
                JSONObject item = subD.getJSONObject(j);
                String subDisease = item.getString("subDisease");
                String imageUrl1 = item.getString("imageUrl1");
                String fileText1 = item.getString("fileText1");
                String imageUrl2 = item.getString("imageUrl2");
                String fileText2 = item.getString("fileText2");
                SubDiseaseItem subDiseaseItem = new SubDiseaseItem(subDisease, fileText1, imageUrl1, fileText2, imageUrl2);
                subDiseases.add(subDiseaseItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapterSubDisease customAdapter = new CustomAdapterSubDisease(SubDiseaseActivity.this, subDiseases, patientName, patientCPR);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
    }


    public void makeToolBar(){
        String patientName= "";
        String cpr ="";
        try {
            // get JSONObject from JSON file
            JSONObject obj = new JSONObject(loadJSONFromAsset());

            // Get patient Name and CPR
            JSONObject patientDetail = obj.getJSONArray("patients").getJSONObject(0);
            // fetch name and cpr and store it in arraylist
            patientName =  patientDetail.getString("name");
            cpr =  patientDetail.getString("cpr");
            } catch (JSONException e) {
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle(patientName);
        getSupportActionBar().setSubtitle(Html.fromHtml("<font color='#FFBF00'>"+ cpr  + "</font>"));
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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

}
