package com.chinalwb.scalableimageviewtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button a1;
    Button a2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        a1 = findViewById(R.id.a1);
        a2 = findViewById(R.id.a2);

        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ScalableImageViewActivity.class);
                startActivity(intent);
            }
        });
    }


}
