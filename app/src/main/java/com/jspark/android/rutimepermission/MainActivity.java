package com.jspark.android.rutimepermission;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView lv;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            readContact();
        }

        tv = (TextView) findViewById(R.id.txt);
        tv.setText("Contacts");
        tv.setTextSize(20.0f);
    }

    private final int REQ_CODE = 100;

    // 1. 권한 체크
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        // 1.1 런타임 권한 체크
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED ||checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            String permArr[] = {Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE};

            requestPermissions(permArr, REQ_CODE);
        } else {
            readContact();
        }
        // 1.2
    }

    // 2. 권한 체크 후 콜백
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQ_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                readContact();
            } else {
                Toast.makeText(this, "권한 없으면 프로그램 못씀", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    // 3. 데이터 찾아오기 (실행)
    private void readContact() {
        Toast.makeText(this, "Hi", Toast.LENGTH_SHORT).show();

        ArrayList<Contacts> data;

        DataLoader dl = new DataLoader(MainActivity.this);
        data = dl.datas;

        lv = (RecyclerView) findViewById(R.id.recyView);
        CustomAdapter adapter = new CustomAdapter(data, this);
        lv.setAdapter(adapter);
        lv.setLayoutManager(new LinearLayoutManager(this));
    }


}