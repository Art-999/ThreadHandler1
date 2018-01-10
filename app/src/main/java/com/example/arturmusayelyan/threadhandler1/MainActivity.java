package com.example.arturmusayelyan.threadhandler1;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final int STATUS_START=0;
    private final int STATUS_END=1;
    private final int STATUS_WORKING=2;
    private TextView tv1;
    private Button connectBtn;
    private Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1=findViewById(R.id.tv1);
        connectBtn=findViewById(R.id.connect_btn);

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
//                switch (msg.what){
//                    case STATUS_START:
//                        connectBtn.setEnabled(false);
//
//                        break;
//                    case STATUS_END:
//                        connectBtn.setEnabled(true);
//                        break;
//                    case STATUS_WORKING:
//                        tv1.setText("Connecting status: "+msg.what);
//                        break;
//
//                }
                if(msg.what>5){
                    connectBtn.setEnabled(true);
                }
                else if(msg.what>=0 && msg.what<4){
                    tv1.setText("Connecting status: "+msg.what);
                    connectBtn.setEnabled(false);
                }
                else if(msg.what==4){
                    tv1.setText("Connecting status: "+msg.what);
                    connectBtn.setEnabled(true);
                }
            }
        };
        handler.sendEmptyMessage(8);
    }
    public void onClick(View view){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <5 ; i++) {
                    handler.sendEmptyMessage(i);
                    SystemClock.sleep(1500);
                }
            }
        });
        thread.start();
    }
}
