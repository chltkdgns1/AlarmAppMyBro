package com.gkftndltek.mybro;

public class ForYouData {
    private String name;
    private String time; // 올린 날짜
    private String des;
    private long time_end; //  끝

    public long getTime_end() {
        return time_end;
    }
    public void setTime_end(long time_end) {
        this.time_end = time_end;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getDes() {
        return des;
    }
    public void setDes(String des) {
        this.des = des;
    }
}
