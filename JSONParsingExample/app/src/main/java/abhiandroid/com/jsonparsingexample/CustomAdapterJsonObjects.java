package abhiandroid.com.jsonparsingexample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.content.res.AppCompatResources;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class CustomAdapterJsonObjects extends RecyclerView.Adapter<CustomAdapterJsonObjects.MyViewHolder> {

    private final String patientName;
    private final String cpr;
    private final int arraySize;
    private final String backgroundColor;
    private final String itemColor;
    private final String toolbarSubtitleColor;
    private final String toolbarColor;
    private final String toolbarTitleColor;
    private final String iconColor;
    JSONArray jsonArray;
    Context context;

    public CustomAdapterJsonObjects(Context context, JSONArray jsonArray, String patientName, String cpr, String mBackgroundColor, String mItemColor, String mToolBarColor, String mToolbarTitleColor, String mToolbarSubtitleColor, String itemColor) {
        this.context = context;
        this.jsonArray= jsonArray;
        this.patientName = patientName;
        this.cpr = cpr;
        this.arraySize = jsonArray.length();
        this.backgroundColor = mBackgroundColor;
        this.itemColor = mItemColor;
        this.toolbarColor = mToolBarColor;
        this.toolbarTitleColor = mToolbarTitleColor;
        this.toolbarSubtitleColor = mToolbarSubtitleColor;
        this.iconColor = itemColor;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayouthospital, parent, false);

        //Make room for largest jsonObject
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
        MyViewHolder vh = new MyViewHolder(v,numberOfItems , backgroundColor, itemColor, iconColor); // pass the view to View Holder
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
                // check if there is an inner project.
                boolean keyValueIsObject= innerObj.getString(keyValue).substring(0,1).equals("[");
                if (keyValueIsObject) {
                    makeTextViewWithInnerObject(holder.tv[mCounter], innerObj, keyValue);
                    mCounter  = mCounter+3;
                } else {
                    final String txt = innerObj.getString(keyValue);
                    // check if string has image value "http"
                    if(txt.length() >3 && txt.substring(0,4).equals("http")){
                        makeImageView(holder, mCounter, innerObj, txt);
                        mCounter = mCounter + 3;
                    }else {
                        // check if string has color value "#"
                        if(txt.indexOf("#") != -1){
                            addIconToTextView(holder.tv[mCounter], txt, "right");
                        } else{
                            ((TextView) holder.tv[mCounter]).setText(txt);
                        }
                        mCounter = mCounter + 3;
                    }
                }
            } while (keys.hasNext()) ;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        removeEmptyViews(holder);
    }

    private void addIconToTextView(View view, String txt, String placement) {
        int idx = txt.indexOf("#");
        String iconString= txt.substring(idx+1,txt.length()); //Works
        String withoutIconText = txt.substring(0,idx-1); //Works
        TextView textView =((TextView) view);
        textView.setText(withoutIconText);
        Drawable unwrappedDrawable = getDrawable(iconColor,iconString);
        if (placement.equals(("left"))){
            textView.setCompoundDrawables(unwrappedDrawable, null, null, null);
        } else textView.setCompoundDrawables(null, null, unwrappedDrawable, null);
    }

    private void removeEmptyViews(MyViewHolder holder) {
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

    private void makeTextViewWithInnerObject(View view1, final JSONObject innerObj, final String keyValue) {
        // use key as title
        TextView view = (TextView) view1;
        if(keyValue.indexOf("#") != -1){
            addIconToTextView(view, keyValue, "left");
        }else{
            view.setText(keyValue);
        }
        //add onclick listener
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
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
                intent.putExtra("backgroundColor", backgroundColor);
                intent.putExtra("itemColor", itemColor);
                intent.putExtra("toolbarColor", toolbarColor);
                intent.putExtra("toolbarTitleColor", toolbarTitleColor);
                intent.putExtra("toolbarSubtitleColor", toolbarSubtitleColor);
                intent.putExtra("iconColor", iconColor);
                context.startActivity(intent);

            }
        });
    }

    private void makeImageView(MyViewHolder holder, int mCounter, final JSONObject innerObj, final String txt) {
        ImageView imageView = (ImageView) holder.tv[mCounter+1];
        imageView.setVisibility(View.VISIBLE);
        Picasso.get().load(txt).fit().centerInside().into(imageView);
        ((TextView) holder.tv[mCounter]).setVisibility(View.GONE); //hide textview

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("url", txt);
                intent.putExtra("patientName", patientName);
                intent.putExtra("cpr", cpr);
                intent.putExtra("backgroundColor", backgroundColor);
                intent.putExtra("itemColor", itemColor);
                intent.putExtra("toolbarColor", toolbarColor);
                intent.putExtra("toolbarTitleColor", toolbarTitleColor);
                intent.putExtra("toolbarSubtitleColor", toolbarSubtitleColor);
                intent.putExtra("iconColor", iconColor);

                Iterator<String> innerKeys = innerObj.keys();
                try {
                    intent.putExtra("recordName", innerObj.getString(innerKeys.next()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return arraySize;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgView;
        private View borderTitle;
        private TextView titleView;
        private View border;
        LinearLayout myLinearLayout;
        View[] tv;

        @SuppressLint("ResourceType")
        public MyViewHolder(View itemView, int numberOfItems, String mBackgroundColor, String mItemColor, String iconColor) {
            super(itemView);
            tv = new View[numberOfItems*3];
            int counter = 0;

            myLinearLayout = (LinearLayout) itemView.findViewById(R.id.linLayout);
            myLinearLayout.setBackgroundColor(Color.parseColor(mBackgroundColor));

            int textViewBorder = context.getResources().getDimensionPixelSize(R.dimen.textViewborder);
            int textViewHeight = context.getResources().getDimensionPixelSize(R.dimen.textViewHeight);
            int textViewWidth = context.getResources().getDimensionPixelSize(R.dimen.textViewWidth);
            int imageViewHeight= context.getResources().getDimensionPixelSize(R.dimen.imgViewHeight);
            int leftMargin = context.getResources().getDimensionPixelSize(R.dimen.left_margin);
            int textViewWidth_plus_leftMargin = context.getResources().getDimensionPixelSize(R.dimen.left_margin_plus_textview_width);


            titleView = new TextView(context);
            titleView.setGravity(Gravity.LEFT| Gravity.CENTER);
            titleView.setLayoutParams(new RecyclerView.LayoutParams(textViewWidth_plus_leftMargin, textViewHeight));
            titleView.setTextSize(20);
            titleView.setTextColor(Color.BLACK);
            titleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));


            //titleView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_pills, 0);
            //Drawable unwrappedDrawable = getDrawable(iconColor,"pills");
            //titleView.setCompoundDrawables(null, null, unwrappedDrawable, null);

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

            for (int i = 1; i < numberOfItems; i++) // i=1 because tile view already added
            {
                //Create textview
                textView = new TextView(context);
                textView.setGravity(Gravity.LEFT | Gravity.CENTER);
                RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(textViewWidth, textViewHeight);
                layoutParams.setMargins(leftMargin,0,0,0);
                textView.setLayoutParams(layoutParams);

                textView.setBackgroundColor(Color.parseColor(mItemColor));
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

    private Drawable getDrawable(String color, String icon) {
        //iconmap
        // TODO: add items here iconMap.put("string_value_used_in_xml_file", R.drawable.)
        // TODO: add more drawables in R.drawable file
        Map<String, Integer> iconMap= new HashMap<>();
        iconMap.put("pills", R.drawable.ic_pills);
        iconMap.put("bacteria", R.drawable.ic_bacteria);


        Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, iconMap.get(icon));
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor(iconColor));
        int h = wrappedDrawable.getIntrinsicHeight();
        int w = wrappedDrawable.getIntrinsicWidth();
        wrappedDrawable.setBounds( 0, 0, w, h );
        return unwrappedDrawable;
    }
}
