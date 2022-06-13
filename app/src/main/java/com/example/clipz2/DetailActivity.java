package com.example.clipz2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.clipz2.Class.Youtube;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailActivity extends YouTubeBaseActivity {
    ImageButton btn_save;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    String genre_list = "";

    ArrayList<Youtube> youtubeList;
    private YouTubePlayerView youTubePlayerView;
    private String trailer01;
    private String m_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        youtubeList = new ArrayList<Youtube>();

        Intent intent = getIntent();
        m_id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");
        String original_title = intent.getStringExtra("original_title");
        String poster_path = intent.getStringExtra("poster_path");
        String overview = intent.getStringExtra("overview");
        String release_date = intent.getStringExtra("release_date");
        String vote_average = intent.getStringExtra("vote_average");
        ArrayList<Integer> genre_ids = intent.getIntegerArrayListExtra("genre_ids");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        // poster
        ImageView imageView_poster = (ImageView) findViewById(R.id.iv_poster);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500"+poster_path)
                .centerCrop()
                .crossFade()
                .into(imageView_poster);

        // 제목
        TextView textView_title = (TextView)findViewById(R.id.tv_title);
        textView_title.setText(title);
        // 원제
        TextView textView_original_title = (TextView)findViewById(R.id.tv_original_title);
        textView_original_title.setText(original_title);
        // 개봉일
        TextView textView_release_date = (TextView)findViewById(R.id.tv_release_date);
        textView_release_date.setText(release_date);
        // 별점
        RatingBar ratingBar = findViewById(R.id.movie_rating);
        ratingBar.setRating(((float)Double.parseDouble(vote_average)/2));
        // 장르
        HashMap<Integer, String> map = new HashMap<>(18);
        map.put(12,"모험 ");map.put(14,"판타지 ");map.put(16,"애니메이션 ");map.put(18,"드라마 ");map.put(27,"공포 ");map.put(28,"액션 ");map.put(35,"코미디 ");map.put(36,"역사 ");map.put(37,"서부 ");
        map.put(53,"스릴러 ");map.put(80,"범죄 ");map.put(99,"다큐멘터리 ");map.put(9648,"미스테리 ");map.put(878,"SF ");map.put(10402,"음악 ");map.put(10749,"로맨스 ");map.put(10751,"가족 ");map.put(10752,"전쟁 ");map.put(10770,"TV Movie ");
        TextView textView_genre_ids = (TextView)findViewById(R.id.tv_genre_ids);
        // ArrayList의 Integer가 map을 통해 String으로 변환후 모아서 setText
        for(int i=0; i<genre_ids.size(); i++)
            genre_list += map.get((genre_ids.get(i)));
        textView_genre_ids.setText(genre_list);
        // 줄거리
        TextView textView_overview = (TextView)findViewById(R.id.tv_overview);
        textView_overview.setText(overview);
        // 리뷰버튼, 찜 버튼
        Button btn_review = findViewById(R.id.btn_review);
        btn_save = findViewById(R.id.btn_save);

        databaseReference.child(user.getUid()).child("save").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    String m_name = dataSnapshot.getValue().toString();
                    if(m_name.equals(title)){
                        btn_save.setSelected(true);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        btn_review.setOnClickListener(new View.OnClickListener() {  // 리뷰버튼 선택시
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, ReviewActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("genre_list", genre_list);
                startActivity(intent);
                finish();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {    // 찜버튼 선택시
            @Override
            public void onClick(View v) {
                if(!btn_save.isSelected()){
                    databaseReference.child(user.getUid()).child("save").child(title).setValue(title);
                    Toast.makeText(DetailActivity.this, "해당 영화가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                    btn_save.setSelected(true);
                }

            }
        });

        //Asynctask
        YoutubeAsyncTask mProcessTask = new YoutubeAsyncTask();
        mProcessTask.execute(m_id);
    }
    public void playVideo(final String videoId, YouTubePlayerView youTubePlayerView) {
        //initialize youtube player view
        Log.d("Youtube", "trailer: " + videoId);
        youTubePlayerView.initialize("AIzaSyBYsCJPIKM0zfDWD5hHSCjXwziC1E6bOh4",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.cueVideo(videoId);

                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }

    public class YoutubeAsyncTask extends AsyncTask<String, Void, Youtube[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Youtube[] youtubes) {
            super.onPostExecute(youtubes);

            //ArrayList에 차례대로 집어 넣는다.
            if (youtubes.length > 0) {
                for (Youtube p : youtubes) {
                    youtubeList.add(p);
                }

                //유튜브뷰어를 이용 화면에 출력하자.
                trailer01 = youtubeList.get(0).getKey();
                Log.d("Youtube", "trailer : " + trailer01);
                youTubePlayerView = findViewById(R.id.youtube_view);
                playVideo(trailer01, youTubePlayerView);

            }
        }

        @Override
        protected Youtube[] doInBackground(String... strings) {
            String m_id = strings[0];

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/" + m_id + "/videos?api_key=f67b2766ccc9c5cea1b76472c07089d5")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                Youtube[] posts = gson.fromJson(rootObject, Youtube[].class);
                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}