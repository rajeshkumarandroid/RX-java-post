package org.raaz.com.rxjavapost.Presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Rajesh Kumar on 06-12-2017.
 */


public class Client {

    public static Client client;
    private IntractiveView view;
    private static Context context;
    public static Client getInit(Context context){
        if(client==null){
            client = new Client();
        }
        Client.context = context;
        return client;
    }
    private OkHttpClient getClient(){

        return  new OkHttpClient.Builder()
                .readTimeout(45, TimeUnit.SECONDS)
                .connectTimeout(45, TimeUnit.SECONDS)
                .build();
    }


    public void fetch_GET_Response(final Response response){
        getView().showLoading();
        AndroidNetworking.get(BaseURL.BASEURL_GET)
                .addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        // do anything with response
                        Log.e("response is ","<<getall useres><"+jsonArray);
                         response.Success(jsonArray.toString());
                        getView().hideLoading();
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("error ","<>getall users<"+error.getErrorBody());
                        response.Failure(error.getErrorBody());
                        getView().hideLoading();
                    }
                });
    }
    public void fetch_POST_Response(Map<String ,String > params,final Response response){
        getView().showLoading();
        AndroidNetworking.post(BaseURL.BASEURL_POST)
                //.addparamsBody(params) // posting json
                .addBodyParameter(params)
                .setTag("test")
                .setOkHttpClient(getClient())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.e("response is ","<><>"+jsonObject);
                        response.Success(jsonObject.toString());
                        getView().hideLoading();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error ","<><"+anError.getErrorBody());
                        response.Failure(anError.getErrorBody());
                        getView().hideLoading();
                    }
                });
    }


    private IntractiveView getView(){
       return ((IntractiveView)(context));
    }


}
