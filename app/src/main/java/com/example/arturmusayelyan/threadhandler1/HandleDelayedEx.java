package com.example.arturmusayelyan.threadhandler1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HandleDelayedEx extends AppCompatActivity {
    private TextView messageTv;
    private Button sendBtn;
    private Handler handler;

    Handler.Callback handlerCallback=new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            messageTv.setText(msg.what+"");
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_delayed_ex);
        messageTv = findViewById(R.id.show_message_tv);
        handler=new Handler(handlerCallback);
    }


    public void sendMessage(View view) {
        handler.sendEmptyMessageDelayed(1, 1000);
        handler.sendEmptyMessageDelayed(2, 2000);
        handler.sendEmptyMessageDelayed(3, 3000);
        handler.sendEmptyMessageDelayed(7,3500);
        handler.sendEmptyMessageDelayed(4, 4000);
        handler.sendEmptyMessageDelayed(5, 5000);

        handler.removeMessages(7);
    }
}
