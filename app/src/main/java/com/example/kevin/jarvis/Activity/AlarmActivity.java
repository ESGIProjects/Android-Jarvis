package com.example.kevin.jarvis.Activity;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kevin.jarvis.R;
import com.example.kevin.jarvis.Tools.ApiCall;
import com.example.kevin.jarvis.Tools.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kevin on 25/04/2017.
 */

public class AlarmActivity extends AppCompatActivity {

    EditText password;
    Button send;
    ProgressDialog pd;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        password = (EditText) findViewById(R.id.password);
        send = (Button) findViewById(R.id.send);
        sp = getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(1);

        final String url = "http://" + sp.getString("ip",null);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw = password.getText().toString();
                if(pw.length() != 4){
                    Toast.makeText(AlarmActivity.this,"Wrong format !", Toast.LENGTH_SHORT).show();
                }
                else if(pw.length() == 0){
                    Toast.makeText(AlarmActivity.this,"You must type a password !", Toast.LENGTH_SHORT).show();
                }
                else{
                    pd = new ProgressDialog(AlarmActivity.this);
                    pd.setMessage("Wait...");
                    pd.show();

                    RetrofitInstance.getInstance(url).create(ApiCall.class)
                            .login("application/x-www-form-urlencoded",password.getText()
                                    .toString()).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            pd.dismiss();
                            Log.i("RESULT : ", response.body());
                            if(response.body().trim().equals("success")){

                                Toast.makeText(AlarmActivity.this,"The alarm is off !",Toast.LENGTH_SHORT).show();
                                finishAffinity();
                            }
                            else if(response.body().trim().equals("failure")){
                                Toast.makeText(AlarmActivity.this,"Wrong password !",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            pd.dismiss();
                            Log.i("FAILURE : ", t.getMessage());
                            Toast.makeText(getApplicationContext(),"Retry !",Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
    }
}
