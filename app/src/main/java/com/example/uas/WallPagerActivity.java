package com.example.uas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class WallPagerActivity extends AppCompatActivity {

    int pos;
    ArrayList<String> allImageList= new ArrayList<>();
    //ViewPager komponen android yang sering dipakai untuk menampilkan data dalam format full screen dan user
    // bisa berpindah antar data dengan menggeser ke kiri atau kekanan
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_pager);

        pos=getIntent().getIntExtra("pos", 0);
        allImageList=getIntent().getStringArrayListExtra("list");
        viewpager=findViewById(R.id.viewPager);

        PagerAdapter pagerAdapter = new PageAdapter(WallPagerActivity.this,allImageList);
        viewpager.setAdapter(pagerAdapter);
        viewpager.setCurrentItem(pos);
    }
}