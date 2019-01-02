package com.maratha.hema.marathabussinessapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApprovalActivity extends AppCompatActivity implements RegistrationAdapter.OnItemClickListner{

    RecyclerView recyclerView;
    RegistrationAdapter adapter;
    List<RegListPlanet> mPlanetlist = new ArrayList<RegListPlanet>();
    ServiceHandler shh;
    String path,custnm,location,custid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);

        final GlobalClass globalVariable = (GlobalClass)getApplicationContext();
        path = globalVariable.getconstr();

        recyclerView = (RecyclerView)findViewById(R.id.rcapproval);
        recyclerView.setLayoutManager(new LinearLayoutManager(ApprovalActivity.this));

        new FetchList1().execute();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(ApprovalActivity.this,ApprovalDetailsActivity.class);
        RegListPlanet planet = mPlanetlist.get(position);
        intent.putExtra("a1",planet.getCustomerId());
        startActivity(intent);
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
                        custnm = a1.getString("Name");
                        location = a1.getString("Address");

                        RegListPlanet planet1 = new RegListPlanet(custid,custnm,location);
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
                    adapter = new RegistrationAdapter(mPlanetlist);
                    recyclerView.setAdapter(adapter);
                }
            });
            adapter.setOnItemClickListner(ApprovalActivity.this);
        }
    }
}
