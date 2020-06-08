package com.gkftndltek.mybro;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForYouAdapter extends RecyclerView.Adapter<ForYouAdapter.MyViewHolder> {

    private List<ForYouData> data;

    //    private static View.OnClickListener clickListener; //목록 클릭시 이벤트 상황 처리
    private static View.OnTouchListener touchListener;
    private static View.OnClickListener cilicListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView TextView_Up_Time,TextView_Name, TextView_Time,TextView_Des_up;
        private ImageView ImageView_Star;

        private View rootView; //목록 클릭시 이벤트 상황 처리

        public MyViewHolder(View v) {
            super(v);

            ImageView_Star = v.findViewById(R.id.ImageView_Star);
            TextView_Up_Time = v.findViewById(R.id.TextView_Up_Time);
            TextView_Name = v.findViewById(R.id.TextView_Name);
            TextView_Time = v.findViewById(R.id.TextView_Time);
            TextView_Des_up = v.findViewById(R.id.TextView_Des_up);

            v.setClickable(true);
            v.setEnabled(true);
            //v.setOnTouchListener(touchListener);
            v.setOnClickListener(cilicListener);
            rootView = v;
        }
    }


    public ForYouAdapter(View.OnTouchListener touch) { // 찾아온 목록 클릭했을 때의 이벤트 처리 생성자 상태
        data = new ArrayList<>();
        touchListener = touch;
    }

    public ForYouAdapter(View.OnClickListener click) { // 찾아온 목록 클릭했을 때의 이벤트 처리 생성자 상태
        data = new ArrayList<>();
        cilicListener = click;
    }

    public ForYouAdapter() {
        data = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.foryou_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ForYouData fdata = data.get(position);

        holder.TextView_Name.setText("먹는 약 / 예방 접종 이름 - " + fdata.getName());
        holder.TextView_Des_up.setText("약 / 예방 주사 설명 - " + fdata.getDes());
        long t = System.currentTimeMillis();
        Date date = new Date(fdata.getTime_end() - t - 9 * 3600000);
        SimpleDateFormat sdfNow = new SimpleDateFormat("dd일 HH시간 mm분 ss초 전");
        String formatDate = sdfNow.format(date);
        holder.TextView_Time.setText("약먹기까지 남은 시간! - " + formatDate);
        holder.TextView_Up_Time.setText(fdata.getTime());

        System.out.println("도대체 몇인데? " + (fdata.getTime_end() - t) / (3600000*24));
        if((fdata.getTime_end() - t) / (3600000*24) <= 3)
            holder.ImageView_Star.setImageResource(R.drawable.ic_star_up);
        else holder.ImageView_Star.setImageResource(R.drawable.ic_star_down);
        holder.rootView.setTag(position); // 목록 클릭시 이벤트 상황 처리
    }

    public void addData(ForYouData dest) {
        if (dest != null) {
            data.add(dest);
            System.out.println("너 몇번째야? " + data.size());
            notifyItemInserted(data.size() - 1); // 갱신용
        }
    }

    public void remove(int position){
        data.remove(position);
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)

    public void clear() {
        data.clear();
        notifyDataSetChanged();
        // notifyDataSetChanged();
        //  notifyItemRangeRemoved(0, data.size() - 1);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public ForYouData getData(int pos) {
        return data != null ? data.get(pos) : null;
    }
}

