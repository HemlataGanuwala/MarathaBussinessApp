package com.maratha.hema.marathabussinessapp.Approval;


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
import android.widget.Button;
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
public class OperatorDetailsFragment extends Fragment {

    View view;
    TextView textViewname,textViewbusiname,textViewocctype,textViewaddress,textViewcontact,textViewemail,textViewwebsite,textViewabout,textViewservice,textViewbestprice;
    ImageView imageViewdocument;
    ServiceHandler shh;
    String path, pname, businame,btype, address, contact,email, website, cabout,service, bestprice, custid,document, doc;
    Button buttonreject,buttonapproval,buttononhold;
    int Status = 1;


    public OperatorDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_operator_details, container, false);

        final GlobalClass globalVariable = (GlobalClass)getActivity().getApplicationContext();
        path = globalVariable.getconstr();

        textViewname = (TextView)view.findViewById(R.id.etappname);
        textViewbusiname = (TextView)view.findViewById(R.id.etappnameofbusiness);
        textViewocctype = (TextView)view.findViewById(R.id.tvappOccupation);
        textViewaddress = (TextView)view.findViewById(R.id.etappaddress);
        textViewcontact = (TextView)view.findViewById(R.id.etappcontactno);
        textViewemail = (TextView)view.findViewById(R.id.etappemail);
        textViewwebsite = (TextView)view.findViewById(R.id.etappwebsite);
        textViewabout = (TextView)view.findViewById(R.id.etappabout);
        textViewservice = (TextView)view.findViewById(R.id.etappservice);
        textViewbestprice = (TextView)view.findViewById(R.id.etappbestprice);
//        imageViewdocument = (ImageView)view.findViewById(R.id.imgappdocument);
        buttonreject = (Button)view.findViewById(R.id.btndetailreject);
        buttonapproval = (Button)view.findViewById(R.id.btndetailapproval);

        buttonapproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ApprovalUpdate().execute();
            }
        });

        buttonreject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ApprovalReject().execute();
            }
        });


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
            pname = (String)bundle.get("a2");
        }
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
                        btype = a1.getString("TypeofBusiness");
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
            textViewocctype.setText(btype);
            textViewcontact.setText(contact);
            textViewaddress.setText(address);
            textViewemail.setText(email);
            textViewwebsite.setText(website);
            textViewabout.setText(cabout);
            textViewservice.setText(service);
            textViewbestprice.setText(bestprice);
//            byte[] decodeString = Base64.decode(document, Base64.DEFAULT);
//            Bitmap decodebitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
//            imageViewdocument.setImageBitmap(decodebitmap);

        }
    }

   public class ApprovalUpdate extends AsyncTask<String, String, String>
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
                params2.add(new BasicNameValuePair("Bid", custid));
                params2.add(new BasicNameValuePair("Status", "1"));

                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, params2);

                if (jsonStr != null)
                {
                    JSONObject c1 = new JSONObject(jsonStr);
                    Status = c1.getInt("Status");


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

            if (Status == 1)
            {
                Toast.makeText(getActivity(), "Approval Successfully", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getActivity(), "Approval Failed", Toast.LENGTH_LONG).show();
            }

        }
    }


   public class ApprovalReject extends AsyncTask<String, String, String>
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

            if (Status == 1)
            {
                Toast.makeText(getActivity(), "Reject Successfully", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getActivity(), "Reject Failed", Toast.LENGTH_LONG).show();
            }

        }
    }

}
