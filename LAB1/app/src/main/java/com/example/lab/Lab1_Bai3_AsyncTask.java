package com.example.lab;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Lab1_Bai3_AsyncTask extends AsyncTask<String,Void, Bitmap> {
    ProgressDialog progressDialog; //su dung tien trinh.
    Listener mListener; //su dung interface

    @Override
    protected Bitmap doInBackground(String... strings) {
        URL url;
        Bitmap bitmap= null;
        try{
            url =  new URL(strings[0]);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public interface Listener {
        void onImageLoader(Bitmap bitmap);
        void onError();
    }

    public Lab1_Bai3_AsyncTask(Listener listener, Context context){
        this.mListener =listener;
        this.progressDialog = new ProgressDialog(context);

    }
    //sau khi thuc hien
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        if (bitmap !=null){
            mListener.onImageLoader(bitmap);
        }else {
            mListener.onError();
        }
    }

}