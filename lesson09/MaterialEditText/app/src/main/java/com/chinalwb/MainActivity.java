package com.chinalwb;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chinalwb.drawable.FloatingTextView;
import com.chinalwb.materialedittext.R;
import com.chinalwb.me2.MaterialEditText2;

public class MainActivity extends AppCompatActivity {

//    private MaterialEditText materialEditText;
//    private Button button;

//    private FloatingTextView floatingTextView;

    private MaterialEditText2 m2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

//        materialEditText = findViewById(R.id.material_edit_text);
//        button = findViewById(R.id.toggle_button);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                materialEditText.setUseFloatingLabel(!materialEditText.isUseFloatingLabel());
//            }
//        });
    }


    private void init() {
//        floatingTextView = findViewById(R.id.floating_view);
//        m2 = findViewById(R.id.me_2);

//        toggleFloatingLabel();
    }

    private void toggleFloatingLabel() {
        m2.postDelayed(new Runnable() {
            @Override
            public void run() {
                m2.setUseFloatingLabel(!m2.getUseFloatingLabel());
                toggleFloatingLabel();
            }
        }, 10000);
    }
}
