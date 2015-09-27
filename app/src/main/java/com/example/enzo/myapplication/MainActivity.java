package com.example.enzo.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.loopj.android.http.*;
import org.json.*;
import cz.msebera.android.httpclient.*;

public class MainActivity extends AppCompatActivity {

    String inputName=null;
    String inputJob=null;
    EditText name,job;
    Button saveData,loadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=(EditText)findViewById(R.id.nameTxt);
        job=(EditText)findViewById(R.id.jobTxt);
        saveData=(Button)findViewById(R.id.saveBtn);
        loadData=(Button)findViewById(R.id.listBtn);


        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputName = name.getText().toString();
                inputJob = job.getText().toString();
                postApi(inputName, inputJob);
            }
        });

        loadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApi();
            }
        });
    }

    public void postApi(String inputName,String  inputJob){
        RequestParams params = new RequestParams();
        params.put("name", inputName);
        params.put("job", inputJob);
        RestClient.post("insert.php", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String message = response.getString("message");
                    Log.d("status", "" + statusCode);
                    Log.d("message", message);
                } catch (JSONException e) {
                    Log.d("err:", "" + e); //error in the above string(in this case,yes)!
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //wrapper for error code 4xx
            }
        });
    }

    public void getApi(){
        //RequestParams params = new RequestParams(); //use if u need query with key and value
        //params.put("name", inputName);
        //params.put("job", inputJob);
        RestClient.get("fetch.php", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                
                    Log.d("status", "" + statusCode);
                    Log.d("result: ", response.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //wrapper for error code 4xx
            }
        });
    }


}
