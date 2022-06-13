package com.example.clipz2.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clipz2.Adapter.SectionDataAdapter;
import com.example.clipz2.Class.SingleItem;
import com.example.clipz2.R;
import com.example.clipz2.SectionItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragmentMovie extends Fragment {

    private View view;
    SectionDataAdapter adapter_m;
    ArrayList<SingleItem> PmovieList;
    ArrayList<SingleItem> NmovieList;
    ArrayList<SingleItem> TmovieList;
    ArrayList<SectionItem> sectionDataList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie, container, false);

        Toolbar myToolbar = view.findViewById(R.id.tool_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        setHasOptionsMenu(true);

        // 메인
        sectionDataList = new ArrayList<SectionItem>();
        PmovieList = new ArrayList<SingleItem>();
        NmovieList = new ArrayList<SingleItem>();
        TmovieList = new ArrayList<SingleItem>();
        // 메인 실행
        MyAsyncTask_P mAsyncTask_P = new MyAsyncTask_P();

        mAsyncTask_P.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        MyAsyncTask_N mAsyncTask_N = new MyAsyncTask_N();

        mAsyncTask_N.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        MyAsyncTask_T mAsyncTask_T = new MyAsyncTask_T();

        mAsyncTask_T.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        CreateMovieList();

        RecyclerView my_recycler_view = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        my_recycler_view.setHasFixedSize(true);
        adapter_m = new SectionDataAdapter(getActivity(), sectionDataList);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        my_recycler_view.setAdapter(adapter_m);

        return view;
    }

    public class MyAsyncTask_P extends AsyncTask<String, Void, SingleItem[]> {
        //로딩중 표시
        ProgressDialog progressDialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("\tLoading...");
            //show dialog
            progressDialog.show();

            PmovieList.clear();
        }

        @Override
        protected SingleItem[] doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/popular?api_key=f67b2766ccc9c5cea1b76472c07089d5&language=ko-KR&page=1")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                SingleItem[] posts = gson.fromJson(rootObject, SingleItem[].class);
                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(SingleItem[] result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            //ArrayList에 차례대로 집어 넣는다.
            if (result.length > 0) {
                for (SingleItem p : result) {
                    PmovieList.add(p);
                    Log.d("opd", "P" + String.valueOf(result));

                }
            }
            adapter_m.notifyDataSetChanged();
        }
    }

    public class MyAsyncTask_N extends AsyncTask<String, Void, SingleItem[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            NmovieList.clear();

        }

        @Override
        protected SingleItem[] doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/now_playing?api_key=f67b2766ccc9c5cea1b76472c07089d5&language=ko-KR&page=1&region=KR")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                SingleItem[] posts = gson.fromJson(rootObject, SingleItem[].class);
                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(SingleItem[] result2) {
            super.onPostExecute(result2);
            //ArrayList에 차례대로 집어 넣는다.
            if (result2.length > 0) {
                for (SingleItem p : result2) {
                    NmovieList.add(p);
                    Log.d("opd", "N" + String.valueOf(result2));
                }
            }
            adapter_m.notifyDataSetChanged();
        }
    }

    public class MyAsyncTask_T extends AsyncTask<String, Void, SingleItem[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            TmovieList.clear();
        }

        @Override
        protected SingleItem[] doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/top_rated?api_key=f67b2766ccc9c5cea1b76472c07089d5&language=ko-KR&page=1")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                SingleItem[] posts = gson.fromJson(rootObject, SingleItem[].class);
                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(SingleItem[] result3) {
            super.onPostExecute(result3);
            //ArrayList에 차례대로 집어 넣는다.
            if (result3.length > 0) {
                for (SingleItem p : result3) {
                    TmovieList.add(p);
                    Log.d("opd", "T" + String.valueOf(result3));
                }
            }
            adapter_m.notifyDataSetChanged();
        }
    }

    public void CreateMovieList() {
        SectionItem sectionDataModel_P = new SectionItem();
        sectionDataModel_P.setHeaderTitle("   인기 영화");
        sectionDataModel_P.setSingItemList(PmovieList);
        sectionDataList.add(sectionDataModel_P);

        SectionItem sectionDataModel_N = new SectionItem();
        sectionDataModel_N.setHeaderTitle("   지금 상영 중인 영화");
        sectionDataModel_N.setSingItemList(NmovieList);
        sectionDataList.add(sectionDataModel_N);

        SectionItem sectionDataModel_T = new SectionItem();
        sectionDataModel_T.setHeaderTitle("   최고 평점 영화 추천");
        sectionDataModel_T.setSingItemList(TmovieList);
        sectionDataList.add(sectionDataModel_T);
    }


}