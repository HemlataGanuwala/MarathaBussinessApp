package com.maratha.hema.marathabussinessapp.CustomerRegistrtion;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maratha.hema.marathabussinessapp.CancelableCallback;
import com.maratha.hema.marathabussinessapp.GlobalClass;
import com.maratha.hema.marathabussinessapp.PhotoEditActivity;
import com.maratha.hema.marathabussinessapp.R;
import com.maratha.hema.marathabussinessapp.RestClient;
import com.maratha.hema.marathabussinessapp.ServiceHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class DocumentPhotoUploadActivity extends AppCompatActivity {

    Button buttonuploaddoc,buttonuploadreceipt,buttonuploadprod1,buttonuploadprod2,btnUpload;
    ImageView imageViewdoc,imageViewreceipt,imageViewproduct1,imageViewproduct2;
    Uri targetUri;
    Bitmap bitmap;
    TextView textViewupload;
    private RestClient restClient;
    private String imagePath="";
    private File savedFileDestination;
    String img = "";
    ProgressDialog mProgress;
    ProgressDialog progress;
    ServiceHandler shh;
    String path,custname,doc,resp,pro1,pro2,nmbusi,address,contact,typebusi,email,website,pay,about,service, id;
    int Status = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_photo_upload);

        final GlobalClass globalVariable = (GlobalClass)getApplicationContext();
        path = globalVariable.getconstr();

        restClient = new RestClient();
        buttonuploaddoc = (Button)findViewById(R.id.btnuploaddoc);
        buttonuploadreceipt = (Button)findViewById(R.id.btnuploadreceipt);
        buttonuploadprod1 = (Button)findViewById(R.id.btnuploadproduct1);
        buttonuploadprod2 = (Button)findViewById(R.id.btnuploadproduct2);
        imageViewdoc = (ImageView) findViewById(R.id.imgupload1);
        imageViewreceipt = (ImageView)findViewById(R.id.imgupload2);
        imageViewproduct1 = (ImageView)findViewById(R.id.imgupload3);
        imageViewproduct2 = (ImageView)findViewById(R.id.imgupload4);
        textViewupload = (TextView) findViewById(R.id.tvupload);
        btnUpload = (Button)findViewById(R.id.btnregupload);

        Display();

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (doc != null && resp != null)
                    {
                        new GetInsertData().execute();
                    }
                    else
                    {
                        Toast.makeText(DocumentPhotoUploadActivity.this, "First Attach Document and Receipt", Toast.LENGTH_LONG).show();
                    }
                }


        });

        buttonuploaddoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img = "btnimg1";
                isStoragePermissionGranted();

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select picture"), 1000 );
            }
        });

        buttonuploadreceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img = "btnimg2";
                isStoragePermissionGranted();

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select picture"), 1000 );
            }
        });

        buttonuploadprod1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img = "btnimg3";
                isStoragePermissionGranted();

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select picture"), 1000 );
            }
        });

        buttonuploadprod2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img = "btnimg4";
