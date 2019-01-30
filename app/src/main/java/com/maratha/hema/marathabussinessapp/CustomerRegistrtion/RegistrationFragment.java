package com.maratha.hema.marathabussinessapp.CustomerRegistrtion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.maratha.hema.marathabussinessapp.CustomerRegistrtion.DocumentPhotoUploadActivity;
import com.maratha.hema.marathabussinessapp.GlobalClass;
import com.maratha.hema.marathabussinessapp.R;
import com.maratha.hema.marathabussinessapp.ServiceHandler;
import com.maratha.hema.marathabussinessapp.SpinnerTypePlanet;
import com.maratha.hema.marathabussinessapp.TypeSelect.ImageShowActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;


public class RegistrationFragment extends Fragment {


    View view;
    EditText editTextname,editTextnamebusiness,editTextaddress,editTextcontact,editTextemail,editTextwebsite,editTextabout,editTextservice,editTextbestprice;
    Spinner spinnertypebusiness;
    Button buttonnext;
    ProgressDialog progress;
    ServiceHandler shh;
    int Status =1;
    String path,name,nmbusiness,address,contact,email,website,about,services,bestprice,typeofbusiness,imagedoc,imagereceipt;
    SpinnerTypePlanet spinnerTypePlanet;
    ArrayList<SpinnerTypePlanet> typePlanetslist = new ArrayList<SpinnerTypePlanet>();
    TextView textViewuri;
    public RegistrationFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_registration, container, false);

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
        spinnertypebusiness = (Spinner)view.findViewById(R.id.spiOccupation);
        buttonnext = (Button)view.findViewById(R.id.btnRegUpload);

        new GetOccupationData().execute();

        Display();

        buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editTextname.getText().toString();
                nmbusiness = editTextnamebusiness.getText().toString();
                address = editTextaddress.getText().toString();
                contact = editTextcontact.getText().toString();
                email = editTextemail.getText().toString();
                website = editTextwebsite.getText().toString();
                about = editTextabout.getText().toString();
                services = editTextservice.getText().toString();
                bestprice = editTextbestprice.getText().toString();
                typeofbusiness = spinnertypebusiness.getSelectedItem().toString();

                if(validated())
                {
                    Intent intent = new Intent(getActivity(), DocumentPhotoUploadActivity.class);
                    intent.putExtra("CustName",name);
                    intent.putExtra("NmBusiness",nmbusiness);
                    intent.putExtra("Address",address);
                    intent.putExtra("Contact",contact);
                    intent.putExtra("Email",email);
                    intent.putExtra("Website",website);
                    intent.putExtra("About",about);
                    intent.putExtra("Service",services);
                    intent.putExtra("Bestprice",bestprice);
                    intent.putExtra("TypeBusiness",typeofbusiness);
                    startActivity(intent);
                }

            }
        });




//        buttonregister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                InsertData();
//            }
//        });

        return view;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK){
//            targetUri = data.getData();
//            textViewuri.setText(targetUri.toString());
//
//            try {
//                bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(targetUri));
//                imageViewdocument.setImageBitmap(bitmap);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public void Display()
    {
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle !=null)
        {
            imagedoc = (String) bundle.get("P1");
            imagereceipt = (String)bundle.get("P2");

//            buttonregister.setVisibility(View.VISIBLE);
        }
    }

    public void InsertData()
    {
        name = editTextname.getText().toString();
        nmbusiness = editTextnamebusiness.getText().toString();
        address = editTextaddress.getText().toString();
        contact = editTextcontact.getText().toString();
        email = editTextemail.getText().toString();
        website = editTextwebsite.getText().toString();
        about = editTextabout.getText().toString();
        services = editTextservice.getText().toString();
        bestprice = editTextbestprice.getText().toString();
        typeofbusiness = spinnertypebusiness.getSelectedItem().toString();

        if(validated())
        {
            new GetInsertData().execute();
        }

    }

    public boolean validated()
    {
        boolean vaild = true;
        if(name.isEmpty() || name.length() > 32)
        {
            editTextname.setError("Name is too long");
            vaild = false;
        }
        if(address.isEmpty())
        {
            editTextaddress.setError("Enter Address");
            vaild = false;
        }
        if(nmbusiness.isEmpty())
        {
            editTextnamebusiness.setError("Enter Name of Business");
            vaild = false;
        }
        if(contact.isEmpty())
        {
            editTextcontact.setError("Enter Contact");
            vaild = false;
        }
        if(!Pattern.matches("[a-zA-Z]+", contact))
        {
            if(contact.length() < 6 || contact.length() > 11)
            {
                vaild = false;
                editTextcontact.setError("Contact is invalid");
            }
            else
            {
                vaild = true;

            }
        }


        return vaild;
    }

    public class GetInsertData extends AsyncTask<String, String, String> {

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

            String url = path + "RegistrationApi/BusinessManRegistration";

            Log.d("Url: ", "> " + url);

            try {
                // Making a request to url and getting response

                List<NameValuePair> para = new ArrayList<>();
                // para.add(new BasicNameValuePair("CustBal", balance));
//                para.add(new BasicNameValuePair("CustName", custname));
                para.add(new BasicNameValuePair("Name", name));
                para.add(new BasicNameValuePair("NameofBusiness", nmbusiness));
                para.add(new BasicNameValuePair("TypeofBusiness", typeofbusiness));
                para.add(new BasicNameValuePair("Contact", contact));
                para.add(new BasicNameValuePair("Address", address));
                para.add(new BasicNameValuePair("Email", email));
                para.add(new BasicNameValuePair("Website", website));
                para.add(new BasicNameValuePair("AboutBusiness", about));
                para.add(new BasicNameValuePair("Services", services));
                para.add(new BasicNameValuePair("BestPrice", bestprice));
//                para.add(new BasicNameValuePair("Document", image_str));
                para.add(new BasicNameValuePair("Status", "0"));


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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Register succesfully", Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                Toast.makeText(getActivity(), "Register Failed", Toast.LENGTH_LONG).show();
            }

            editTextname.setText("");
            editTextnamebusiness.setText("");
            editTextaddress.setText("");
            editTextcontact.setText("");
            editTextemail.setText("");
            editTextwebsite.setText("");
            editTextabout.setText("");
            editTextservice.setText("");
            editTextbestprice.setText("");
//            imageViewdocument.setImageBitmap(null);

        }
    }



    public class GetOccupationData extends AsyncTask<String, String, String> {

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
            List<String> typelables = new ArrayList<String>();

            for (int i = 0; i < typePlanetslist.size(); i++) {
                typelables.add(typePlanetslist.get(i).getOccupationname());
            }

            // Creating adapter for spinner
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item, typelables);

            // Drop down layout style - list view with radio button
            spinnerAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinnertypebusiness.setAdapter(spinnerAdapter);

        }
    }


}
