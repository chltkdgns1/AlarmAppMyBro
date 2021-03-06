package com.gkftndltek.mybro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ForYouRecyclerViewClass {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public void execute(final Activity act, final Context con, final Handler handlerPushMessage) { // 리사이클러뷰 사용 시작
        recyclerView = (RecyclerView) act.findViewById(R.id.RecyclerView_Foryou); // 이거 수정해야함
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(con);

        recyclerView.setLayoutManager(layoutManager);

        //mAdapter = new DestinationAdapter(); // 원래 기존에 사용하던 것

        // mAdapter = new MakeMapAdapter();

        mAdapter = new ForYouAdapter(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() != null) {
                    final int position = (int) v.getTag();
                    final ForYouData data = ((ForYouAdapter) mAdapter).getData(position);

                    AlertDialog.Builder alert_ex = new AlertDialog.Builder(act);
                    alert_ex.setMessage("알람을 삭제하시겠습니까?");

                    alert_ex.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });

                    alert_ex.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 버그 발생할 수 있음.

                            ((ForYouAdapter) mAdapter).remove(position);

                            Message msg = Message.obtain();
                            msg.obj = data;
                            msg.what = 1;
                            handlerPushMessage.sendMessage(msg);
                        }
                    });

                    alert_ex.setTitle("Notice");
                    AlertDialog alert = alert_ex.create();
                    alert.show();
                }
            }

        });

        recyclerView.setAdapter(mAdapter);
    }

    public synchronized void add(ForYouData data) {
        ((ForYouAdapter) mAdapter).addData(data);
    } // 리사이클러뷰에 데이터 추가

    public void clear() {
        ((ForYouAdapter) mAdapter).clear();
    }  // 리사이클러뷰 전체 초기화
}
