package com.example.uas;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Myclass> {

    SecondPage secondPage;
    ArrayList<String> imgArraylist;

    public RecyclerViewAdapter(SecondPage secondPage, ArrayList<String> imgArraylist) {
        this.imgArraylist=imgArraylist;
        this.secondPage=secondPage;
    }

    //ViewHolder berfungsi untuk menyimpan view-view yang akan di gunakan pada item,
    // view-view disini bisa berupa ImageView, TextView, EditText, Dll.
    // ViewHolder adalah class static yang terdapat pada adapter, jadi setiap
    // komponen yang kita buat, kita harus menginisialisasikannya didalam ViewHolder.

    @NonNull
    @Override
    public Myclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(secondPage).inflate(R.layout.recycler_image_item,parent,false);
        return new Myclass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myclass holder, int position) {

        //Glide adalah sumber terbuka perpustakaan Android yang populer untuk memuat gambar, video, dan GIF animasi.
        //sebelum menggunakan glide di sini saya terlebih dahulu menambahkan library untuk glide pada gradle
        //karena library ini berasal dari luar bukan bawaan android studio.
        Glide.with(secondPage).load(imgArraylist.get(position)).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pagerIntent= new Intent(secondPage,WallPagerActivity.class);

                pagerIntent.putExtra("list",imgArraylist);
                pagerIntent.putExtra("pos",position);

                secondPage.startActivity(pagerIntent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return imgArraylist.size();
    }

    //Recycler.ViewHolder ini berfungsi untuk memperluas tampilan yang akan ditampilkan
    //atau bahkan semua data yang tersimpan dalam ArrayList
    class Myclass extends RecyclerView.ViewHolder{

        ImageView imageView;

        public Myclass(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.image);
        }
    }
}
