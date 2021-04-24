package abhiandroid.com.jsonparsingexample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;


public class CustomAdapterJsonObjects extends RecyclerView.Adapter<CustomAdapterJsonObjects.MyViewHolder> {

    private final String patientName;
    private final String cpr;
    private final String arrayName;
    private final int arraySize;
    JSONArray jsonArray;
    ArrayList<String> items;
    ArrayList<ArrayList<SubDiseaseItem>> subDiseases;

    Context context;

    public CustomAdapterJsonObjects(Context context, String arrayName, JSONArray jsonArray, String patientName, String cpr) {
        this.context = context;
        this.jsonArray= jsonArray;
        this.patientName = patientName;
        this.arrayName = arrayName;
        this.cpr = cpr;
        this.arraySize = jsonArray.length();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayouthospital, parent, false);

        int numberOfItems = 0;
        try {
            JSONObject innerObj = jsonArray.getJSONObject(0);
            numberOfItems =innerObj.length();
            System.out.println("!!!!!");
            System.out.println(innerObj);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyViewHolder vh = new MyViewHolder(v,numberOfItems ); // pass the view to View Holder
        return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        ArrayList<String> itemStrings = new ArrayList<>();
        int numberOfItems =0;
        try {
            int mCounter =0;
            JSONObject innerObj = jsonArray.getJSONObject(position);
            numberOfItems =innerObj.length();
            Iterator<String> keys = innerObj.keys();
            do {
                String keyValue = (String) keys.next();
                boolean keyValueIsObject= innerObj.getString(keyValue).substring(0,1).equals("[");
                if (keyValueIsObject) {
                    // use key as title
                    ( (TextView) holder.tv[mCounter ]).setText(keyValue);
                    mCounter  = mCounter+2;
                    //add onclick listener
                } else {
                    String txt = innerObj.getString(keyValue);
                    ( (TextView) holder.tv[mCounter]).setText(txt);
                    mCounter  =mCounter+2;
                }
            } while (keys.hasNext()) ;

        } catch (JSONException e) {
            e.printStackTrace();
        }


        // implement for loop for getting list data
        /*for (int i = 0; i < jsonArray.length(); i++) {
            try {
                // create a JSONObject for fetching single user data
                JSONObject innerObj = jsonArray.getJSONObject(i);
                Iterator<String> keys = innerObj.keys();
                do {
                    String keyValue = (String) keys.next();
                    boolean keyValueIsObject= innerObj.getString(keyValue).substring(0,1).equals("[");
                    if (keyValueIsObject) {
                        innerArray = innerObj.getJSONArray(keyValue);
                        System.out.println("innerArray");

                        continue;
                    } else {
                        System.out.println(keyValue);
                        holder.diseaseName.setText(keyValue);
                    }
                } while (keys.hasNext()) ;
            } catch(JSONException e){
                e.printStackTrace();
            }
        }*/

    }

    @Override
    public int getItemCount() {
        return arraySize;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private View borderTitle;
        private TextView titleView;
        private View border;
        TextView diseaseName, treatment, medication, plannedSurgery;// init the item view's
        LinearLayout myLinearLayout;
        View[] tv;

        @SuppressLint("ResourceType")
        public MyViewHolder(View itemView, int numberOfItems) {
            super(itemView);
            tv = new View[numberOfItems*2];
            int counter = 0;

            // get the reference of item view's
            diseaseName = (TextView) itemView.findViewById(R.id.diseaseName);
            treatment = (TextView) itemView.findViewById(R.id.treatment);
            medication = (TextView) itemView.findViewById(R.id.medication);
            plannedSurgery = (TextView) itemView.findViewById(R.id.plannedSurgery);
            myLinearLayout= (LinearLayout) itemView.findViewById(R.id.linLayout);

            int size = numberOfItems; // total number of TextViews to add
            int textViewBorder = context.getResources().getDimensionPixelSize(R.dimen.textViewborder);
            int textViewHeight = context.getResources().getDimensionPixelSize(R.dimen.textViewHeight);
            int textViewWidth = context.getResources().getDimensionPixelSize(R.dimen.textViewWidth);

            titleView = new TextView(context);
            titleView.setGravity(Gravity.LEFT| Gravity.CENTER);
            titleView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT));

            //titleView.setText("Alarm: title"); //arbitrary task
            titleView.setTextSize(20);
            titleView.setTextColor(Color.BLACK);
            titleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            myLinearLayout.addView(titleView);
            tv[counter] = titleView;
            counter ++;

            borderTitle = new View(context);
            borderTitle.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,textViewBorder ));
            myLinearLayout.addView(borderTitle);
            tv[counter] = borderTitle;
            counter ++;
            TextView textView;

            for (int i = 1; i < size; i++) // i=1 because tile view already added
            {
                //Create Item
                textView = new TextView(context);
                textView.setGravity(Gravity.LEFT | Gravity.CENTER);
                textView.setLayoutParams(new RecyclerView.LayoutParams(textViewWidth,textViewHeight));
                textView.setBackgroundResource(android.R.color.holo_blue_bright);
                //temp.setText("Alarm: " + i); //arbitrary task
                textView.setTextSize(20);
                textView.setTextColor(Color.BLACK);
                myLinearLayout.addView(textView);
                tv[counter] = textView;
                counter ++;
                //Create Border between items
                border = new View(context);
                border.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,textViewBorder ));
                myLinearLayout.addView(border);
                tv[counter] = border;
                counter ++;
            }
        }
    }
    

}
