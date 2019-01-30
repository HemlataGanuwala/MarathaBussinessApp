package com.maratha.hema.marathabussinessapp.Approval;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.maratha.hema.marathabussinessapp.Adapter.RegistrationApprpvalAdapter;
import com.maratha.hema.marathabussinessapp.GlobalClass;
import com.maratha.hema.marathabussinessapp.Model.RegApprovalListPlanet;
import com.maratha.hema.marathabussinessapp.R;
import com.maratha.hema.marathabussinessapp.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoDetailsFragment extends Fragment {

    View view;
    ImageView imageViewdowndoc,imageViewdownreceipt;
    String custid,pname,path,p1,p2,p3,p4;
    ServiceHandler shh;


    public PhotoDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_photo_details, container, false);

        final GlobalClass globalVariable = (GlobalClass)getActivity().getApplicationContext();
        path = globalVariable.getconstr();

        imageViewdowndoc = (ImageView)view.findViewById(R.id.imgappuploaddoc);
        imageViewdownreceipt = (ImageView)view.findViewById(R.id.imgappuploadreceipt);

        Display();

        new FetchList1().execute();


        return view;
    }

    public void Display()
    {
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null)
        {
            custid = (String)bundle.get("a1");
            pname = (String)bundle.get("a2");
        }
    }

   public class FetchList1 extends AsyncTask<String, String, String>
    {

        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params)
        {
            shh = new ServiceHandler();
            String url =  path + "RegistrationApi/ProductImageIdWise";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Name", pname));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (jsonStr != null)
                {
                    JSONObject c1 = new JSONObject(jsonStr);
                    JSONArray classArray = c1.getJSONArray("Response");
                    for (int i = 0; i < classArray.length(); i++) {
                        JSONObject a1 = classArray.getJSONObject(i);
                        p1 = a1.getString("Product1");
                        p2 = a1.getString("Product2");
                        p3 = a1.getString("Product3");
                        p4 = a1.getString("Product4");
                    }


                } else {
                    Toast.makeText(getActivity(), "Data Not Available", Toast.LENGTH_LONG).show();
                }

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            new SendHttpRequestTask().execute();

            new SendHttpRequestTask1().execute();
        }
    }

    private class SendHttpRequestTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            try {

                String filename=p1.substring(p1.lastIndexOf("/")+1);

               // URL url = new URL("http://192.168.0.117:8014/UploadedFiles/" + filename);
                URL url = new URL("http://marathabusiness.skyvisioncables.com/UploadedFiles/" + filename);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            }catch (Exception e){
                //Log.d(TAG,e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            //ImageView imageView = (ImageView) findViewById(imgPhoto);
            imageViewdowndoc.setImageBitmap(result);
        }
    }

    private class SendHttpRequestTask1 extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            try {

                String filename=p2.substring(p2.lastIndexOf("/")+1);

//                URL url = new URL("http://192.168.0.117:8014/UploadedFiles/" + filename);
                URL url = new URL("http://marathabusiness.skyvisioncables.com/UploadedFiles/" + filename);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            }catch (Exception e){
                //Log.d(TAG,e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            //ImageView imageView = (ImageView) findViewById(imgPhoto);
            imageViewdownreceipt.setImageBitmap(result);
        }
    }

}
