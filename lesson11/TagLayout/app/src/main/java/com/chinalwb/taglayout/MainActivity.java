package com.chinalwb.taglayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.chinalwb.taglayout.v1.ColorTextView;
import com.chinalwb.taglayout.v1.TagLayout;
import com.chinalwb.taglayout.v2.FlowLayout;
import com.chinalwb.taglayout.v2.SquareImageView;

public class MainActivity extends AppCompatActivity {

    private static final String[] PROVINCES = {
            "河南省", "内蒙古自治区", "宁夏回族自治区", "新疆维吾尔自治区", "河北省", "陕西省",
            "内蒙古自治区", "宁夏回族自治区", "新疆维吾尔自治区", "河北省", "陕西省",
            "石家庄", "保定市", "秦皇岛", "唐山市", "邯郸市", "邢台市", "沧州市", "承德市", "廊坊市", "衡水市", "张家口", "太原市", "大同市", "阳泉市", "长治市", "临汾市", "晋中市", "运城市", "晋城市", "忻州市", "朔州市", "吕梁市",
            "澳门特别行政区", "香港特别行政区"
    };

    private TagLayout tagLayout;

    private FlowLayout flowLayout;

    private SquareImageView squareImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        testClick();

//        test();

//        flowLayout = findViewById(R.id.flow_layout);

//        addProvinces();
    }

    private void testClick() {
        View view1 = findViewById(R.id.view_1);
        View view2 = findViewById(R.id.view_2);

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "View - Left", Toast.LENGTH_SHORT).show();
            }
        });

        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "View - right", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void test () {
//        squareImageView = findViewById(R.id.square_image_view);
//        squareImageView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                squareImageView.changeSize();
//            }
//        }, 2000);
    }

    private void addProvinces() {
        for (String x : PROVINCES) {
            ColorTextView colorTextView = new ColorTextView(this);
            colorTextView.setText(x);
            this.flowLayout.addView(colorTextView);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.w("XX", "MainActivity >> dispatch touch event");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.w("XX", "MainActivity >> onTouchEvent");
        return super.onTouchEvent(event);
    }
}
