package com.gkftndltek.mybro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // 뷰
    private TextView TextView_Add,TextView_New;

    // 데이터베이스

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    private ForYouRecyclerViewClass forYouRecyclerViewClass;

    public Handler handlerPushMessage = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                ForYouData data = (ForYouData) msg.obj;
                databaseReference.child("list").child(data.getName() + data.getTime()).removeValue();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    void init(){

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        forYouRecyclerViewClass = new ForYouRecyclerViewClass();
        forYouRecyclerViewClass.execute(this,getApplicationContext(),handlerPushMessage);

        TextView_Add = findViewById(R.id.TextView_Add);
        TextView_New = findViewById(R.id.TextView_New);
        TextView_Add.setClickable(true);
        TextView_New.setClickable(true);

        TextView_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForYouAdd.class);
                startActivityForResult(intent, 0);
            }
        });

       databaseReference.child("list").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    ForYouData data = snap.getValue(ForYouData.class);
                    forYouRecyclerViewClass.add(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        TextView_New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forYouRecyclerViewClass.clear();
                databaseReference.child("list").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            ForYouData data = snap.getValue(ForYouData.class);
                            forYouRecyclerViewClass.add(data);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // 디비를 가져오던중 에러 발생 시
                        Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1) {
            String time = data.getExtras().getString("time");
            long t = (long) Integer.parseInt(time);
            String name = data.getExtras().getString("name");
            String des = data.getExtras().getString("des");

            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formatDate = sdfNow.format(date);

            ForYouData fdata = new ForYouData();
            fdata.setDes(des);
            fdata.setName(name);
            fdata.setTime(formatDate);
            fdata.setTime_end(now + (t - 1) * 3600000*24);
            databaseReference.child("list").child(name + formatDate).setValue(fdata);
            forYouRecyclerViewClass.add(fdata);
        }
    }
}
