package com.maratha.hema.marathabussinessapp;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.maratha.hema.marathabussinessapp.service.UploadService;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Tan on 2/26/2016.
 */
public class RestClient {
    private UploadService uploadService;
    private String URL ="http://192.168.0.117:8014/api/";

    public RestClient(){
            Gson localGson = new GsonBuilder().create();

            this.uploadService = ((UploadService)new RestAdapter.Builder()
            .setEndpoint(URL)
            .setConverter(new GsonConverter(localGson))
            .setRequestInterceptor(new RequestInterceptor()
            {
                public void intercept(RequestFacade requestFacade)
                {
                    if (URL.contains("192.168.0.117")) {
                        requestFacade.addHeader("Host", "localhost");
                    }
                }
            })
            .build().create(UploadService.class));

    }



    public UploadService getService()
    {
        return this.uploadService;
    }


}
