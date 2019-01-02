package com.maratha.hema.marathabussinessapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button buttonregister,buttonnext;
    Spinner spinnertype;
    String path,spinname;
    ServiceHandler shh;
    SpinnerTypePlanet spinnerTypePlanet;
    ArrayList<SpinnerTypePlanet> typePlanetslist = new ArrayList<SpinnerTypePlanet>();
    ProgressDialog progress;
    ImageView imageViewphoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GlobalClass globalVariable = (GlobalClass)getApplicationContext();
        path = globalVariable.getconstr();

        buttonnext = (Button)findViewById(R.id.btnnext);
        buttonregister = (Button)findViewById(R.id.btnregister);
        spinnertype = (Spinner)findViewById(R.id.spinnertype);
        imageViewphoto = (ImageView)findViewById(R.id.imgloginphoto);

        new GetOccupationData().execute();

        imageViewphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinname = spinnertype.getSelectedItem().toString();
                Intent intent = new Intent(MainActivity.this,BusinesstypeActivity.class);
                intent.putExtra("a1",spinname);
                startActivity(intent);
            }
        });

        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InfoActivity.class);
                startActivity(intent);
            }
        });
    }

    public class GetOccupationData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progress = new ProgressDialog(MainActivity.this);
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
                    Toast.makeText(MainActivity.this, "Data not Found", Toast.LENGTH_LONG).show();
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
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_spinner_item, typelables);

            // Drop down layout style - list view with radio button
            spinnerAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinnertype.setAdapter(spinnerAdapter);

        }
    }
}
