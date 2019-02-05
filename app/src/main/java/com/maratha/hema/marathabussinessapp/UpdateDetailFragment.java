package com.maratha.hema.marathabussinessapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class UpdateDetailFragment extends Fragment {

    EditText editTextname,editTextnamebusiness,editTextaddress,editTextcontact,editTextemail,editTextwebsite,editTextabout,editTextservice,editTextbestprice, editTextbusinesstype;
    Button buttupload, buttondelete;
    ProgressDialog progress;
    ServiceHandler shh;
    int Status =1;
    String path,name,nmbusiness,address,contact,email,website,about,services,bestprice,typeofbusiness,imagedoc,imagereceipt,id;
    SpinnerTypePlanet spinnerTypePlanet;
    ArrayList<SpinnerTypePlanet> typePlanetslist = new ArrayList<SpinnerTypePlanet>();
    TextView textViewuri;
    View view;

    public UpdateDetailFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=  inflater.inflate(R.layout.fragment_update_detail, container, false);

        final GlobalClass globalVariable = (GlobalClass)getActivity().getApplicationContext();
        path = globalVariable.getconstr();

        textViewuri = (TextView)view.findViewById(R.id.tvphotouri);
        editTextname = (EditText)view.findViewById(R.id.etname);
        editTextnamebusiness = (EditText)view.findViewById(R.id.etnameofbusiness);
        editTextaddress = (EditText)view.findViewById(R.id.etaddress);
        editTextcontact = (EditText)view.findViewById(R.id.etcontactno);
        editTextemail = (EditText)view.findViewById(R.id.etemail);
        editTextwebsite = (EditText)view.findViewById(R.id.etwebsite);
        editTextabout = (EditText)view.findViewById(R.id.etabout);
        editTextservice = (EditText)view.findViewById(R.id.etservice);
        editTextbestprice = (EditText)view.findViewById(R.id.etbestprice);
        editTextbusinesstype = (EditText) view.findViewById(R.id.spiOccupation);
        buttupload = (Button)view.findViewById(R.id.btnRegUpload);
        buttondelete = (Button)view.findViewById(R.id.btndelete);

        buttupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });

        buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteData().execute();
            }
        });

        display();
        new FetchList1().execute();
        return view;

    }

    public void display()
    {
        Intent intent=getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null)
        {
            id=(String)bundle.get("a1");
        }
    }

    public void insert(){
        name=editTextname.getText().toString();
        nmbusiness=editTextnamebusiness.getText().toString();
        address=editTextaddress.getText().toString();
        contact=editTextcontact.getText().toString();
        email=editTextemail.getText().toString();
        typeofbusiness=editTextbusinesstype.getText().toString();
        services=editTextservice.getText().toString();
        website=editTextwebsite.getText().toString();
        bestprice=editTextbestprice.getText().toString();
        about=editTextabout.getText().toString();
        new GetInsertRegData().execute();
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
                        nmbusiness = a1.getString("NameofBusiness");
                        typeofbusiness = a1.getString("TypeofBusiness");
                        email = a1.getString("Email");
                        contact = a1.getString("Contact");
                        website = a1.getString("Website");
                        about = a1.getString("AboutBusiness");
                        services = a1.getString("Services");
                        bestprice = a1.getString("BestPrice");
                        address = a1.getString("Address");

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


            if(!name.equals("null")) {
                editTextname.setText(name);
            }
            else {
                editTextname.setText(" ");
            }

            if(!nmbusiness.equals("null")) {
                editTextnamebusiness.setText(nmbusiness);
            }
            else {
                editTextnamebusiness.setText(" ");
            }

            if(!contact.equals("null")) {
                editTextcontact.setText(contact);
            }
            else {
                editTextcontact.setText(" ");
            }

            if (!address.equals("null")) {
                editTextaddress.setText(address);
            }
            else{
                editTextaddress.setText(" ");
            }

            if (!email.equals("null")) {
                editTextemail.setText(email);
            }
            else {
                editTextemail.setText(" ");
            }

            if (!website.equals("null")) {
                editTextwebsite.setText(website);
            }
            else {
                editTextwebsite.setText(" ");
            }

            if (!about.equals("null")) {
                editTextabout.setText(about);
            }
            else {
                editTextabout.setText(" ");
            }

            if (!services.equals("null"))
            {
                editTextservice.setText(services);
            }
            else {
                editTextservice.setText(" ");
            }

            if (!bestprice.equals("null")) {
                editTextbestprice.setText(bestprice);
            }
            else {
                editTextbestprice.setText(" ");
            }
//            byte[] decodeString = Base64.decode(document, Base64.DEFAULT);
//            Bitmap decodebitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
//            imageViewdocument.setImageBitmap(decodebitmap);

        }
    }

    public class GetInsertRegData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            progress = new ProgressDialog(getActivity());
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

            String url = path + "RegistrationApi/UpdateRecord";

            Log.d("Url: ", "> " + url);

            try {
                // Making a request to url and getting response

                List<NameValuePair> para = new ArrayList<>();

                para.add(new BasicNameValuePair("Bid", id));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, para);
                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    String msg = jObj.getString("Message");
                    Status = Integer.parseInt(jObj.getString("Status"));


                } else {
                    Toast.makeText(getActivity(), "Data not Found", Toast.LENGTH_LONG).show();
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

            if (Status == 1) {

                Toast.makeText(getActivity(), "Update succesfully", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getActivity(), "Update Failed", Toast.LENGTH_LONG).show();
            }

            editTextname.setText("");
            editTextaddress.setText("");
            editTextabout.setText("");
            editTextbestprice.setText("");
            editTextbusinesstype.setText("");
            editTextcontact.setText("");
            editTextemail.setText("");
            editTextnamebusiness.setText("");
            editTextservice.setText("");
            editTextwebsite.setText("");
        }
    }

    public class DeleteData extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            shh = new ServiceHandler();

            String url = path + "RegistrationApi/DeleteRecord";

            Log.d("Url: ", "> " + url);

            try {
                // Making a request to url and getting response

                List<NameValuePair> para = new ArrayList<>();

                para.add(new BasicNameValuePair("Bid", id));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, para);

                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    String msg = jObj.getString("Message");
                    Status = Integer.parseInt(jObj.getString("Status"));

                } else {
                    Toast.makeText(getActivity(), "Data not Found", Toast.LENGTH_LONG).show();
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
            if (Status==1){
                Toast.makeText(getActivity(), "Record Deleted ", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getActivity(), "Record Not Deleted", Toast.LENGTH_LONG).show();
            }

            editTextname.setText("");
            editTextaddress.setText("");
            editTextabout.setText("");
            editTextbestprice.setText("");
            editTextbusinesstype.setText("");
            editTextcontact.setText("");
            editTextemail.setText("");
            editTextnamebusiness.setText("");
            editTextservice.setText("");
            editTextwebsite.setText("");
        }

    }

}
