package com.example.uas;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.chootdev.blurimg.BlurImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;


//jembatan antara AdapterView dengan data.
public class PageAdapter extends PagerAdapter {

    WallPagerActivity wallPagerActivity;
    ArrayList<String> allImageList;

    public PageAdapter(WallPagerActivity wallPagerActivity, ArrayList<String> allImageList) {
        this.allImageList=allImageList;
        this.wallPagerActivity=wallPagerActivity;
    }

    @Override
    public int getCount() {
        return allImageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override //method untuk menginisiasi objeck yang akan digunakan
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(wallPagerActivity).inflate(R.layout.page_adapter_irem,container,false);
        ImageView imageView;
        ImageView simpleImg;
        ImageView share;
        ImageView setwall;
        imageView = view.findViewById(R.id.blurImage);
        simpleImg = view.findViewById(R.id.simpleImage);
        share = view.findViewById(R.id.share);
        setwall = view.findViewById(R.id.setWall);

        //Glide untuk memanggil semua data array agar ditampilkan dalam layout page_adapter
        Glide.with(wallPagerActivity).load(allImageList.get(position)).into(simpleImg);

        //BlurImage sama saja seperti Glide yaitu berfungsi untuk memblur gambar/foto yang ada,
        //sebelum memakainya karena BlurImage ini bukan bawaan android studio maka terlebih dahulu
        //menambahkan librarynya pada gradle
        BlurImage.withContext(wallPagerActivity).blurFromUri(allImageList.get(position)).into(imageView);



        //memnaggil method share yang telah dibuat ketika button share diklik
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new shareImage().execute(allImageList.get(position));

            }
        });

        ////memanggil method setWallpaper yang telah dibuat ketika button set diklik
        setwall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new setWallPaper().execute(allImageList.get(position));

            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }


    //AsyncTask -> Sebuah class yang disediakan Android untuk proses/operasi pengambilan/pengiriman yang dilakukan secara background.
    //Biasanya kita menggunakan AsyncTask saat mengambil data dari API. Proses pengambilan datanya
    //terjadi di background dan di UI nya hanya menampilkan ProgressDialog.

    //METHOD setWallPaper agar gambar yang kita pilih diterapkan menjadi wallpaper smarthphone
    class setWallPaper extends AsyncTask<String, Void, Bitmap> {

        Random random;
        int var;

        @Override //method yang akan dilakukan ketika doInBackground telah selesai dieksekusi
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //sebuah toast untuk memunculkan pesan
            Toast.makeText(wallPagerActivity, "Wallpaper set Success", Toast.LENGTH_SHORT).show();

        }

        @Override //Pada method ini proses thread berjalan, proses pengiriman/pengambilan data terjadi disini.
        protected Bitmap doInBackground(String... urls) {

            random = new Random();
            var = random.nextInt(10000);

            try {

                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                WallpaperManager myWallpaperManager
                        = WallpaperManager.getInstance(wallPagerActivity.getApplicationContext());
                try {
                    myWallpaperManager.setBitmap(myBitmap);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            } catch (Exception e) {

                e.printStackTrace();

            }
            return null;
        }
    }


    //Method untuk tombol share image.
    class shareImage extends AsyncTask<String, Void, Bitmap> {

        @Override //Method ini digunakan untuk mengupdate User Interface ketika proses doInBackground(Void… params) telah selesai (berjalan di Thread UI).
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Toast.makeText(wallPagerActivity, "Share Successfully", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);


                Bitmap b = myBitmap;
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                share.setType("text/html");
                share.putExtra(Intent.EXTRA_TEXT, "shre body");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(wallPagerActivity.getContentResolver(),
                        b, "Title", null);
                Uri imageUri = Uri.parse(path);
                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                wallPagerActivity.startActivity(Intent.createChooser(share, "Select"));


            } catch (Exception e) {

                e.printStackTrace();

            }

            return null;
        }
    }
}
