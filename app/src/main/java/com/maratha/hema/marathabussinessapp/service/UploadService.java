package com.maratha.hema.marathabussinessapp.service;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by Tan on 2/26/2016.
 */
public abstract interface UploadService {


    @Multipart
    @POST("/upload")
    public abstract void upload(@Part("file") TypedFile paramTypedFile, Callback<Response> paramCallback);

    @Multipart
    @POST("/upload")
    public abstract Response uploadSync(@Part("file") TypedFile paramTypedFile);
}
