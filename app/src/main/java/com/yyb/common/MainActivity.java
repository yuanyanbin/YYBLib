package com.yyb.common;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.yyb.yyblib.remote.ReqCallBack;
import com.yyb.yyblib.util.GsonUtil;
import com.yyb.yyblib.util.LogUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpManager.getInstance(MainActivity.this).getCheckForceUpdate(new ReqCallBack<String>() {
                    @Override
                    public void onReqSuccess(String result) {
                        LogUtil.e(result);
                    }

                    @Override
                    public void onReqFailed(int errCode, String errorMsg) {
                        LogUtil.e(errorMsg);
                    }
                });
            }
        });
    }
}
