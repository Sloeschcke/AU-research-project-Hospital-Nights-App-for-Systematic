package abhiandroid.com.jsonparsingexample;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

public class DetailActivity extends AppCompatActivity {

    private String url;
    private String patientCPR;
    private String patientName;
    private String fileName;
    private String toolBarColor;
    private String titleColor;
    private String subtitleColor;
    private String iconColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        url = getIntent().getStringExtra("url");
        patientName = getIntent().getStringExtra("patientName");
        patientCPR = getIntent().getStringExtra("cpr");
        fileName = getIntent().getStringExtra("recordName");
        toolBarColor = getIntent().getStringExtra("toolbarColor");
        titleColor = getIntent().getStringExtra("toolbarTitleColor");
        subtitleColor = getIntent().getStringExtra("toolbarSubtitleColor");

        ImageView imageView = (ImageView) findViewById(R.id.image_view_detail);
        TextView fileView = (TextView) findViewById(R.id.file_name);

        Picasso.get().load(url).fit().centerInside().into(imageView);
        fileView.setText(fileName);

        String toolbarTitle = patientName + " - " + fileName;

        makeToolBar(toolbarTitle, patientCPR, toolBarColor, titleColor, subtitleColor);
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
}
