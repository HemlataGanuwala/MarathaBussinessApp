package com.example.hema.marathabussinessapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    String imgDecodableString;
    Button buttonLoadImage;
    View view;
   Button buttonregistration;
   String name,mobno,phoneno,city,shopname,address,email,path;
   Spinner spinneroccupation;
   EditText editTextname,editTextmobno,editTextphoneno,editTextcity,editTextshopname,editTextaddress,editTextemail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

Button button=(Button)findViewById(R.id.btnbrowse);
button.setOnClickListener((new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE );
    }
}));

//       Button button=(Button) findViewById(R.id.btnbrowse);
//        button.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               Intent intent=new Intent( Intent.ACTION_PICK,
//                       android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            startActivityForResult(intent, RESULT_LOAD_IMAGE);
//           }
//     });
 }

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE );
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//            Cursor cursor = getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//
//            ImageView imageView = (ImageView) findViewById(R.id.imgView);
//            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//
//
//
//        }
//        else {
//            Toast.makeText(this,"You haven't picked Image",
//                    Toast.LENGTH_LONG).show();
//        }
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imageView = (ImageView) findViewById(R.id.imgView);
                // Set the Image in ImageView after decoding the String
                imageView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }


    }
}
