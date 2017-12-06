package org.raaz.com.rxjavapost;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.raaz.com.rxjavapost.Presenter.Client;
import org.raaz.com.rxjavapost.Presenter.IntractiveView;
import org.raaz.com.rxjavapost.Presenter.Response;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Rajesh Kumar on 06-12-2017.
 */
public class MainActivity extends AppCompatActivity implements IntractiveView {

    Unbinder unbinder;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder= ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
    }
    @OnClick(R.id.btn_get)
    public void fetchGetRequest(){
        Log.e("calling click","<><>");
        Client.getInit(this).fetch_GET_Response(new Response() {
            @Override
            public void Success(String response) {
                Log.e("response is ","<<getall useres><"+response);
                ((TextView)findViewById(R.id.txt)).setText(response);
            }

            @Override
            public void Failure(String respose) {
                Log.e("error ","<>getall users<"+respose);
            }
        });
    }


    @OnClick(R.id.btn_post)
    public void getPostRequest(){
        Client.getInit(this).fetch_POST_Response(getPostedParams(), new Response() {
            @Override
            public void Success(String response) {
                Log.e("response is ","<>success<>"+response);
                ((TextView)findViewById(R.id.txt)).setText(response);
            }

            @Override
            public void Failure(String respose) {
                Log.e("error ","<><"+respose);
            }
        });
    }
    private Map<String ,String > getPostedParams(){
        Map<String,String > params = new HashMap<>();
            params.put("action","finance_spinners");
            params.put("design_version", "1");
            params.put("api_id", "cte2017v8.7");
            params.put("version_code", "" + BuildConfig.VERSION_CODE);
            params.put("dealer_id", "5995");
            params.put("app_code", "e7856867-2898-4190-8fc5-aea22c2d8dd0,");
        return params;
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbinder.unbind();
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }
}
