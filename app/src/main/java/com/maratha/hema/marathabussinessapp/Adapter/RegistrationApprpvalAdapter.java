package com.maratha.hema.marathabussinessapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.maratha.hema.marathabussinessapp.R;
import com.maratha.hema.marathabussinessapp.Model.RegApprovalListPlanet;

import java.util.List;

public class RegistrationApprpvalAdapter extends RecyclerView.Adapter<RegistrationApprpvalAdapter.ListHolder> {
    private List<RegApprovalListPlanet> mPlanetList1;
    private OnItemClickListner mlistner;

    public interface OnItemClickListner
    {
        void onItemClick(int position);

        void iconImageViewOnClick(View v, int position);

//        void onItemCheck(String checkBoxName, int position);
    }
    public void setOnItemClickListner(OnItemClickListner listner){
        mlistner = listner;
    }

    public RegistrationApprpvalAdapter(List<RegApprovalListPlanet> mPlanetList1) {
        this.mPlanetList1 = mPlanetList1;
    }


    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.reg_approval_list_item,viewGroup,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListHolder listHolder, int i) {
        listHolder.textViewcontact.setText(mPlanetList1.get(i).getContact());
        listHolder.textViewname.setText(mPlanetList1.get(i).getPersonName());
        listHolder.textViewcustid.setText(mPlanetList1.get(i).getCustomerId());

        if (mPlanetList1.get(i).getChecked())
        {
            listHolder.checkBoxapp.setChecked(true);
        }
        else
        {
            listHolder.checkBoxapp.setChecked(false);
        }

    }


    @Override
    public int getItemCount() {
        return mPlanetList1.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView textViewname,textViewcontact,textViewcustid;
        ImageView imageViewcall;
        CheckBox checkBoxapp;

        public ListHolder(final View itemView) {
            super(itemView);
            textViewcontact = (TextView) itemView.findViewById(R.id.tvlistappcontact);
            textViewcustid = (TextView) itemView.findViewById(R.id.tvlistappcustid);
            textViewname = (TextView) itemView.findViewById(R.id.tvlistappname);
            imageViewcall = (ImageView) itemView.findViewById(R.id.imglistappcall);
            checkBoxapp = (CheckBox)itemView.findViewById(R.id.chkapp);
            this.setIsRecyclable(false);
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

            checkBoxapp.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {

                        int adptorpos = getAdapterPosition();
                        if(mPlanetList1.get(adptorpos).getChecked())
                        {
                            checkBoxapp.setChecked(false);
                            mPlanetList1.get(adptorpos).setChecked(false);
                        }
                        else
                        {
                            checkBoxapp.setChecked(true);
                            mPlanetList1.get(adptorpos).setChecked(true);
                        }
//                        CheckBox cb = (CheckBox) v ;
//                        RegApprovalListPlanet planet = (RegApprovalListPlanet) cb.getTag();
//                        planet.setChecked( cb.isChecked() );

                    }
                });
        }
    }
}
