package com.example.kevin.jarvis.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevin.jarvis.R;

public class MainActivity extends AppCompatActivity {

    EditText ip;
    TextView currentIp;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final SharedPreferences sp = getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ip = (EditText) findViewById(R.id.ip);
        currentIp = (TextView) findViewById(R.id.currentIP);
        update = (Button) findViewById(R.id.update);

        if(sp.getString("ip",null) == null){
            currentIp.setText("There is no ip yet !");
        }
        else{
            currentIp.setText("The current IP is : http://" + sp.getString("ip",null));
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ip.getText().toString().length() != 0){
                    sp.edit().putString("ip", ip.getText().toString()).apply();
                    currentIp.setText("The current IP is http://: " + sp.getString("ip",null));
                }
                else{
                    Toast.makeText(getApplicationContext(),"Set an IP !",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
