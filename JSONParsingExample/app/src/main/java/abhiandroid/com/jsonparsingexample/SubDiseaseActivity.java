package abhiandroid.com.jsonparsingexample;

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
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SubDiseaseActivity extends AppCompatActivity {
    public int position;
    String patientName ="";
    String patientCPR ="";
    JSONArray jsonArray = new JSONArray();
    private String backgroundColor;
    private String itemColor;
    private String toolbarColor;
    private String toolbarTitleColor;
    private String toolbarSubtitleColor;
    private String iconColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_disease);
        Intent intent = getIntent();
        patientName= intent.getStringExtra("patientName");
        patientCPR= intent.getStringExtra("cpr");
        backgroundColor= intent.getStringExtra("backgroundColor");
        itemColor= intent.getStringExtra("itemColor");
        toolbarColor = intent.getStringExtra("toolbarColor");
        toolbarTitleColor = intent.getStringExtra("toolbarTitleColor");
        toolbarSubtitleColor = intent.getStringExtra("toolbarSubtitleColor");
        iconColor = intent.getStringExtra("iconColor");

        String jsonArrayString = intent.getStringExtra("jsonArray");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        try {
            jsonArray= new JSONArray(jsonArrayString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapterJsonObjects customAdapter = new CustomAdapterJsonObjects(SubDiseaseActivity.this, jsonArray, patientName,patientCPR,backgroundColor,itemColor, toolbarColor, toolbarTitleColor, toolbarSubtitleColor, iconColor);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

        makeToolBar(patientName,patientCPR,toolbarColor,toolbarTitleColor,toolbarSubtitleColor);

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
