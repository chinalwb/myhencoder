package com.chinalwb.taglayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    private static final String[] PROVINCES = {
            "河南省", "内蒙古自治区", "宁夏回族自治区", "新疆维吾尔自治区", "河北省", "陕西省",
            "内蒙古自治区", "宁夏回族自治区", "新疆维吾尔自治区", "河北省", "陕西省",
            "石家庄", "保定市", "秦皇岛", "唐山市", "邯郸市", "邢台市", "沧州市", "承德市", "廊坊市", "衡水市", "张家口", "太原市", "大同市", "阳泉市", "长治市", "临汾市", "晋中市", "运城市", "晋城市", "忻州市", "朔州市", "吕梁市",
            "澳门特别行政区", "香港特别行政区"
    };

    private TagLayout tagLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tagLayout = findViewById(R.id.tag_layout);

        addProvinces();
    }

    private void addProvinces() {
        for (String x : PROVINCES) {
            ColorTextView colorTextView = new ColorTextView(this);
            colorTextView.setText(x);
            this.tagLayout.addView(colorTextView);
        }
    }
}
