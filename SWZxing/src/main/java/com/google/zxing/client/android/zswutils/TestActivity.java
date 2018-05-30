package com.google.zxing.client.android.zswutils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.R;

import org.w3c.dom.Text;

public class TestActivity extends AppCompatActivity {

    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        button = (Button) findViewById(R.id.btn);
        textView = (TextView)findViewById(R.id.texttt);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestActivity.this, CaptureActivity.class);
                intent.putExtra("aa",100);
                startActivityForResult(intent,100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){
            if(resultCode == 2){
                String res = data.getStringExtra("data");

                if(!TextUtils.isEmpty(res)){
                    textView.setText(res);
                }

            }
        }
    }
}
