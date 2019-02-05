package com.maratha.hema.marathabussinessapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.maratha.hema.marathabussinessapp.Model.EditDetailsPlanet;
import com.maratha.hema.marathabussinessapp.R;

import java.util.List;

public class EditDetailAdapter extends RecyclerView.Adapter<EditDetailAdapter.ListHolder> {

    List<EditDetailsPlanet> mPlanetlist;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void onItemClick(int position);

        void iconImageViewOnClick(View v, int position);
    }

   public void setOnItemClickListener(OnItemClickListener listener){
        this.mlistener=listener;
   }

   public EditDetailAdapter(List<EditDetailsPlanet> mPlanetList)
   {
       this.mPlanetlist=mPlanetList;
   }

    @NonNull
    @Override
    public EditDetailAdapter.ListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.edit_list,viewGroup,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder listHolder, int i) {
        listHolder.textViewcontact.setText(mPlanetlist.get(i).getContact());
        listHolder.textViewname.setText(mPlanetlist.get(i).getPersonName());
        listHolder.textViewcustid.setText(mPlanetlist.get(i).getCustomerId());
    }

    @Override
    public int getItemCount() {
        return mPlanetlist.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        TextView textViewname, textViewcontact, textViewcustid;
        ImageView imageViewcall;



        public ListHolder(final View itemView) {
            super(itemView);
            textViewcontact = (TextView) itemView.findViewById(R.id.tvlistappcontact);
            textViewcustid = (TextView) itemView.findViewById(R.id.tvlistappcustid);
            textViewname = (TextView) itemView.findViewById(R.id.tvlistappname);
            imageViewcall = (ImageView) itemView.findViewById(R.id.imglistappcall);

            this.setIsRecyclable(false);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mlistener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mlistener.onItemClick(position);
                        }
                    }
                }
            });

            imageViewcall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mlistener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mlistener.iconImageViewOnClick(v, getAdapterPosition());
                        }
                    }
                }
            });

        }
    }
}
