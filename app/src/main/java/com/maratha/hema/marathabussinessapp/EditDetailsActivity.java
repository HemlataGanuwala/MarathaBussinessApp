package com.maratha.hema.marathabussinessapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditDetailsActivity extends AppCompatActivity {

    EditText editTextname, editTextbusinessname, editTextaddress, editTextcontactno, editTextemail, editTextwebsite, editTextaboutbuisness, editTextservice, editTextprice;
    Button buttonupdate;
    Spinner spinnerbuisnesstype;
    String name, businessname, address, phoneno, email, website, aboutebuisness, service, price, typeofbuisness, path, id;
    ProgressDialog progress;
    ServiceHandler shh;
    int Status =1;
    SpinnerTypePlanet spinnerTypePlanet;
    ArrayList<SpinnerTypePlanet> typePlanetslist = new ArrayList<SpinnerTypePlanet>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        final GlobalClass globalVariable = (GlobalClass)getApplicationContext();
        path = globalVariable.getconstr();

        editTextname=(EditText)findViewById(R.id.etname);
        editTextaddress=(EditText)findViewById(R.id.etaddress);
        editTextbusinessname=(EditText)findViewById(R.id.etnameofbusiness);
        editTextcontactno=(EditText)findViewById(R.id.etcontactno);
        editTextemail=(EditText)findViewById(R.id.etemail);
        editTextaboutbuisness=(EditText)findViewById(R.id.etabout);
        editTextwebsite=(EditText)findViewById(R.id.etwebsite);
        editTextprice=(EditText)findViewById(R.id.etbestprice);
        editTextservice=(EditText)findViewById(R.id.etservice);

        buttonupdate=(Button)findViewById(R.id.btnupdate);
        spinnerbuisnesstype=(Spinner)findViewById(R.id.spiOccupation);

        Display();
        new FetchList1().execute();
        new GetOccupationData().execute();

        buttonupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
    }

    public void Display()
    {
        Intent intent= getIntent();
        Bundle bundle=intent.getExtras();
        if (bundle!=null)
        {
            id = (String)bundle.get("Id");
            phoneno = (String)bundle.get("Mobileno");
            name = (String)bundle.get("Name");
        }
    }

    public void insertData()
    {
        name=editTextname.getText().toString();
        businessname=editTextbusinessname.getText().toString();
        address=editTextaddress.getText().toString();
        phoneno=editTextcontactno.getText().toString();
        email=editTextemail.getText().toString();
        aboutebuisness=editTextaboutbuisness.getText().toString();
        website=editTextwebsite.getText().toString();
        service=editTextservice.getText().toString();
        price=editTextprice.getText().toString();
        typeofbuisness=spinnerbuisnesstype.getSelectedItem().toString();

        new GetUpdateData().execute();

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
            String url =  path + "RegistrationApi/CustomerSearchIdWise";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Bid", id));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (jsonStr != null)
                {
                    JSONObject c1 = new JSONObject(jsonStr);
                    JSONArray classArray = c1.getJSONArray("Response");
                    for (int i = 0; i < classArray.length(); i++) {
                        JSONObject a1 = classArray.getJSONObject(i);

                        name = a1.getString("Name");
                        phoneno = a1.getString("Contact");

                    }


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

            editTextname.setText(name);
            editTextcontactno.setText(phoneno);


        }
    }

    public class GetUpdateData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress = new ProgressDialog(EditDetailsActivity.this);
            progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.show();
            progress.setContentView(R.layout.progress_dialog);
        }


        @Override
        protected String doInBackground(String... strings) {

            shh=new ServiceHandler();

            String url=path+"RegistrationApi/RegUpdate";

            Log.d("Url: ", "> " + url);

            try {
                // Making a request to url and getting response

                List<NameValuePair> para = new ArrayList<>();
                // para.add(new BasicNameValuePair("CustBal", balance));
//                para.add(new BasicNameValuePair("CustName", custname));
                para.add(new BasicNameValuePair("Bid",id));
                para.add(new BasicNameValuePair("Name", name));
                para.add(new BasicNameValuePair("NameofBusiness", businessname));
                para.add(new BasicNameValuePair("TypeofBusiness", typeofbuisness));
                para.add(new BasicNameValuePair("Address", address));
                para.add(new BasicNameValuePair("Contact", phoneno));
                para.add(new BasicNameValuePair("Email", email));
                para.add(new BasicNameValuePair("Website", website));
                para.add(new BasicNameValuePair("AboutBusiness", aboutebuisness));
                para.add(new BasicNameValuePair("Services", service));
                para.add(new BasicNameValuePair("BestPrice", price));
//                para.add(new BasicNameValuePair("Document", image_str));
                para.add(new BasicNameValuePair("Status", "0"));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, para);
                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    String msg = jObj.getString("Message");
                    Status = Integer.parseInt(jObj.getString("Status"));

                }


            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ServiceHandler", "Json Error ");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (Status == 1) {
                EditDetailsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(EditDetailsActivity.this, "Update succesfully", Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                Toast.makeText(EditDetailsActivity.this, "Update Failed", Toast.LENGTH_LONG).show();
            }

            editTextname.setText("");
            editTextbusinessname.setText("");
            editTextaddress.setText("");
            editTextcontactno.setText("");
            editTextemail.setText("");
            editTextwebsite.setText("");
            editTextaboutbuisness.setText("");
            editTextservice.setText("");
            editTextprice.setText("");
        }

    }

    public class GetOccupationData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progress = new ProgressDialog(EditDetailsActivity.this);
            progress.getWindow().setBackgroundDrawable(new
                    ColorDrawable(android.graphics.Color.TRANSPARENT));
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.show();
            progress.setContentView(R.layout.progress_dialog);
        }

        @Override
        protected String doInBackground(String... params) {


            shh = new ServiceHandler();

            String url = path + "RegistrationApi/GetOccupationList";

            Log.d("Url: ", "> " + url);

            try {
                // Making a request to url and getting response

                List<NameValuePair> para = new ArrayList<>();


                String jsonStr = shh.makeServiceCall(url, ServiceHandler.GET, null);
                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    JSONArray jsonArray=jObj.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject a1 = jsonArray.getJSONObject(i);

                        spinnerTypePlanet = new SpinnerTypePlanet(a1.getString("Occupation"));
                        typePlanetslist.add(spinnerTypePlanet);

                    }


                } else {
                    Toast.makeText(EditDetailsActivity.this, "Data not Found", Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ServiceHandler", "Json Error ");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            progress.dismiss();
            List<String> typelables = new ArrayList<String>();

            for (int i = 0; i < typePlanetslist.size(); i++) {
                typelables.add(typePlanetslist.get(i).getOccupationname());
            }

            // Creating adapter for spinner
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(EditDetailsActivity.this,
                    android.R.layout.simple_spinner_item, typelables);

            // Drop down layout style - list view with radio button
            spinnerAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinnerbuisnesstype.setAdapter(spinnerAdapter);

        }
    }
}
