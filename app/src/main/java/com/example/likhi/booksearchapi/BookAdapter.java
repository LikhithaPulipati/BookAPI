package com.example.likhi.booksearchapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

    Context ct;
    ArrayList<BookModel> models;
    public BookAdapter(MainActivity mainActivity, ArrayList<BookModel> bookModels) {
        this.ct=mainActivity;
        this.models=bookModels;
    }

    @NonNull
    @Override
    public BookAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(ct).inflate(R.layout.rowdesign,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.MyViewHolder holder, int position) {

      // BookModel bookModel=models.get(position);
        //holder.bimage.setImageResource(Integer.parseInt(models.get(position).getBookimage()));
       /* holder.btitle.setText(models.get(position).getTitle());
        holder.bdesc.setText(models.get(position).getDescription());
       */ Picasso.with(ct).load(models.get(position).getBookimage()).into(holder.bimage);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView bimage;
        TextView btitle,bdesc;
        public MyViewHolder(View itemView) {
            super(itemView);

            bimage=itemView.findViewById(R.id.bookimage);
           /* btitle=itemView.findViewById(R.id.booktitle);
            bdesc=itemView.findViewById(R.id.bookdesc);*/
        }
    }
}