//                Intent intent = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, 0);
                isStoragePermissionGranted();

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select picture"), 1000 );
            }
        });


    }


    private void initiateProgressDialog(){
        mProgress = new ProgressDialog(DocumentPhotoUploadActivity.this);
        mProgress.setMessage("Uploading files...");
        mProgress.setCancelable(true);

        //setButton is depreciated, it's tell us is not good to cancel when something is running at the back. :). Think about it.
        //When cancel button clicked, it will ignore the response from server
        //You could see this from video
        mProgress.show();
    }

    private void uploadImage(final ImageView imgPhoto){
        if (savedFileDestination==null) {
            Toast.makeText(DocumentPhotoUploadActivity.this,"Please take photo first", Toast.LENGTH_LONG).show();
            return;
        }

        TypedFile typedFile = new TypedFile("multipart/form-data", savedFileDestination);
        initiateProgressDialog();

        restClient.getService().upload(typedFile, new CancelableCallback<Response>() {
            @Override
            public void onSuccess(Response response, Response response2) {
                mProgress.dismiss();
                Picasso.with(DocumentPhotoUploadActivity.this)
                        .load(savedFileDestination)
                        .into(imgPhoto);


                Toast.makeText(DocumentPhotoUploadActivity.this,"Upload successfully",Toast.LENGTH_LONG).show();
                Log.e("Upload", "success");
            }

            @Override
            public void onFailure(RetrofitError error) {
                mProgress.dismiss();
                Toast.makeText(DocumentPhotoUploadActivity.this,"Upload failed",Toast.LENGTH_LONG).show();
                Log.e("Upload", error.getMessage().toString());
            }
        });


    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                // Log.v(TAG,"Permission is granted");
                return true;
            } else {

                //  Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(DocumentPhotoUploadActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            // Log.v(TAG,"Permission is granted");
            return true;
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//        if (resultCode == RESULT_OK){
//            targetUri = data.getData();
//            savedFileDestination = new File(getRealPathFromURI(this,targetUri));
//        }
//    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getRealPathFromURI(Context context, Uri uri) {
        String filePath = "";

        // ExternalStorageProvider
        if (isExternalStorageDocument(uri)) {
            final String docId = DocumentsContract.getDocumentId(uri);
            final String[] split = docId.split(":");
            final String type = split[0];

            if ("primary".equalsIgnoreCase(type)) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else {

                if (Build.VERSION.SDK_INT > 20) {
                    //getExternalMediaDirs() added in API 21
                    File extenal[] = context.getExternalMediaDirs();
                    if (extenal.length > 1) {
                        filePath = extenal[1].getAbsolutePath();
                        filePath = filePath.substring(0, filePath.indexOf("Android")) + split[1];
                    }
                }else{
                    filePath = "/storage/" + type + "/" + split[1];
                }
                return filePath;
            }

        } else if (isDownloadsDocument(uri)) {
            // DownloadsProvider
            final String id = DocumentsContract.getDocumentId(uri);
            //final Uri contentUri = ContentUris.withAppendedId(
            // Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

            Cursor cursor = null;
            final String column = "_data";
            final String[] projection = {column};

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int index = cursor.getColumnIndexOrThrow(column);
                    String result = cursor.getString(index);
                    cursor.close();
                    return result;
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } else if (DocumentsContract.isDocumentUri(context, uri)) {
            // MediaProvider
            String wholeID = DocumentsContract.getDocumentId(uri);

            // Split at colon, use second item in the array
            String[] ids = wholeID.split(":");
            String id;
            String type;
            if (ids.length > 1) {
                id = ids[1];
                type = ids[0];
            } else {
                id = ids[0];
                type = ids[0];
            }

            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(type)) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }

            final String selection = "_id=?";
            final String[] selectionArgs = new String[]{id};
            final String column = "_data";
            final String[] projection = {column};
            Cursor cursor = context.getContentResolver().query(contentUri,
                    projection, selection, selectionArgs, null);

            if (cursor != null) {
                int columnIndex = cursor.getColumnIndex(column);

                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(columnIndex);
                }
                cursor.close();
            }
            return filePath;
        } else {
            String[] proj = {MediaStore.Audio.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                if (cursor.moveToFirst())
                    filePath = cursor.getString(column_index);
                cursor.close();
            }


            return filePath;
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (img) {
            case "btnimg1":
                if (resultCode == RESULT_OK){
                    targetUri = data.getData();
                    //textViewupload.setText(targetUri.toString());
                    savedFileDestination = new File(getRealPathFromURI(this,targetUri));

                    uploadImage(imageViewdoc);
                    doc = savedFileDestination.toString();

                    //new GetInsertData().execute();



                    //do in background

//                    try {
//                        bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(targetUri));
//                        imageView1.setImageBitmap(bitmap);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                }
                break;
            case "btnimg2":
                if (resultCode == RESULT_OK){
                    targetUri = data.getData();
                    //textViewupload.setText(targetUri.toString());
                    savedFileDestination = new File(getRealPathFromURI(this,targetUri));

                    uploadImage(imageViewreceipt);

                    resp = savedFileDestination.toString();

                   // new GetInsertData().execute();




//                    try {
//                        bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(targetUri));
//                        imageView2.setImageBitmap(bitmap);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                }
                break;
            case "btnimg3":
                if (resultCode == RESULT_OK){
                    targetUri = data.getData();
                    //textViewupload.setText(targetUri.toString());
                    savedFileDestination = new File(getRealPathFromURI(this,targetUri));

                    uploadImage(imageViewproduct1);

                    pro1 = savedFileDestination.toString();

                    //new GetInsertData().execute();

//                    try {
//                        bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(targetUri));
//                        imageView3.setImageBitmap(bitmap);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                }
                break;
            case "btnimg4":
                if (resultCode == RESULT_OK){
                    targetUri = data.getData();
                    //textViewupload.setText(targetUri.toString());
                    savedFileDestination = new File(getRealPathFromURI(this,targetUri));

                    uploadImage(imageViewproduct2);

                    pro2 = savedFileDestination.toString();

                   // new GetInsertData().execute();

//                    try {
//                        bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(targetUri));
//                        imageView4.setImageBitmap(bitmap);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                }
                break;
        }


    }

    public void Display()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle !=null)
        {
            custname = (String) bundle.get("CustName");
            nmbusi = (String) bundle.get("NmBusiness");
            address = (String) bundle.get("Address");
            contact = (String) bundle.get("Contact");
            email = (String) bundle.get("Email");
            website = (String) bundle.get("Website");
            about = (String) bundle.get("About");
            service = (String) bundle.get("Service");
            pay = (String) bundle.get("Bestprice");
            typebusi = (String) bundle.get("TypeBusiness");
        }

    }

    public class GetInsertData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            progress = new ProgressDialog(DocumentPhotoUploadActivity.this);
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

            String url = path + "RegistrationApi/ProductImage";

            Log.d("Url: ", "> " + url);

            try {
                // Making a request to url and getting response

                List<NameValuePair> para = new ArrayList<>();
                // para.add(new BasicNameValuePair("CustBal", balance));
//                para.add(new BasicNameValuePair("CustName", id));
                para.add(new BasicNameValuePair("Name", custname));
                para.add(new BasicNameValuePair("Product1", doc));
                para.add(new BasicNameValuePair("Product2", resp));
                para.add(new BasicNameValuePair("Product3", pro1));
                para.add(new BasicNameValuePair("Product4", pro2));


                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, para);
                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    String msg = jObj.getString("Message");
                    Status = Integer.parseInt(jObj.getString("Status"));


                } else {
                    Toast.makeText(DocumentPhotoUploadActivity.this, "Data not Found", Toast.LENGTH_LONG).show();
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new GetInsertRegData().execute();
                        //Toast.makeText(DocumentPhotoUploadActivity.this, "Register succesfully", Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                Toast.makeText(DocumentPhotoUploadActivity.this, "Register Failed", Toast.LENGTH_LONG).show();
            }



        }
    }

    public class GetInsertRegData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            progress = new ProgressDialog(DocumentPhotoUploadActivity.this);
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
//                para.add(new BasicNameValuePair("Bid", id));
                para.add(new BasicNameValuePair("Name", custname));
                para.add(new BasicNameValuePair("NameofBusiness", nmbusi));
                para.add(new BasicNameValuePair("TypeofBusiness", typebusi));
                para.add(new BasicNameValuePair("Contact", contact));
                para.add(new BasicNameValuePair("Address", address));
                para.add(new BasicNameValuePair("Email", email));
                para.add(new BasicNameValuePair("Website", website));
                para.add(new BasicNameValuePair("AboutBusiness", about));
                para.add(new BasicNameValuePair("Services", service));
                para.add(new BasicNameValuePair("BestPrice", pay));
//                para.add(new BasicNameValuePair("Document", image_str));
                para.add(new BasicNameValuePair("Status", "0"));


                String jsonStr = shh.makeServiceCall(url, ServiceHandler.POST, para);
                if (jsonStr != null) {
                    JSONObject jObj = new JSONObject(jsonStr);
                    String msg = jObj.getString("Message");
                    Status = Integer.parseInt(jObj.getString("Status"));


                } else {
                    Toast.makeText(DocumentPhotoUploadActivity.this, "Data not Found", Toast.LENGTH_LONG).show();
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DocumentPhotoUploadActivity.this, "Register succesfully", Toast.LENGTH_LONG).show();

                    }
                });

            } else {
                Toast.makeText(DocumentPhotoUploadActivity.this, "Register Failed", Toast.LENGTH_LONG).show();
            }



        }
    }
}
