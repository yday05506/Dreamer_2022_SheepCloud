package com.example.dreamer_2022_sheepcloud;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class WriteActivity extends AppCompatActivity {
    ImageButton btnList, btnUser;
    private int wYear = 0, wMonth = 0, wDay = 0;
    private DatePicker.OnDateChangedListener wOnDateChangedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        final String[] cultureKind = {"뮤지컬", "책", "영화", "드라마", "미술/박물관", "기타"};

        Spinner spinner = findViewById(R.id.spinner_kind);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cultureKind);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_write);

        Calendar calendar = new GregorianCalendar();
        wYear = calendar.get(Calendar.YEAR);
        wMonth = calendar.get(Calendar.MONTH);
        wDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePicker datePicker = findViewById(R.id.write_date);
        datePicker.init(wYear, wMonth, wDay, wOnDateChangedListener);

        // 타이틀바 없애는 거임!! 지우지 마셈!!
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btnList = findViewById(R.id.btn_list);
        btnUser = findViewById(R.id.btn_user);

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showDatePicker(View view) {
        DialogFragment writeFragment = new DatePickerFragment();
        writeFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (year_string + "/" + month_string + "/" + day_string);
        Toast.makeText(this, dateMessage, Toast.LENGTH_SHORT).show();
    }
}