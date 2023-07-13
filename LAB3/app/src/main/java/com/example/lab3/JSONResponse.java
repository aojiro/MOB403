package com.example.lab3;

import retrofit2.Call;
import retrofit2.http.GET;

public class JSONResponse {
    private AndroidVersion[] android;

    public AndroidVersion[] getAndroid() {
        return android;
    }
}
