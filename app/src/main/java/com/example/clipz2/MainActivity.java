package com.example.clipz2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.clipz2.Fragment.FragmentFeed;
import com.example.clipz2.Fragment.FragmentMain;
import com.example.clipz2.Fragment.FragmentMovie;
import com.example.clipz2.Fragment.FragmentMypage;
import com.example.clipz2.Fragment.FragmentNotification;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private FragmentMain fragMain;
    private FragmentMovie fragMovie;
    private FragmentFeed fragFeed;
    private FragmentNotification fragNotification;
    private FragmentMypage fragMypage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=getIntent();
        bottomNavigationView = findViewById(R.id.bottom_navi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_movie:
                        setFrag(0);
                        break;
                    case R.id.action_home:
                        setFrag(1);
                        break;
                    case R.id.action_feed:
                        setFrag(2);
                        break;
                    case R.id.action_notification:
                        setFrag(3);
                        break;
                    case R.id.action_mypage:
                        setFrag(4);
                        break;
                }
                return true;
            }
        });
        fragMain = new FragmentMain();
        fragMovie = new FragmentMovie();
        fragFeed = new FragmentFeed();
        fragNotification = new FragmentNotification();
        fragMypage = new FragmentMypage();
        if(intent.getBooleanExtra("mypage",false)==true){
            setFrag(3);//닉네임변경, 언팔로우시 프래그먼트 mypage로
            bottomNavigationView.setSelectedItemId(R.id.action_mypage);//하단 아이콘 설정
        }
        else{
            setFrag(0); //첫 프래그먼트 화면을 무엇으로 지정해줄 것인지 선택
        }
    }


    // 프래그먼터 교체가 일어나는 실행문
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {

            case 0 :
                ft.replace(R.id.main_frame, fragMovie);
                ft.commit();
                break;
            case 1 :
                ft.replace(R.id.main_frame, fragMain);
                ft.commit();
                break;
            case 2 :
                ft.replace(R.id.main_frame, fragFeed);
                ft.commit();
                break;
            case 3 :
                ft.replace(R.id.main_frame, fragNotification);
                ft.commit();
                break;
            case 4 :
                ft.replace(R.id.main_frame, fragMypage);
                ft.commit();
                break;

        }

    }
}