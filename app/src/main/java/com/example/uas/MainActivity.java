package com.example.uas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //MainActivity. java saya gunakan untuk code splash screen yang ada pada awal ketika
    //membuka aplikasi begitupun dengan layoutnya.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //method postDelayed yang diambil dari objek handler dimana method ini akan menjalankan
        // statemen setelah delay yang telah ditentukan berakhir
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //code untuk berpindah halaman
                Intent mainintent = new Intent(MainActivity.this,SecondPage.class);
                startActivity(mainintent);
                finish();

            }
        }, 2000); //waktu delay yang diambil yaitu 2 detik
    }
}