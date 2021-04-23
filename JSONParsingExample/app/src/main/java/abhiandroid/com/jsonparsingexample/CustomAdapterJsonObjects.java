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
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayouthospital, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        JSONArray innerArray;

        System.out.println("NOWWOWOWOWOWO");
        System.out.println(jsonArray);
        // implement for loop for getting list data
        for (int i = 0; i < jsonArray.length(); i++) {
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
        }

    }

    @Override
    public int getItemCount() {
        return arraySize;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private View border;
        TextView diseaseName, treatment, medication, plannedSurgery;// init the item view's
        LinearLayout myLinearLayout;
        @SuppressLint("ResourceType")
        public MyViewHolder(View itemView) {
            super(itemView);

            final float scale = context.getResources().getDisplayMetrics().density;

            // get the reference of item view's
            diseaseName = (TextView) itemView.findViewById(R.id.diseaseName);
            treatment = (TextView) itemView.findViewById(R.id.treatment);
            medication = (TextView) itemView.findViewById(R.id.medication);
            plannedSurgery = (TextView) itemView.findViewById(R.id.plannedSurgery);
            myLinearLayout= (LinearLayout) itemView.findViewById(R.id.linLayout);

            int size = 5; // total number of TextViews to add

            /*
            temp = new TextView(context);
            temp.setGravity(Gravity.CENTER | Gravity.BOTTOM);
            temp.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT));

            temp.setText("Alarm: " + i); //arbitrary task
            temp.setTextSize(20);
            temp.setTypeface(Typeface.DEFAULT_BOLD);
            */

            TextView temp;

            for (int i = 0; i < size; i++)
            {
                temp = new TextView(context);
                temp.setGravity(Gravity.LEFT | Gravity.CENTER);
                int textViewHeight = context.getResources().getDimensionPixelSize(R.dimen.textViewHeight);
                int textViewWidth = context.getResources().getDimensionPixelSize(R.dimen.textViewWidth);
                //temp.setLayoutParams(new RecyclerView.LayoutParams( 350, 100));
                temp.setLayoutParams(new RecyclerView.LayoutParams(textViewWidth,textViewHeight));

                temp.setBackgroundResource(android.R.color.holo_blue_bright);


                temp.setText("Alarm: " + i); //arbitrary task
                temp.setTextSize(20);
                temp.setTypeface(Typeface.DEFAULT_BOLD);
                //temp.setTextColor(0x000);

                // add the textview to the linearlayout
                myLinearLayout.addView(temp);

                int textViewBorder = context.getResources().getDimensionPixelSize(R.dimen.textViewborder);
                android.view.ViewGroup.LayoutParams params = myLinearLayout.getLayoutParams();
                border = new View(context);
                border.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,textViewBorder ));
                myLinearLayout.addView(border);
            }
        }
    }
    

}
