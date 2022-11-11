package com.example.diaryproject;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.radiobutton.MaterialRadioButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 상세보기 화면 or 작성하기 화면이다.
 */

public class DiaryDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvDate_start, mTvDate_end;                   // 일시 설정 텍스트
    private EditText mEtTitle, mEtTitle2, mEtContent;// 일기 제목, 일기 내용
    private RadioGroup mRgWeather;

    private String mDetailMode = "";                    // intent로 받아낸 게시글 모드
    private String mBeforeDate = "";                    // intent로 받아낸 게시글 기준 작성 일자
    private String mSelectedUserDate_start = "";        // 선택된 일시 값
    private String mSelectedUserDate_end = "";          // 선택된 일시 값
    private int mSelectiveWeatherType = -1;             // 선택된 날씨 값(1 ~ 4)

    private DataBaseHelper mDatabaseHelper; // Database 유틸객체

    private ListAdapter adapter;
    private Button add;
    private Button remove;
    private Button result;
    private ListView listView;
    private int num;

    public ArrayList<EditText> find = new ArrayList<>();
    public ArrayList<ListItem> listViewItemList = new ArrayList<ListItem>(); //리스트뷰
    private ArrayList<ListItem> filteredItemList = listViewItemList; //리스트뷰 임시저장소
    public ArrayList<String>find2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_detail);

        // list 관련 자료
        num = 0;

        add =(Button)findViewById(R.id.add);
        remove=(Button)findViewById(R.id.remove);
        result=(Button)findViewById(R.id.result);
        listView=(ListView)findViewById(R.id.listview);

        adapter = new ListAdapter();

        listView.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DiaryDetailActivity.this,num+ "추가되었습니다.", Toast.LENGTH_SHORT).show();
                adapter.addItem(num+"","","",num);
                adapter.notifyDataSetChanged();
                num ++;
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DiaryDetailActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                adapter.delItem();
                adapter.notifyDataSetChanged();
                num --;
            }
        });
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DiaryDetailActivity.this, "입력되었습니다.", Toast.LENGTH_SHORT).show();
                System.out.println(adapter.getItem(0).toString());

                for(int i=0;i<listViewItemList.size();i++){
                    System.out.println(listViewItemList.get(i).getName());
                    System.out.println(listViewItemList.get(i).getCount());
                    System.out.println(listViewItemList.get(i).getPrice());
                }
            }
        });

        //어뎁터 시작
        class ListAdapter extends BaseAdapter {

            @Override
            public int getCount() {
                return filteredItemList.size();
            }

            @Override
            public Object getItem(int position) {
                return filteredItemList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                final int pos = filteredItemList.get(position).getNum();
                final Context context = parent.getContext();
                final ViewHolder holder;

                // "listview_item" Layout을 inflate하여 convertView 참조 획득.
                if (convertView == null) {
                    holder = new ViewHolder();
                    LayoutInflater inflater =
                            (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.listview_list, parent, false);
                    holder.editText1 = (EditText)convertView.findViewById(R.id.editText1);
                    holder.editText2 = (EditText)convertView.findViewById(R.id.editText2);
                    holder.editText3 = (EditText)convertView.findViewById(R.id.editText3);

                    convertView.setTag(holder);
                }else{
                    holder = (ViewHolder)convertView.getTag();
                }

                holder.ref = position;

                // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
                final EditText editText1 = (EditText)convertView.findViewById(R.id.editText1);

                // Data Set(filteredItemList)에서 position에 위치한 데이터 참조 획득
                final ListItem listViewItem = filteredItemList.get(position);

                holder.editText1.setText(listViewItem.getName());
                holder.editText2.setText(listViewItem.getCount());
                holder.editText3.setText(listViewItem.getPrice());

                holder.editText1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        filteredItemList.get(holder.ref).setName(s.toString());
                    }
                });

                holder.editText2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        filteredItemList.get(holder.ref).setCount(s.toString());
                    }
                });

                holder.editText3.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        filteredItemList.get(holder.ref).setPrice(s.toString());
                        find2.add(holder.editText3.getText().toString());
                    }
                });

                return convertView;
            }

            public void addItem(String name, String count, String price, int num) {
                ListItem item = new ListItem();
                item.setName(name);
                item.setCount(count);
                item.setPrice(price);
                item.setNum(num);

                listViewItemList.add(item);
            }

            public void delItem() {
                if (listViewItemList.size() < 1) {
                } else {
                    listViewItemList.remove(listViewItemList.size() - 1);
                }
            }

            class ViewHolder {

                public int ref;
                EditText editText1;
                EditText editText2;
                EditText editText3;
            }
        }

        //DataBase 객체 생성
        mDatabaseHelper = new DataBaseHelper(this);

        mTvDate_start = findViewById(R.id.tv_date);             // 일시 설정 텍스트
        mTvDate_end = findViewById(R.id.tv_date2);
        mEtTitle = findViewById(R.id.et_title);  // 제목 입력필드
        mEtTitle2 = findViewById(R.id.et_title2);         // 내용 입력필드
        mRgWeather = findViewById(R.id.rg_weather);             // 날씨 선택 라디오 그룹

        ImageView iv_back = findViewById(R.id.iv_back);         // 뒤로가기 버튼
        ImageView iv_check = findViewById(R.id.iv_check);       // 작성완료 버튼

        mTvDate_start.setOnClickListener(this);     // 클릭 가능 부여
        mTvDate_end.setOnClickListener(this);       // 클릭 가능 부여
        iv_back.setOnClickListener(this);           // 클릭 가능 부여
        iv_check.setOnClickListener(this);          // 클릭 가능 부여

        // 기본으로 설정된 날씨의 값을 지정 (디바이스 현재 시간 기준)
        mSelectedUserDate_start = new SimpleDateFormat("yyyy/MM/dd (EE)", Locale.KOREA).format(new Date());
        mTvDate_start.setText(mSelectedUserDate_start);

        mSelectedUserDate_end = new SimpleDateFormat("yyyy/MM/dd (EE)", Locale.KOREA).format(new Date());
        mTvDate_end.setText(mSelectedUserDate_end);

        // 이전 엑티비티로부터 값을 전달 받기
        Intent intent = getIntent();
        if (intent.getExtras() != null){
            // intent putExtra 했던 데이터가 존재한다면 내부를 수행
            DiaryModel diaryModel = (DiaryModel) intent.getSerializableExtra("diaryModel");
            mDetailMode = intent.getStringExtra("mode");
            mBeforeDate = diaryModel.getWriteDate(); // 게시글 database update 처리를 위해서 여기서 받아둔다.

            // 넘겨받은 값을 활용해서 필드들을 설정해주기
            mEtTitle.setText(diaryModel.getTitle());
            mEtTitle2.setText(diaryModel.getTitle2());
            mEtContent.setText(diaryModel.getContent());
            int weatherType = diaryModel.getWeatherType();
            ((MaterialRadioButton) mRgWeather.getChildAt(weatherType)).setChecked(true);
            mSelectedUserDate_start = diaryModel.getUserDate();
            mSelectedUserDate_end = diaryModel.getUserDate2();
            mTvDate_start.setText(diaryModel.getUserDate());
            mTvDate_end.setText(diaryModel.getUserDate2());

            if (mDetailMode.equals("modify")){
                //수정모드
                TextView tv_header_title = findViewById(R.id.tv_header_title);
                tv_header_title.setText("일기 수정");
            }else if (mDetailMode.equals("detail")) {
                //상세 보기 모드
                TextView tv_header_title = findViewById(R.id.tv_header_title);
                tv_header_title.setText("일기 상세보기");

                //읽기 전용 화면으로 표시
                mEtTitle.setEnabled(false);
                mEtTitle2.setEnabled(false);
                mEtContent.setEnabled(false);
                mTvDate_start.setEnabled(false);
                mTvDate_end.setEnabled(false);
                for (int i=0; i < mRgWeather.getChildCount(); i++){
                    // 라디오 그룹 내에 읽기전용
                    mRgWeather.getChildAt(i).setEnabled(false);
                }
                // 작성완료 버튼을 투명처리한다.
                iv_check.setVisibility(View.INVISIBLE);
            }

        }

    }

    @Override
    public void onClick(View view){
        // setOnClickListener가 붙어있는 뷰들은 클릭하면 모두 이곳을 수행하게 된다.
        switch (view.getId()){
            case R.id.iv_back:
                // 뒤로가기
                finish();       //현재 액티비티 종료
                break;

            case R.id.iv_check:
                // 작성 완료

                // 라디오 그룹의 버튼 클릭 현재 상황 가지고 오기
                mSelectiveWeatherType = mRgWeather.indexOfChild(findViewById(mRgWeather.getCheckedRadioButtonId()));

                // 입력필드 작성란이 비어있는지 체크
                if(mEtTitle.getText().length() == 0 || mEtContent.getText().length() == 0){
                    // error
                    Toast.makeText(this, "입력되지 않은 필드가 존재합니다.", Toast.LENGTH_SHORT).show();
                    return;     // 밑의 로직을 태우지 않고 되돌려 보냄

                }

                // 날씨 선택이 되어있는지 체크
                if (mSelectiveWeatherType == -1) {
                    // error
                    Toast.makeText(this, "날씨를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;     // 밑의 로직을 태우지 않고 되돌려 보냄
                }

                // 이 곳까지 도착했다면 에러상황이 없으므로 데이터 저장!

                String title = mEtTitle.getText().toString();       // 제목 입력 값
                String title2 = mEtTitle2.getText().toString();
                String content = mEtTitle.getText().toString();     // 내용 입력 값
                String userDate = mSelectedUserDate_start;          // 사용자가 선택한 일시
                if (userDate.equals("")){
                    //날짜를 설정 안 한 경우
                    userDate = mTvDate_start.getText().toString();
                }
                String userDate2 = mSelectedUserDate_end;           // 사용자가 선택한 일시
                if (userDate2.equals("")){
                    //날짜를 설정 안 한 경우
                    userDate2 = mTvDate_end.getText().toString();
                }
                String writeDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREAN).format(new Date()); //작성 완료 누른 시점의 일시


                // 액티비티의 현재 모드에 따라서 데이터베이스에 저장 또는 업데이트
                if(mDetailMode.equals("modify")) {
                    //게시글 수정 모드
                    mDatabaseHelper.setUpdateDiaryList(title, title2, content, mSelectiveWeatherType, userDate, userDate2, writeDate, mBeforeDate);
                    Toast.makeText(this, "다이어리 수정이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                } else{
                    mDatabaseHelper.setInsertDiaryList(title, title2, content, mSelectiveWeatherType, userDate, userDate2, writeDate);
                    Toast.makeText(this, "다이어리 등록이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                }

                finish();       // 현재 액티비티 종료
                break;

            case R.id.tv_date:
                // 일시 설정 텍스트

                // 달력을 띄워서 사용자에게 일시를 입력 받는다.

                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // 달력에 선택 된 (년, 월, 일)을 가지고와서 다시 캘린더 함수에 넣어줘서 사용자가 선택한 요일을 알아낸다.
                        Calendar innerCal = Calendar.getInstance();
                        innerCal.set(Calendar.YEAR, year);
                        innerCal.set(Calendar.MONTH, month);
                        innerCal.set(Calendar.DAY_OF_MONTH, day);

                        mSelectedUserDate_start = new SimpleDateFormat("yyyy/MM/dd (EE)", Locale.KOREAN).format(innerCal.getTime());
                        mTvDate_start.setText(mSelectedUserDate_start);

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                dialog.show();      // 다이얼로그 (팝업) 활성화!

                break;

            case R.id.tv_date2:

                Calendar calendar2 = Calendar.getInstance();
                DatePickerDialog dialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // 달력에 선택 된 (년, 월, 일)을 가지고와서 다시 캘린더 함수에 넣어줘서 사용자가 선택한 요일을 알아낸다.
                        Calendar innerCal2 = Calendar.getInstance();
                        innerCal2.set(Calendar.YEAR, year);
                        innerCal2.set(Calendar.MONTH, month);
                        innerCal2.set(Calendar.DAY_OF_MONTH, day);

                        mSelectedUserDate_end = new SimpleDateFormat("yyyy/MM/dd (EE)", Locale.KOREAN).format(innerCal2.getTime());
                        mTvDate_end.setText(mSelectedUserDate_end);

                    }
                }, calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DATE));
                dialog2.show();      // 다이얼로그 (팝업) 활성화!

                break;

        }
    }
}

