package com.maratha.hema.marathabussinessapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maratha.hema.marathabussinessapp.R;
import com.maratha.hema.marathabussinessapp.Model.RegListPlanet;

import java.util.List;

public class RegistrationAdapter extends RecyclerView.Adapter<RegistrationAdapter.ListHolder> {
    private List<RegListPlanet> mPlanetList1;
    private OnItemClickListner mlistner;

    public interface OnItemClickListner
    {
        void onItemClick(int position);

        void iconImageViewOnClick(View v, int position);
    }
    public void setOnItemClickListner(OnItemClickListner listner){
        mlistner = listner;
    }

    public RegistrationAdapter(List<RegListPlanet> mPlanetList1) {
        this.mPlanetList1 = mPlanetList1;
    }


    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.business_type_item,viewGroup,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder listHolder, int i) {
        listHolder.textViewcontact.setText(mPlanetList1.get(i).getContact());
        listHolder.textViewlocation.setText(mPlanetList1.get(i).getLocation());

    }


    @Override
    public int getItemCount() {
        return mPlanetList1.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView textViewlocation,textViewcontact;
        ImageView imageViewcall;

        public ListHolder(View itemView) {
            super(itemView);
            textViewcontact = (TextView) itemView.findViewById(R.id.tvlistcontact);
            textViewlocation = (TextView) itemView.findViewById(R.id.tvlistlocation);
            imageViewcall = (ImageView) itemView.findViewById(R.id.imglistcall);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mlistner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mlistner.onItemClick(position);
                        }
                    }
                }
            });

            imageViewcall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mlistner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mlistner.iconImageViewOnClick(v, getAdapterPosition());
                        }
                    }
                }
            });
        }
    }
}
