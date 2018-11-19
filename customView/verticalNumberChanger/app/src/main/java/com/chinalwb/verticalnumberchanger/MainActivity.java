package com.chinalwb.verticalnumberchanger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements VerticalNumberChangerView.NumberChangeListener {

    VerticalNumberChangerView mVerticalNumberChangerView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVerticalNumberChangerView = findViewById(R.id.v_number);
        mVerticalNumberChangerView.setNumberChangeListener(this);

        textView = findViewById(R.id.number_now);

        int number = mVerticalNumberChangerView.getCurrentNumber();
        setNumber(number);
    }

    @Override
    public void onNumberChanged( int newNumber) {
        Log.e("XX", "Number changed. new == " + newNumber);
        setNumber(newNumber);
    }

    private void setNumber(int number) {
        textView.setText(String.format("%02d", number));
    }
}
