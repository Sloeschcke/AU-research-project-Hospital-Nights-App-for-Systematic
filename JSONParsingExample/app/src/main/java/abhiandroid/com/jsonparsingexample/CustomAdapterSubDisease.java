package abhiandroid.com.jsonparsingexample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CustomAdapterSubDisease extends RecyclerView.Adapter<CustomAdapterSubDisease.MyViewHolder> {

    ArrayList<String> diseaseNames;
    ArrayList<String> mImageUrls;
    ArrayList<String> mRecordText;
    Context context;

    public CustomAdapterSubDisease(Context context, ArrayList<String> diseaseNames) {
        this.context = context;
        this.diseaseNames = diseaseNames;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayoutsubdisease, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        holder.diseaseName.setText(diseaseNames.get(position));
        if(diseaseNames.get(position).equals("")){
            holder.diseaseName.setVisibility(View.GONE);
        }

        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Toast.makeText(context, diseaseNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public int getItemCount() {
        return diseaseNames.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView diseaseName, treatment, medication, plannedSurgery;// init the item view's

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            diseaseName = (TextView) itemView.findViewById(R.id.subDiseaseName);
        }
    }
}
