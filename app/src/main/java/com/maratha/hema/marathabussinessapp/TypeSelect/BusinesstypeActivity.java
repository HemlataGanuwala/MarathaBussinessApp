package com.maratha.hema.marathabussinessapp.TypeSelect;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.maratha.hema.marathabussinessapp.GlobalClass;
import com.maratha.hema.marathabussinessapp.R;
import com.maratha.hema.marathabussinessapp.Model.RegListPlanet;
import com.maratha.hema.marathabussinessapp.Adapter.RegistrationAdapter;
import com.maratha.hema.marathabussinessapp.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BusinesstypeActivity extends AppCompatActivity implements RegistrationAdapter.OnItemClickListner {

    private static final int PERMISSIONS_REQUEST_CALL_PHONE = 10;
    RecyclerView recyclerView;
    RegistrationAdapter adapter;
    List<RegListPlanet> mPlanetlist = new ArrayList<RegListPlanet>();
    ServiceHandler shh;
    String path,contact,nameofbusiness,custid,type,pname;
    String uri;
    TextView textViewtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businesstype);

        final GlobalClass globalVariable = (GlobalClass)getApplicationContext();
        path = globalVariable.getconstr();

        recyclerView = (RecyclerView)findViewById(R.id.rcbusinesstype);
        recyclerView.setLayoutManager(new LinearLayoutManager(BusinesstypeActivity.this));
        textViewtype = (TextView)findViewById(R.id.tvbusitypenameofbusi);

        Display();

        new FetchList1().execute();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(BusinesstypeActivity.this,BusinesspersondetailsActivity.class);
        RegListPlanet planet = mPlanetlist.get(position);
        intent.putExtra("a1",planet.getCustomerId());
        intent.putExtra("a2",planet.getPersonName());
        startActivity(intent);
    }

    @Override
    public void iconImageViewOnClick(View v, int position) {
        RegListPlanet planet = mPlanetlist.get(position);
        uri = planet.getContact();

        showContacts();

        if (uri.length() != 0) {
            // TODO Auto-generated method stub
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + uri));
            startActivity(callIntent);
        }
    }

    public void Display()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null)
        {
            type = (String)bundle.get("a1");
        }
        textViewtype.setText(type);
    }

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_CALL_PHONE);


            if (uri.length() != 0) {
                // TODO Auto-generated method stub
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + uri));
                startActivity(callIntent);
            }
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
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
            String url =  path + "RegistrationApi/CustomerSearchOccupation";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("TypeofBusiness", type));
                params2.add(new BasicNameValuePair("Status", "1"));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (jsonStr != null)
                {
                    JSONObject c1 = new JSONObject(jsonStr);
                    JSONArray classArray = c1.getJSONArray("Response");
                    for (int i = 0; i < classArray.length(); i++) {
                        JSONObject a1 = classArray.getJSONObject(i);
                        custid = a1.getString("Bid");
                        contact = a1.getString("Contact");
                        nameofbusiness = a1.getString("NameofBusiness");
                        pname = a1.getString("Name");

                        RegListPlanet planet1 = new RegListPlanet(custid,contact,nameofbusiness,pname);
                        mPlanetlist.add(planet1);
                    }


                } else {
                    Toast.makeText(BusinesstypeActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter = new RegistrationAdapter(mPlanetlist);
                    recyclerView.setAdapter(adapter);
                }
            });
            adapter.setOnItemClickListner(BusinesstypeActivity.this);
        }
    }
}

