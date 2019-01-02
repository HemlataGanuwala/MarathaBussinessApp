package com.maratha.hema.marathabussinessapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApprovalDetailsActivity extends AppCompatActivity {

    TextView textViewname,textViewbusiname,textViewocctype,textViewaddress,textViewcontact,textViewemail,textViewwebsite,textViewabout,textViewservice,textViewbestprice;
    ImageView imageViewdocument;
    ServiceHandler shh;
    String path;
    String pname;
    String businame;
    String btype;
    String address;
    String contact;
    String email;
    String website;
    String cabout;
    String service;
    String bestprice;
    String custid;
    String document;
    byte doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_details);

        final GlobalClass globalVariable = (GlobalClass)getApplicationContext();
        path = globalVariable.getconstr();

        textViewname = (TextView)findViewById(R.id.etappname);
        textViewbusiname = (TextView)findViewById(R.id.etappnameofbusiness);
        textViewocctype = (TextView)findViewById(R.id.tvappOccupation);
        textViewaddress = (TextView)findViewById(R.id.etappaddress);
        textViewcontact = (TextView)findViewById(R.id.etappcontactno);
        textViewemail = (TextView)findViewById(R.id.etappemail);
        textViewwebsite = (TextView)findViewById(R.id.etappwebsite);
        textViewabout = (TextView)findViewById(R.id.etappabout);
        textViewservice = (TextView)findViewById(R.id.etappservice);
        textViewbestprice = (TextView)findViewById(R.id.etappbestprice);
        imageViewdocument = (ImageView)findViewById(R.id.imgappdocument);

        Display();

        new FetchList1().execute();


    }

    public void Display()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null)
        {
            custid = (String)bundle.get("a1");
        }
    }

    class FetchList1 extends AsyncTask<String, String, String>
    {

        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params)
        {
            shh = new ServiceHandler();
            String url =  path + "RegistrationApi/CustomerSearchIdWise";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Bid", custid));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (jsonStr != null)
                {
                    JSONObject c1 = new JSONObject(jsonStr);
                    JSONArray classArray = c1.getJSONArray("Response");
                    for (int i = 0; i < classArray.length(); i++) {
                        JSONObject a1 = classArray.getJSONObject(i);
                        custid = a1.getString("Bid");
                        pname = a1.getString("Name");
                        businame = a1.getString("NameofBusiness");
                        btype = a1.getString("TypeofBusiness");
                        document = a1.getString("Document");
                        email = a1.getString("Email");
                        contact = a1.getString("Contact");
                        website = a1.getString("Website");
                        cabout = a1.getString("AboutBusiness");
                        service = a1.getString("Services");
                        bestprice = a1.getString("BestPrice");
                        address = a1.getString("Address");




                    }


                } else {
                    Toast.makeText(ApprovalDetailsActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
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

            textViewname.setText(pname);
            textViewbusiname.setText(businame);
            textViewocctype.setText(btype);
            textViewcontact.setText(contact);
            textViewaddress.setText(address);
            textViewemail.setText(email);
            textViewwebsite.setText(website);
            textViewabout.setText(cabout);
            textViewservice.setText(service);
            textViewbestprice.setText(bestprice);
            byte[] doc = document.getBytes();
//            imageViewdocument.setImageBitmap(document);
//            byte[] decodeString = Base64.decode(document, Base64.DEFAULT);
            Bitmap decodebitmap = BitmapFactory.decodeByteArray(doc, 0, doc.length);
            imageViewdocument.setImageBitmap(decodebitmap);

        }
    }
}
