package com.maratha.hema.marathabussinessapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.maratha.hema.marathabussinessapp.TypeSelect.ImageShowActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class RegistrationFragment extends Fragment {

    View view;
    EditText editTextname,editTextnamebusiness,editTextaddress,editTextcontact,editTextemail,editTextwebsite,editTextabout,editTextservice,editTextbestprice;
    Spinner spinnertypebusiness;
    ImageView imageViewdocument;
    Button buttonregister,buttonupload,buttonaddtype;
    ProgressDialog progress;
    ServiceHandler shh;
    int Status =1;
    String path,name,nmbusiness,address,contact,email,website,about,services,bestprice,typeofbusiness,imagedoc;
    SpinnerTypePlanet spinnerTypePlanet;
    ArrayList<SpinnerTypePlanet> typePlanetslist = new ArrayList<SpinnerTypePlanet>();
    TextView textViewuri;
    Uri targetUri;
    Bitmap bitmap;
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
        imageViewdocument = (ImageView)view.findViewById(R.id.imgdocument);
        buttonregister = (Button)view.findViewById(R.id.btnregister);
        buttonupload = (Button)view.findViewById(R.id.btnbrowse);

        new GetOccupationData().execute();

        imageViewdocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ImageShowActivity.class);
                intent.putExtra("a1",targetUri);
                startActivity(intent);
            }
        });

        buttonupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            targetUri = data.getData();
            textViewuri.setText(targetUri.toString());

            try {
                bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(targetUri));
                imageViewdocument.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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
        imagedoc = bitmap.toString();

        new GetInsertData().execute();

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
                para.add(new BasicNameValuePair("Document", imagedoc));
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
