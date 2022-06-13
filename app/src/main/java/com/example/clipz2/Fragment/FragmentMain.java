package com.example.clipz2.Fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clipz2.Adapter.SectionTvDataAdapter;
import com.example.clipz2.Class.SingleItem_tv;
import com.example.clipz2.R;
import com.example.clipz2.SectionItem_tv;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragmentMain extends Fragment {

    private View view;
    SectionTvDataAdapter adapter_m;
    ArrayList<SingleItem_tv> PtvList;
    ArrayList<SingleItem_tv> NtvList;
    ArrayList<SingleItem_tv> TtvList;
    ArrayList<SectionItem_tv> sectionDataTvList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        Toolbar myToolbar = view.findViewById(R.id.tool_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        setHasOptionsMenu(true);

        // 메인
        sectionDataTvList = new ArrayList<SectionItem_tv>();
        PtvList = new ArrayList<SingleItem_tv>();
        NtvList = new ArrayList<SingleItem_tv>();
        TtvList = new ArrayList<SingleItem_tv>();
        // 메인 실행
        MyAsyncTask_P mAsyncTask_P = new MyAsyncTask_P();

        mAsyncTask_P.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        MyAsyncTask_N mAsyncTask_N = new MyAsyncTask_N();

        mAsyncTask_N.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        MyAsyncTask_T mAsyncTask_T = new MyAsyncTask_T();

        mAsyncTask_T.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        CreateTvList();

        RecyclerView my_recycler_view = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        my_recycler_view.setHasFixedSize(true);
        adapter_m = new SectionTvDataAdapter(sectionDataTvList, getActivity());
        my_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        my_recycler_view.setAdapter(adapter_m);

        return view;
    }

    public class MyAsyncTask_P extends AsyncTask<String, Void, SingleItem_tv[]> {
        //로딩중 표시
        ProgressDialog progressDialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("\tLoading...");
            //show dialog
            progressDialog.show();

            PtvList.clear();
        }

        @Override
        protected SingleItem_tv[] doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/tv/popular?api_key=f67b2766ccc9c5cea1b76472c07089d5&language=ko-KR&page=1")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                SingleItem_tv[] posts = gson.fromJson(rootObject, SingleItem_tv[].class);
                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(SingleItem_tv[] result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            //ArrayList에 차례대로 집어 넣는다.
            if (result.length > 0) {
                for (SingleItem_tv p : result) {
                    PtvList.add(p);
                    Log.d("opd", "P" + String.valueOf(result));

                }
            }
            adapter_m.notifyDataSetChanged();
        }
    }


    public class MyAsyncTask_N extends AsyncTask<String, Void, SingleItem_tv[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            NtvList.clear();

        }

        @Override
        protected SingleItem_tv[] doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/tv/airing_today?api_key=f67b2766ccc9c5cea1b76472c07089d5&language=ko-KR&page=1&region=KR")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                SingleItem_tv[] posts = gson.fromJson(rootObject, SingleItem_tv[].class);
                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(SingleItem_tv[] result2) {
            super.onPostExecute(result2);
            //ArrayList에 차례대로 집어 넣는다.
            if (result2.length > 0) {
                for (SingleItem_tv p : result2) {
                    NtvList.add(p);
                    Log.d("opd", "N" + String.valueOf(result2));
                }
            }
            adapter_m.notifyDataSetChanged();
        }
    }

    public class MyAsyncTask_T extends AsyncTask<String, Void, SingleItem_tv[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            TtvList.clear();
        }

        @Override
        protected SingleItem_tv[] doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/tv/top_rated?api_key=f67b2766ccc9c5cea1b76472c07089d5&language=ko-KR&page=1")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                SingleItem_tv[] posts = gson.fromJson(rootObject, SingleItem_tv[].class);
                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(SingleItem_tv[] result3) {
            super.onPostExecute(result3);
            //ArrayList에 차례대로 집어 넣는다.
            if (result3.length > 0) {
                for (SingleItem_tv p : result3) {
                    TtvList.add(p);
                    Log.d("opd", "T" + String.valueOf(result3));
                }
            }
            adapter_m.notifyDataSetChanged();
        }
    }

    public void CreateTvList() {
        SectionItem_tv sectionDataModel_P = new SectionItem_tv();
        sectionDataModel_P.setHeaderTitle("   인기 프로그램");
        sectionDataModel_P.setSingleItemList_tv(PtvList);
        sectionDataTvList.add(sectionDataModel_P);

        SectionItem_tv sectionDataModel_N = new SectionItem_tv();
        sectionDataModel_N.setHeaderTitle("   최신 프로그램");
        sectionDataModel_N.setSingleItemList_tv(NtvList);
        sectionDataTvList.add(sectionDataModel_N);

        SectionItem_tv sectionDataModel_T = new SectionItem_tv();
        sectionDataModel_T.setHeaderTitle("   최고 평점 프로그램 추천");
        sectionDataModel_T.setSingleItemList_tv(TtvList);
        sectionDataTvList.add(sectionDataModel_T);
    }


}