package com.example.likhi.booksearchapi;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    String bookurl="https://www.googleapis.com/books/v1/volumes?q=";
    EditText searchname;
    Button getbook;
    RecyclerView recyclerView;
    ArrayList<BookModel> bookModels;
    ProgressDialog dialog;

    int book_Loader_ID=27;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchname=findViewById(R.id.bookname);
        getbook=findViewById(R.id.book);
        recyclerView=findViewById(R.id.recycler);
        bookModels=new ArrayList<>();
        getbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookModels.clear();
                //new BookTask().execute();
                getSupportLoaderManager().initLoader(book_Loader_ID,null,MainActivity.this);
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        //BookAdapter bookAdapter=new BookAdapter(this,bookModels);
        recyclerView.setAdapter(new BookAdapter(this,bookModels));
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            @Nullable
            @Override
            public String loadInBackground() {
                try {
                    URL url=new URL(bookurl+searchname.getText().toString());
                    Log.i("bookurl",url.toString());
                    HttpURLConnection urlConnection=(HttpURLConnection)
                            url.openConnection();
                    InputStream inputStream=urlConnection.getInputStream();
                    Scanner scanner=new Scanner(inputStream);
                    scanner.useDelimiter("\\A");
                    if (scanner.hasNext()){
                        return scanner.next();
                    }else {
                        return null;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                dialog=new ProgressDialog(MainActivity.this);
                dialog.setMessage("Loading...!");
                dialog.setCancelable(false);
                dialog.show();

                forceLoad();
            }
        };

    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        dialog.dismiss();
        try {
            JSONObject jsonObject=new JSONObject(data);
            JSONArray jsonArray=jsonObject.getJSONArray("items");
            for (int a=0;a<jsonArray.length();a++){
                JSONObject object=jsonArray.getJSONObject(a);
                JSONObject volume=object.getJSONObject("volumeInfo");
                String booktitle=volume.getString("title");
                String desc=volume.getString("description");
                JSONObject image=volume.getJSONObject("imageLinks");
                String bookimage=image.getString("thumbnail");
                bookModels.add(new BookModel(booktitle,desc,bookimage));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
/*
    class BookTask extends AsyncTask<String,Void,String>{
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading...!");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url=new URL(bookurl+searchname.getText().toString());
                Log.i("bookurl",url.toString());
                HttpURLConnection urlConnection=(HttpURLConnection)
                                                        url.openConnection();
                InputStream inputStream=urlConnection.getInputStream();
                Scanner scanner=new Scanner(inputStream);
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()){
                    return scanner.next();
                }else {
                    return null;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("items");
                for (int a=0;a<jsonArray.length();a++){
                    JSONObject object=jsonArray.getJSONObject(a);
                    JSONObject volume=object.getJSONObject("volumeInfo");
                    String booktitle=volume.getString("title");
                    String desc=volume.getString("description");
                    JSONObject image=volume.getJSONObject("imageLinks");
                    String bookimage=image.getString("thumbnail");
                    bookModels.add(new BookModel(booktitle,desc,bookimage));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }*/
}