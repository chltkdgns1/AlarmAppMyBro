package com.gkftndltek.mybro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ForYouAdd extends AppCompatActivity {
    private List<String> data;
    private ArrayAdapter<String> adapter;
    private Spinner Spinner_Date;
    private TextView TextView_Add_End;
    private EditText EditText_Name,EditText_Des;



    private int times = 0;
    private String des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foryou_add);

        init();
    }

    void init(){
        EditText_Des = findViewById(R.id.EditText_Des);
        EditText_Name = findViewById(R.id.EditText_Name);
        TextView_Add_End = findViewById(R.id.TextView_Add_End);
        TextView_Add_End.setClickable(true);

        Spinner_Date = findViewById(R.id.Spinner_Date);

        data = new ArrayList<>();
        for(int i=1;i<=365;i++) {
            String date = Integer.toString(i) + "일";
            data.add(date);
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        Spinner_Date.setAdapter(adapter);

        Spinner_Date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                times = (position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        TextView_Add_End.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = EditText_Name.getText().toString();
                des = EditText_Des.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(getApplicationContext(),"이름을 적어주세요!",Toast.LENGTH_LONG).show();
                    return;
                }

                if(times == 0) {
                    Toast.makeText(getApplicationContext(),"기간을 선택해주세요!",Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra("name",name);
                intent.putExtra("time",Integer.toString(times));
                intent.putExtra("des",des);
                setResult(1,intent);
                finish();
            }
        });

    }
}
