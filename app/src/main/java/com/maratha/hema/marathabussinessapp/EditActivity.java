package com.maratha.hema.marathabussinessapp;

import android.Manifest;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.maratha.hema.marathabussinessapp.Adapter.EditDetailAdapter;
import com.maratha.hema.marathabussinessapp.Adapter.RegistrationAdapter;
import com.maratha.hema.marathabussinessapp.Adapter.RegistrationApprpvalAdapter;
import com.maratha.hema.marathabussinessapp.Approval.ApprovalActivity;
import com.maratha.hema.marathabussinessapp.Approval.ApprovalDetailsActivity;
import com.maratha.hema.marathabussinessapp.Model.EditDetailsPlanet;
import com.maratha.hema.marathabussinessapp.Model.RegApprovalListPlanet;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity implements EditDetailAdapter.OnItemClickListener {

    private static final int PERMISSIONS_REQUEST_CALL_PHONE = 10;
    RecyclerView recyclerView;
    EditDetailAdapter adapter;
    List<EditDetailsPlanet> mPlanetlist = new ArrayList<EditDetailsPlanet>();
    ServiceHandler shh;
    String path,contact,pernm,custid,uri,checkid;
    int Status = 1;
    boolean chk;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        final GlobalClass globalVariable = (GlobalClass)getApplicationContext();
        path = globalVariable.getconstr();

        recyclerView = (RecyclerView)findViewById(R.id.rcedit);
        recyclerView.setLayoutManager(new LinearLayoutManager(EditActivity.this));

        mPlanetlist.clear();

       new FetchList1().execute();
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(EditActivity.this, EditDetailsActivity.class);
        EditDetailsPlanet planet = mPlanetlist.get(position);
        intent.putExtra("a1",planet.getCustomerId());
        intent.putExtra("a2",planet.getPersonName());
        intent.putExtra("a3",custid);
        startActivity(intent);

    }

    @Override
    public void iconImageViewOnClick(View v, int position) {
        EditDetailsPlanet planet = mPlanetlist.get(position);
        String uri = planet.getContact();

        showContacts();

        if (uri.length() != 0) {
            // TODO Auto-generated method stub
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + uri));
            startActivity(callIntent);
        }
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
            String url =  path + "RegistrationApi/GetEditList";

            Log.d("Url: ", "> " + url);

            try {
                List<NameValuePair> params2 = new ArrayList<>();
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


                        EditDetailsPlanet planet1 = new EditDetailsPlanet(custid,contact,pernm);
                        mPlanetlist.add(planet1);
                    }


                } else {
                    Toast.makeText(EditActivity.this, "Data Not Available", Toast.LENGTH_LONG).show();
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
                    adapter = new EditDetailAdapter(mPlanetlist);
                    recyclerView.setAdapter(adapter);
                }
            });
            adapter.setOnItemClickListener(EditActivity.this);
        }
    }
}
