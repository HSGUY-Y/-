package com.example.end;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dispose.Dispose;
import com.entity.CommonResult;
import com.entity.CommonVo;
import com.entity.UserEntity;

public class Set extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    private LinearLayout deleteAcc;
    private TextView account;
    private LinearLayout logout;
    private LinearLayout close;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set);
        back=findViewById(R.id.back);
        deleteAcc = findViewById(R.id.deleteAcc);
        account = findViewById(R.id.account);
        logout = findViewById(R.id.logout);
        close = findViewById(R.id.close);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //
        Intent intent = getIntent();
        String accounts =   intent.getStringExtra("account");
        account.setText(accounts);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        back.setOnClickListener(this);
        deleteAcc.setOnClickListener(this);
        logout.setOnClickListener(this);
        close.setOnClickListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.deleteAcc:
                Intent intent = new Intent(Set.this,Deleteaccount.class);
                intent.putExtra("account",account.getText().toString());
                startActivity(intent);
                finish();
                break;
            case R.id.logout:
                Intent intent1 = new Intent(Set.this,MainActivity.class);
                intent1.putExtra("account",account.getText().toString());
                startActivity(intent1);
                mainUI.mainui.finish();
                Set.this.finish();
                break;
            case R.id.close:
                finish();
                mainUI.mainui.finish();
                MainActivity.mainActivity.finish();
                break;
        }
    }
}
