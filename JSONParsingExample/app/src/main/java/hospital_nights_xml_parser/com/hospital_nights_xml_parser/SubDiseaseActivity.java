package hospital_nights_xml_parser.com.hospital_nights_xml_parser;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;

import hospital_nights_xml_parser.com.hospital_nights_xml_parser.R;

public class SubDiseaseActivity extends AppCompatActivity {
    String patientName ="";
    String patientCPR ="";
    JSONArray jsonArray = new JSONArray();
    private String backgroundColor;
    private String itemColor;
    private String toolbarColor;
    private String toolbarTitleColor;
    private String toolbarSubtitleColor;
    private String iconColor;
    private String lastLayerString;
    private String clickableItemColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_disease);
        Intent intent = getIntent();
        lastLayerString = intent.getStringExtra("objName");
        patientName= intent.getStringExtra("patientName");
        patientCPR= intent.getStringExtra("cpr");
        backgroundColor= intent.getStringExtra("backgroundColor");
        itemColor= intent.getStringExtra("itemColor");
        toolbarColor = intent.getStringExtra("toolbarColor");
        toolbarTitleColor = intent.getStringExtra("toolbarTitleColor");
        toolbarSubtitleColor = intent.getStringExtra("toolbarSubtitleColor");
        iconColor = intent.getStringExtra("iconColor");
        clickableItemColor = intent.getStringExtra("clickableItemColor");

        String jsonArrayString = intent.getStringExtra("jsonArray");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        // erase any "#" char from string
        String toolBarTitle ="";
        int idx = lastLayerString.indexOf("#");
        if(idx != -1){
            String withoutIconText = lastLayerString.substring(0,idx-1);
            toolBarTitle = patientName + " - " + withoutIconText;
        } else  toolBarTitle = patientName + " - " + lastLayerString;


        try {
            jsonArray= new JSONArray(jsonArrayString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapterJsonObjects customAdapter = new CustomAdapterJsonObjects(SubDiseaseActivity.this, jsonArray, patientName,patientCPR,backgroundColor,itemColor, toolbarColor, toolbarTitleColor, toolbarSubtitleColor, iconColor, clickableItemColor);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

        makeToolBar(toolBarTitle,patientCPR,toolbarColor,toolbarTitleColor,toolbarSubtitleColor);

    }

    public void makeToolBar(String title, String subTitle, String toolBarColor, String titleColor, String subtitleColor){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle((Html.fromHtml("<font color=\"" + titleColor + " \">" + title + "</font>")));
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(toolBarColor));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setSubtitle(Html.fromHtml("<font color=\"" +subtitleColor+ "\">" + subTitle  + "</font>" ));
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


}
