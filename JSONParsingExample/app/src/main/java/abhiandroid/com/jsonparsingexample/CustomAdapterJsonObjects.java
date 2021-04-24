package abhiandroid.com.jsonparsingexample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class CustomAdapterJsonObjects extends RecyclerView.Adapter<CustomAdapterJsonObjects.MyViewHolder> {

    private final String patientName;
    private final String cpr;
    private final int arraySize;
    JSONArray jsonArray;
    ArrayList<String> items;
    ArrayList<ArrayList<SubDiseaseItem>> subDiseases;

    Context context;

    public CustomAdapterJsonObjects(Context context, JSONArray jsonArray, String patientName, String cpr) {
        this.context = context;
        this.jsonArray= jsonArray;
        this.patientName = patientName;
        this.cpr = cpr;
        this.arraySize = jsonArray.length();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayouthospital, parent, false);

        int numberOfItems = 0;
        try {
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject innerObj = jsonArray.getJSONObject(i);
                int innerLength =innerObj.length();
                if (innerLength > numberOfItems){
                    numberOfItems = innerLength;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyViewHolder vh = new MyViewHolder(v,numberOfItems ); // pass the view to View Holder
        return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        try {
            int mCounter =0;
            final JSONObject innerObj = jsonArray.getJSONObject(position);
            Iterator<String> keys = innerObj.keys();
            do {
                final String keyValue = (String) keys.next();
                boolean keyValueIsObject= innerObj.getString(keyValue).substring(0,1).equals("[");
                if (keyValueIsObject) {
                    // use key as title
                    TextView view = (TextView) holder.tv[mCounter ];
                    view.setText(keyValue);
                    mCounter  = mCounter+3;
                    //add onclick listener
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // display a toast with person name on item click
                            Toast.makeText(context, keyValue, Toast.LENGTH_SHORT).show();
                            JSONArray subArray = new JSONArray();
                            try {
                                subArray = innerObj.getJSONArray(keyValue);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(context, SubDiseaseActivity.class);
                            //intent.putStringArrayListExtra("subDiseases",new ArrayList<>(subDiseases.get(position)));
                            intent.putExtra("patientName", patientName);
                            intent.putExtra("cpr", cpr);
                            intent.putExtra("jsonArray", subArray.toString());
                            context.startActivity(intent);

                        }
                    });
                } else {
                    final String txt = innerObj.getString(keyValue);
                    if(txt.length() >3 && txt.substring(0,4).equals("http")){
                        ImageView imageView = (ImageView) holder.tv[mCounter+1];
                        imageView.setVisibility(View.VISIBLE);
                        Picasso.get().load(txt).fit().centerInside().into(imageView);
                        ((TextView) holder.tv[mCounter]).setVisibility(View.GONE); //hide textview

                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // display a toast with person name on item click
                                Intent intent = new Intent(context, DetailActivity.class);
                                //intent.putStringArrayListExtra("subDiseases",new ArrayList<>(subDiseases.get(position)));
                                intent.putExtra("url", txt);
                                intent.putExtra("patientName", patientName);
                                intent.putExtra("cpr", cpr);
                                Iterator<String> innerKeys = innerObj.keys();
                                try {
                                    intent.putExtra("recordName", innerObj.getString(innerKeys.next()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                context.startActivity(intent);

                            }
                        });
                        mCounter = mCounter + 3;
                    }else {
                        ((TextView) holder.tv[mCounter]).setText(txt);
                        mCounter = mCounter + 3;
                    }
                }
            } while (keys.hasNext()) ;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // hide views with no text
        for (int j = 0; j < holder.tv.length; j=j+3){
            TextView view = (TextView) holder.tv[j];
            boolean isEmptyTextView =view.getText().toString().equals("");
            if( isEmptyTextView ){
                view.setVisibility(View.GONE);
                if(j > 0){
                    ((View) holder.tv[j-1]).setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return arraySize;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgView;
        private int numberOfItems;
        private View borderTitle;
        private TextView titleView;
        private View border;
        LinearLayout myLinearLayout;
        View[] tv;

        @SuppressLint("ResourceType")
        public MyViewHolder(View itemView, int numberOfItems) {
            super(itemView);
            this.numberOfItems = numberOfItems;
            tv = new View[numberOfItems*3];
            int counter = 0;

            myLinearLayout = (LinearLayout) itemView.findViewById(R.id.linLayout);

            int size = numberOfItems; // total number of TextViews to add
            int textViewBorder = context.getResources().getDimensionPixelSize(R.dimen.textViewborder);
            int textViewHeight = context.getResources().getDimensionPixelSize(R.dimen.textViewHeight);
            int textViewWidth = context.getResources().getDimensionPixelSize(R.dimen.textViewWidth);
            int imageViewHeight= context.getResources().getDimensionPixelSize(R.dimen.imgViewHeight);

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

            //Add imageView
            imgView = new ImageView(context);
            imgView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,imageViewHeight));
            imgView.setVisibility(View.GONE);
            myLinearLayout.addView(imgView);
            tv[counter] = imgView;
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
                //Add imageView
                imgView = new ImageView(context);
                imgView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,imageViewHeight));
                imgView.setVisibility(View.GONE);
                myLinearLayout.addView(imgView);
                tv[counter] = imgView;
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
