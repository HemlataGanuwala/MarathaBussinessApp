package com.maratha.hema.marathabussinessapp.Approval;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.maratha.hema.marathabussinessapp.GlobalClass;
import com.maratha.hema.marathabussinessapp.R;
import com.maratha.hema.marathabussinessapp.SelectActivity;
import com.maratha.hema.marathabussinessapp.ServiceHandler;

public class LoginActivity extends AppCompatActivity {

    String path,tpin,lpin;
    Button button0,button1,button2,button3,button4,button5,button6,button7,button8,button9;
    TextView textViewpin1,textViewpin2,textViewpin3,textViewpin4;
    ProgressDialog progress;
    ServiceHandler shh;
    ImageButton buttonback,buttonshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final GlobalClass globalVariable = (GlobalClass)getApplicationContext();
        path = globalVariable.getconstr();

        button0 = (Button)findViewById(R.id.btn0);
        button1 = (Button)findViewById(R.id.btn1);
        button2 = (Button)findViewById(R.id.btn2);
        button3 = (Button)findViewById(R.id.btn3);
        button4 = (Button)findViewById(R.id.btn4);
        button5 = (Button)findViewById(R.id.btn5);
        button6 = (Button)findViewById(R.id.btn6);
        button7 = (Button)findViewById(R.id.btn7);
        button8 = (Button)findViewById(R.id.btn8);
        button9 = (Button)findViewById(R.id.btn9);
        buttonback = (ImageButton) findViewById(R.id.btnback);
        buttonshow = (ImageButton)findViewById(R.id.btnshow);
        textViewpin1 = (TextView)findViewById(R.id.etpass1);
        textViewpin2 = (TextView)findViewById(R.id.etpass2);
        textViewpin3 = (TextView)findViewById(R.id.etpass3);
        textViewpin4 = (TextView)findViewById(R.id.etpass4);

        buttonshow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {

                    case MotionEvent.ACTION_UP:
                        textViewpin1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        textViewpin2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        textViewpin3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        textViewpin4.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        textViewpin1.setInputType(InputType.TYPE_CLASS_TEXT);
                        textViewpin2.setInputType(InputType.TYPE_CLASS_TEXT);
                        textViewpin3.setInputType(InputType.TYPE_CLASS_TEXT);
                        textViewpin4.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;

                }
                return true;
            }

        });

        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Cleartextdata();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpin = "1";
                Filltextdata();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpin = "2";
                Filltextdata();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpin = "3";
                Filltextdata();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpin = "4";
                Filltextdata();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpin = "5";
                Filltextdata();
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpin = "6";
                Filltextdata();
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpin = "7";
                Filltextdata();
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpin = "8";
                Filltextdata();
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpin = "9";
                Filltextdata();
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpin = "0";
                Filltextdata();
            }
        });

        textViewpin4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LoginData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void Filltextdata()
    {
        if (textViewpin1.getText().toString().equals(""))
        {
            textViewpin1.setText(tpin);
        }
        else if (textViewpin2.getText().toString().equals(""))
        {
            textViewpin2.setText(tpin);
        }
        else if (textViewpin3.getText().toString().equals(""))
        {
            textViewpin3.setText(tpin);
        }
        else if (textViewpin4.getText().toString().equals(""))
        {
            textViewpin4.setText(tpin);
        }
    }

    public void Cleartextdata()
    {
        if (!textViewpin4.getText().toString().equals(""))
        {
            textViewpin4.setText("");
        }
        else if (!textViewpin3.getText().toString().equals(""))
        {
            textViewpin3.setText("");
        }
        else if (!textViewpin2.getText().toString().equals(""))
        {
            textViewpin2.setText("");
        }
        else if (!textViewpin1.getText().toString().equals(""))
        {
            textViewpin1.setText("");
        }
    }

    public void LoginData(){

        tpin = textViewpin1.getText().toString() + textViewpin2.getText().toString() + textViewpin3.getText().toString() + textViewpin4.getText().toString();

        if(!textViewpin4.getText().toString().equals(""))
        {
            lpin = "1"+"2"+"3"+"4";
            if(tpin.equals(lpin))
            {
                Toast.makeText(LoginActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, SelectActivity.class);
                startActivity(intent);
            }
        }


    }

//    class getloginData extends AsyncTask<Void, Void, String>
//    {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
////            progress=new ProgressDialog(getActivity());
////            progress.setMessage("Loading...");
////            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
////            progress.setIndeterminate(true);
////            progress.setProgress(0);
////            progress.show();
//
//            progress = new ProgressDialog(LoginActivity.this);
//            progress.getWindow().setBackgroundDrawable(new
//                    ColorDrawable(android.graphics.Color.TRANSPARENT));
//            progress.setIndeterminate(true);
//            progress.setCancelable(false);
//            progress.show();
//            progress.setContentView(R.layout.progress_dialog);
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//            shh = new ServiceHandler();
//            String url = path + "RegistrationApi/AdminLogin";
//            Log.d("Url: ", "> " + url);
//
//            try{
//                List<NameValuePair> params2 = new ArrayList<>();
//                params2.add(new BasicNameValuePair("Pin",pin1));
//                //params2.add(new BasicNameValuePair("Password",null));
//                params2.add(new BasicNameValuePair("IMEINo",imeino));
//                params2.add(new BasicNameValuePair("SkyStatus","1"));
//
//                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST , params2);
//
//                if (jsonStr != null) {
//                    JSONObject c1 = new JSONObject(jsonStr);
//                    Status = Integer.parseInt(c1.getString("Status"));
//                    Message = (c1.getString("Message"));
//
//                }
//                else
//                {
//                    //Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
//                }
//            }
//            catch (JSONException e)
//            {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            progress.dismiss();
//
//            if (Status == 1)
//            {
//                Toast.makeText(getActivity(), "Login Successfully", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getActivity(),MainActivity.class);
//                intent.putExtra("a1",imeino);
//                intent.putExtra("a2",operatorno);
//                intent.putExtra("a3",cmonth);
//                intent.putExtra("a4",cyear);
//                intent.putExtra("a5",pathIp);
//                startActivity(intent);
//                textViewpin1.setText("");
//                textViewpin2.setText("");
//                textViewpin3.setText("");
//                textViewpin4.setText("");
//                //finish();
//            }
//            else {
//                Toast.makeText(getActivity(), "" + Message, Toast.LENGTH_LONG).show();
//                textViewpin1.setText("");
//                textViewpin2.setText("");
//                textViewpin3.setText("");
//                textViewpin4.setText("");
//            }
//        }
//
//
//    }
}
