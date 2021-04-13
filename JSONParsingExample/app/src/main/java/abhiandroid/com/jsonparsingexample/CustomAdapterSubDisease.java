package abhiandroid.com.jsonparsingexample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;


public class CustomAdapterSubDisease extends RecyclerView.Adapter<CustomAdapterSubDisease.MyViewHolder> {

    ArrayList<SubDiseaseItem> diseaseNames;
    ArrayList<String> mImageUrls;
    ArrayList<String> mRecordText;
    Context context;
    SubDiseaseItem item;

    public CustomAdapterSubDisease(Context context, ArrayList<SubDiseaseItem> diseaseNames) {
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

        item = diseaseNames.get(position);
        System.out.println("###"+(item.getFileText2()));
        System.out.println("###"+(item.getSubDisease()));
        System.out.println("###"+item);

        holder.diseaseName.setText(item.getSubDisease());
        if(item.getSubDisease().equals("")){
            holder.diseaseName.setVisibility(View.GONE);
            holder.fileText1.setVisibility(View.GONE);
            holder.fileText2.setVisibility(View.GONE);
            holder.img1.setVisibility(View.GONE);
            holder.img2.setVisibility(View.GONE);
        }

        holder.fileText1.setText(item.getFileText1());
        if(item.getFileText1().equals("")){
            holder.fileText1.setVisibility(View.GONE);
        }

        holder.fileText2.setText(item.getFileText2());
        System.out.println("!!!!!"+(item.getFileText2().length()));
        if(item.getFileText2().equals("")){
            holder.fileText2.setVisibility(View.GONE);
        }

        String imageUrl1 = item.getImageUrl1();
        String imageUrl2 = item.getImageUrl2();
        if(imageUrl1.equals("")){
            holder.img1.setVisibility(View.GONE);
        } else Picasso.get().load(imageUrl1).fit().centerInside().into(holder.img1);
        if(imageUrl2.equals("")){
            holder.img2.setVisibility(View.GONE);
        } else  Picasso.get().load(imageUrl2).fit().centerInside().into(holder.img2);


        // implement setOnClickListener event on item view.
        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                //Toast.makeText(context, diseaseNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public int getItemCount() {
        return diseaseNames.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img1, img2;
        TextView diseaseName, fileText1, fileText2;// init the item view's

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            diseaseName = (TextView) itemView.findViewById(R.id.subDiseaseName);
            fileText1 = (TextView) itemView.findViewById(R.id.fileText1);
            img1 = (ImageView) itemView.findViewById(R.id.img1);
            fileText2 = (TextView) itemView.findViewById(R.id.fileText2);
            img2 = (ImageView) itemView.findViewById(R.id.img2);
        }
    }
}