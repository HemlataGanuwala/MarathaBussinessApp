package com.maratha.hema.marathabussinessapp.Approval;

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
import android.widget.Button;
import android.widget.Toast;

import com.maratha.hema.marathabussinessapp.EditDetailsActivity;
import com.maratha.hema.marathabussinessapp.GlobalClass;
import com.maratha.hema.marathabussinessapp.R;
import com.maratha.hema.marathabussinessapp.Model.RegApprovalListPlanet;
import com.maratha.hema.marathabussinessapp.Adapter.RegistrationApprpvalAdapter;
import com.maratha.hema.marathabussinessapp.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApprovalActivity extends AppCompatActivity implements RegistrationApprpvalAdapter.OnItemClickListner {

    private static final int PERMISSIONS_REQUEST_CALL_PHONE = 10;
    RecyclerView recyclerView;
    RegistrationApprpvalAdapter adapter;
    List<RegApprovalListPlanet> mPlanetlist = new ArrayList<RegApprovalListPlanet>();
    ServiceHandler shh;
    String path,contact,pernm,custid,uri,checkid;
    Button buttonapproval,buttonreject;
    int Status = 1;
    boolean chk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);

        final GlobalClass globalVariable = (GlobalClass)getApplicationContext();
        path = globalVariable.getconstr();

        recyclerView = (RecyclerView)findViewById(R.id.rcapproval);
        recyclerView.setLayoutManager(new LinearLayoutManager(ApprovalActivity.this));
        buttonapproval = (Button)findViewById(R.id.btnapproval);
        buttonreject = (Button)findViewById(R.id.btnreject);


        buttonapproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               int ti = recyclerView.getAdapter().getItemCount();

                for (int i = 0; i < ti; i++)
                {

                    checkid = (String) mPlanetlist.get(i).getCustomerId();


                    if (mPlanetlist.get(i).getChecked())
                    {
                        new ApprovalUpdate().execute();
                    }
                    else {

                    }

                    try  { Thread.sleep(500);}
                    catch (InterruptedException e){e.printStackTrace();}
                }

            }
        });

        new FetchList1().execute();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(ApprovalActivity.this,ApprovalDetailsActivity.class);
        RegApprovalListPlanet planet = mPlanetlist.get(position);
        intent.putExtra("a1",planet.getCustomerId());
        intent.putExtra("a2",planet.getPersonName());
        startActivity(intent);


    }

    @Override
    public void iconImageViewOnClick(View v, int position) {
        RegApprovalListPlanet planet = mPlanetlist.get(position);
        String uri = planet.getContact();

        showContacts();

        if (uri.length() != 0) {
            // TODO Auto-generated method stub
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + uri));
            startActivity(callIntent);
        }
    }

//    @Override
//    public void onItemCheck(String checkBoxName, int position) {
//        RegApprovalListPlanet planet = mPlanetlist.get(position);
//        checkid = planet.getCustomerId();
//    }

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
            String url =  path + "RegistrationApi/CustomerSearch";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
//                params2.add(new BasicNameValuePair("Bid", custid));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.GET, params2);

                if (jsonStr != null)
                {
                    JSONObject c1 = new JSONObject(jsonStr);
                    JSONArray classArray = c1.getJSONArray("Response");
                    for (int i = 0; i < classArray.length(); i++) {
                        JSONObject a1 = classArray.getJSONObject(i);
                        custid = a1.getString("Bid");
                        contact = a1.getString("Contact");
                        pernm = a1.getString("Name");


                        RegApprovalListPlanet planet1 = new RegApprovalListPlanet(custid,contact,pernm,chk);
                        mPlanetlist.add(planet1);
                    }


                } else {
                    Toast.makeText(ApprovalActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
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
                    adapter = new RegistrationApprpvalAdapter(mPlanetlist);
                    recyclerView.setAdapter(adapter);
                }
            });
            adapter.setOnItemClickListner(ApprovalActivity.this);
        }
    }

    class ApprovalUpdate extends AsyncTask<String, String, String>
    {

        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params)
        {
            shh = new ServiceHandler();
            String url =  path + "RegistrationApi/ApprovalCustomer";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Bid", checkid));
                params2.add(new BasicNameValuePair("Status", "1"));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (jsonStr != null)
                {
                    JSONObject c1 = new JSONObject(jsonStr);
                    Status = c1.getInt("Status");


                } else {
                    Toast.makeText(ApprovalActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
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

            if (Status == 1)
            {
                Toast.makeText(ApprovalActivity.this, "Approval Successfully", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(ApprovalActivity.this, "Approval Failed", Toast.LENGTH_LONG).show();
            }

        }
    }


    class ApprovalReject extends AsyncTask<String, String, String>
    {

        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params)
        {
            shh = new ServiceHandler();
            String url =  path + "RegistrationApi/ApprovalReject";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
                params2.add(new BasicNameValuePair("Bid", custid));
                params2.add(new BasicNameValuePair("Status", "2"));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (jsonStr != null)
                {
                    JSONObject c1 = new JSONObject(jsonStr);
                    Status = c1.getInt("Status");


                } else {
                    Toast.makeText(ApprovalActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
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

            if (Status == 1)
            {
                Toast.makeText(ApprovalActivity.this, "Reject Successfully", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(ApprovalActivity.this, "Reject Failed", Toast.LENGTH_LONG).show();
            }

        }
    }
}
