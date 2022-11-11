package com.example.diaryproject;

import java.io.Serializable;

/**
 * 다이어리 리스트 아이템을 구성하는 모델 (표본)
 */

public class DiaryModel implements Serializable {
    int id;                 // 게시물 고유 Id 값
    String title;           // 게시물 제목
    String title2;   //여행지입력란
    String content;         // 게시물 내용
    int weatherType;        // 날씨 값 (0:맑음 1:흐림뒤갬 2:흐림 3:매우흐림 4:비 5:눈)
    String userDate;        // 사용자가 지정한 날짜(일시)
    String userDate2;
    String writeDate;       // 게시글 작성한 날짜(일시)

    // getter & setter 게터세터
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getTitle2() { return  title2; }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setTitle2(String title2) {this.title2 = title2;}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(int weatherType) {
        this.weatherType = weatherType;
    }

    public String getUserDate() {
        return userDate;
    }

    public String getUserDate2() {
        return userDate2;
    }


    public void setUserDate(String userDate) {
        this.userDate = userDate;
    }

    public void setUserDate2(String userDate2) {this.userDate2 = userDate2;}

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

    // list 관련 자료
    String name;
    String count;
    String price;
    int num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
