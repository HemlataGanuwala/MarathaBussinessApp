package com.maratha.hema.marathabussinessapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.maratha.hema.marathabussinessapp.TypeSelect.BusinesspersondetailsActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class photouploadFragment extends Fragment {

    View view;
    Button buttonupload1,buttonupload2,buttonupload3,buttonupload4;
    ImageView imageView1,imageView2,imageView3,imageView4;
    Uri targetUri;
    Bitmap bitmap;
    TextView textViewupload;
    String img = "";
//    String img2 = "";
//    String img3 = "";
//    String img4 = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_photoupload, container, false);

        buttonupload1 = (Button)view.findViewById(R.id.btnupload1);
        buttonupload2 = (Button)view.findViewById(R.id.btnupload2);
        buttonupload3 = (Button)view.findViewById(R.id.btnupload3);
        buttonupload4 = (Button)view.findViewById(R.id.btnupload4);
        imageView1 = (ImageView) view.findViewById(R.id.imgupload1);
        imageView2 = (ImageView)view.findViewById(R.id.imgupload2);
        imageView3 = (ImageView)view.findViewById(R.id.imgupload3);
        imageView4 = (ImageView)view.findViewById(R.id.imgupload4);
        textViewupload = (TextView) view.findViewById(R.id.tvupload);

        buttonupload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img = "btnimg1";
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        buttonupload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img = "btnimg2";
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        buttonupload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img = "btnimg3";
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        buttonupload4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img = "btnimg4";
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (img) {
            case "btnimg1":
                if (resultCode == RESULT_OK){
                    targetUri = data.getData();
                    textViewupload.setText(targetUri.toString());

                    try {
                        bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(targetUri));
                        imageView1.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "btnimg2":
                if (resultCode == RESULT_OK){
                    targetUri = data.getData();
                    textViewupload.setText(targetUri.toString());

                    try {
                        bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(targetUri));
                        imageView2.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "btnimg3":
                if (resultCode == RESULT_OK){
                    targetUri = data.getData();
                    textViewupload.setText(targetUri.toString());

                    try {
                        bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(targetUri));
                        imageView3.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "btnimg4":
                if (resultCode == RESULT_OK){
                    targetUri = data.getData();
                    textViewupload.setText(targetUri.toString());

                    try {
                        bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(targetUri));
                        imageView4.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }


    }


}
