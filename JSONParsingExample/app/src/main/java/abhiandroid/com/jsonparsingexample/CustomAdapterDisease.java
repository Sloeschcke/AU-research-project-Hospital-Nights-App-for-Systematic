package abhiandroid.com.jsonparsingexample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;


public class CustomAdapterDisease extends RecyclerView.Adapter<CustomAdapterDisease.MyViewHolder> {

    ArrayList<String> diseaseNames;
    ArrayList<String> treatments;
    ArrayList<String> medications;
    ArrayList<String> plannedSurgeries;
    ArrayList<ArrayList<SubDiseaseItem>> subDiseases;

    Context context;

    public CustomAdapterDisease(Context context, ArrayList<String> diseaseNames, ArrayList<String> treatments, ArrayList<String> medications, ArrayList<String> plannedSurgeries, ArrayList<ArrayList<SubDiseaseItem>> subDiseases) {
        this.context = context;
        this.diseaseNames = diseaseNames;
        this.treatments = treatments;
        this.medications = medications;
        this.plannedSurgeries = plannedSurgeries;
        this.subDiseases = subDiseases;
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
        // set the data in items
        holder.diseaseName.setText(diseaseNames.get(position));
        if(diseaseNames.get(position).equals("")){
            holder.diseaseName.setVisibility(View.GONE);
        }
        holder.treatment.setText(treatments.get(position));
        if(treatments.get(position).equals("")){
            holder.treatment.setVisibility(View.GONE);
        }
        holder.medication.setText(medications.get(position));
        if(medications.get(position).equals("")){
            holder.medication.setVisibility(View.GONE);
        }
        holder.plannedSurgery.setText(plannedSurgeries.get(position));
        if(plannedSurgeries.get(position).equals("")){
            holder.plannedSurgery.setVisibility(View.GONE);
        }


        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Toast.makeText(context, diseaseNames.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, SubDiseaseActivity.class);
                //System.out.println("!!€€"+subDiseases.get(position));
                //intent.putStringArrayListExtra("subDiseases",new ArrayList<>(subDiseases.get(position)));
                intent.putExtra("position", position);
                context.startActivity(intent);

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
            diseaseName = (TextView) itemView.findViewById(R.id.diseaseName);
            treatment = (TextView) itemView.findViewById(R.id.treatment);
            medication = (TextView) itemView.findViewById(R.id.medication);
            plannedSurgery = (TextView) itemView.findViewById(R.id.plannedSurgery);

        }
    }
}
