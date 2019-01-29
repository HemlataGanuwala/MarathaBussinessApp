package com.maratha.hema.marathabussinessapp.TypeSelect;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maratha.hema.marathabussinessapp.Approval.ApprovalDetailsActivity;
import com.maratha.hema.marathabussinessapp.GlobalClass;
import com.maratha.hema.marathabussinessapp.R;
import com.maratha.hema.marathabussinessapp.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BusiPersonDetailFragment extends Fragment {

    View view;
    TextView textViewname,textViewbusiname,textViewocctype,textViewaddress,textViewcontact,textViewemail,textViewwebsite,textViewabout,textViewservice,textViewbestprice;
    ImageView imageViewdocument;
    ServiceHandler shh;
    String path, pname, businame,btype, address, contact,email, website, cabout,service, bestprice, custid,document, doc;


    public BusiPersonDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_busi_person_detail, container, false);

        final GlobalClass globalVariable = (GlobalClass)getActivity().getApplicationContext();
        path = globalVariable.getconstr();

        textViewname = (TextView)view.findViewById(R.id.etname);
        textViewbusiname = (TextView)view.findViewById(R.id.etnameofbusiness);
        textViewaddress = (TextView)view.findViewById(R.id.etaddress);
        textViewcontact = (TextView)view.findViewById(R.id.etcontactno);
        textViewemail = (TextView)view.findViewById(R.id.etemail);
        textViewwebsite = (TextView)view.findViewById(R.id.etwebsite);
        textViewabout = (TextView)view.findViewById(R.id.etabout);
        textViewservice = (TextView)view.findViewById(R.id.etservice);
        textViewbestprice = (TextView)view.findViewById(R.id.etbestprice);
        Display();

        new FetchList1().execute();


        return view;
    }

    public void Display()
    {
        Intent intent = getActivity().getIntent();
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

            textViewname.setText(pname);
            textViewbusiname.setText(businame);
            textViewcontact.setText(contact);
            textViewaddress.setText(address);
            textViewemail.setText(email);
            textViewwebsite.setText(website);
            textViewabout.setText(cabout);
            textViewservice.setText(service);
            textViewbestprice.setText(bestprice);


        }
    }

}
