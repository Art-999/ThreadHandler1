package com.example.arturmusayelyan.threadhandler1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    private final int STATUS_NONE = 0;
    private final int STATUS_CONNECTING = 1;
    private final int STATUS_CONNECTED = 2;
    private final int STATUS_DOWNLOAD_START = 3;
    private final int STATUS_DOWNLOAD_FILE = 4;
    private final int STATUS_DOWNLOAD_END = 5;
    private final int STATUS_DOWNLOAD_NONE = 6;
    private TextView tvStatus;
    private Button btnConnect;
    private ProgressBar progBar;
    private Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvStatus = findViewById(R.id.status_tv);
        btnConnect = findViewById(R.id.connect_btn);
        progBar = findViewById(R.id.prog_bar_download);
        btnConnect.setOnClickListener(this);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case STATUS_NONE:
                        btnConnect.setEnabled(true);
                        tvStatus.setText("Not connected");
                        break;
                    case STATUS_CONNECTING:
                        btnConnect.setEnabled(false);
                        tvStatus.setText("Connecting");
                        break;
                    case STATUS_CONNECTED:
                        tvStatus.setText("Connected");
                        break;
                    case STATUS_DOWNLOAD_START:
                        tvStatus.setText("Start download " + msg.arg1 + " files");
                        progBar.setMax(msg.arg1);
                        progBar.setProgress(0);
                        progBar.setVisibility(View.VISIBLE);
                        break;
                    case STATUS_DOWNLOAD_FILE:
                        tvStatus.setText("Downloading. Left " + msg.arg2 + " files");
                        progBar.setProgress(msg.arg1);
                        saveFile((byte[]) msg.obj);
                        break;
                    case STATUS_DOWNLOAD_END:
                        tvStatus.setText("Download complete!");
                        break;
                    case STATUS_DOWNLOAD_NONE:
                        tvStatus.setText("No files for Download");
                        break;
                }
            }
        };
        handler.sendEmptyMessage(STATUS_NONE);
    }

    @Override
    public void onClick(View v) {
        Thread thread = new Thread(new Runnable() {
            Message message;
            byte[] file;
            // Random random = new Random();

            @Override
            public void run() {
                handler.sendEmptyMessage(STATUS_CONNECTING);
                SystemClock.sleep(1000);

                handler.sendEmptyMessage(STATUS_CONNECTED);
                SystemClock.sleep(1000);

                int filesCount = (int) (Math.random() * 6); // kam random.nexInt(5);
                if (filesCount == 0) {
                    message = handler.obtainMessage(STATUS_DOWNLOAD_START, filesCount, 0);
                    handler.sendMessage(message);
                    return;
                }
                for (int i = 1; i <= filesCount; i++) {
                    file = downloadFile();
                    message = handler.obtainMessage(STATUS_DOWNLOAD_FILE, i, filesCount - i, file);
                    handler.sendMessage(message);
                }
                handler.sendEmptyMessage(STATUS_DOWNLOAD_END);
                SystemClock.sleep(1500);
                handler.sendEmptyMessage(STATUS_NONE);
            }
        });
        thread.start();
    }

    public byte[] downloadFile() {
        SystemClock.sleep(2000);
        return new byte[1024];
    }

    public void saveFile(byte[] file) {

    }
}
