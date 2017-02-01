package com.jspark.android.rutimepermission;

import java.util.ArrayList;

/**
 * Created by jsPark on 2017. 2. 1..
 */

public class Contacts {
    private int id;
    private String name;
    private ArrayList<String> phoneNum;

    public Contacts() {
        id = 0;
        name="";
        phoneNum = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum.get(0);
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum.add(phoneNum);
    }

    public void removePhoneNum(int i) {
        this.phoneNum.remove(i);
    }
}