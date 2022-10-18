package com.example.diaryproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRvDiary;              // 리사이클러 뷰 (리스트 뷰)
    DiaryListAdapter mAdapter;          // 리사이클러 뷰와 연동할 어댑터
    ArrayList<DiaryModel> mLstDiary;    // 리스트에 표현할 다이어리 데이터들 (배열)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLstDiary = new ArrayList<>();

        mRvDiary = findViewById(R.id.rv_diary);

        mAdapter = new DiaryListAdapter();          // 리사이클러 뷰 어댑터 인스탠스 생성

        // 다이어리 샘플 아이템 2개 생성
        DiaryModel item = new DiaryModel();
        item.setId(0);
        item.setTitle("캡스톤 디자인 프로젝트 예제");
        item.setContent("내 내용입니다.");
        item.setUserDate("2022/10/18 Tue");
        item.setWriteDate("2022/10/18 Tue");
        item.setWeatherType(0);
        mLstDiary.add(item);

        DiaryModel item2 = new DiaryModel();
        item2.setId(0);
        item2.setTitle("캡스톤 디자인 중간 점검");
        item2.setContent("아무렴 어때");
        item2.setUserDate("2022/10/17 Mon");
        item2.setWriteDate("2022/10/17 Mon");
        item2.setWeatherType(3);
        mLstDiary.add(item2);

        mAdapter.setSampleList(mLstDiary);
        mRvDiary.setAdapter(mAdapter);

        // 액티비티 (화면) 이 실행될 때 가장 먼저 호출되는 곳
        FloatingActionButton floatingActionButton = findViewById(R.id.btn_write);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 작성하기 버튼을 누를 때 호출되는 곳

                // 작성하기 화면으로 이동!
                Intent intent = new Intent(MainActivity.this, DiaryDetailActivity.class);
                startActivity(intent);
            }
        });
    }

}