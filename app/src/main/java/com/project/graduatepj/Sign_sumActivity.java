package com.project.graduatepj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Sign_sumActivity extends AppCompatActivity {
    private Button bt;
    private Button bt2;
    private RESTfulApi resTfulApi;

    TextView nurse,transfer,bloodnum,bloodtype,transop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_sum);

        Bundle bundle = getIntent().getExtras();
        bt = findViewById(R.id.nextbt);
        bt2 = findViewById(R.id.frontbt);
        nurse = findViewById(R.id.nurse);
        transfer = findViewById(R.id.transfer);
        bloodnum = findViewById(R.id.bloodnum);
        bloodtype = findViewById(R.id.bloodtype);
        transop = findViewById(R.id.transop);

        String nurseman = bundle.getString("nurse");
        String transferman = bundle.getString("transfer");
        String bloodnumcount = bundle.getString("bloodnum");
        String patient = bundle.getString("patient");
        String transoper = bundle.getString("transop");

        nurse.setText(nurseman);
        transfer.setText(transferman);
        bloodnum.setText(bloodnumcount);
        transop.setText(transoper);


        Retrofit retrofit = new Retrofit.Builder() //api連接
                .baseUrl("http://140.136.151.75:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        resTfulApi = retrofit.create(RESTfulApi.class);

        Get_staff(retrofit,patient);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign_sumActivity.this,blood_homeActivity.class);
                startActivity(intent);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign_sumActivity.this,SignActivity.class);
                startActivity(intent);
            }
        });
    }

    public void Get_staff(Retrofit retrofit,String id){
        Call<Patient_Api> call = resTfulApi.getOne(id); //A00010

        call.enqueue(new Callback<Patient_Api>() {
            @Override
            public void onResponse(Call<Patient_Api> call, Response<Patient_Api> response) {
                if (response.body()==null) {
                    bloodtype.setText("恭喜沒有血型！");
                    return;
                }
                String type = response.body().getBloodType();
                bloodtype.setText(type);
            }

            @Override
            public void onFailure(Call<Patient_Api> call, Throwable t) {
                bloodtype.setText("no bloodtype");
            }
        });
    }
}