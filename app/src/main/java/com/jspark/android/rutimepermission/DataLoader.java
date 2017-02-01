package com.jspark.android.rutimepermission;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by jsPark on 2017. 2. 1..
 */

public class DataLoader {

    ArrayList<Contacts> datas;
    Context context;

    public DataLoader(Context context) {
        datas = new ArrayList<>();
        this.context = context;

        load();
    }

    public void load() {

        datas = getContacs();
    }

    public ArrayList<Contacts> getContacs() {
        ArrayList<Contacts> contact = new ArrayList<>();

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String data[] = new String[] {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        Log.w("data[]", "success");

        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";

        Cursor cursor = context.getContentResolver().query(uri, data, null, null, sortOrder);

        if(cursor.moveToFirst()) {
            String phoneNum = "";
            while(cursor.moveToNext()) {
                Contacts mContacts = new Contacts();
                
                int idx = cursor.getColumnIndex(data[0]);
                mContacts.setId(cursor.getInt(idx));
                idx = cursor.getColumnIndex(data[1]);
                mContacts.setName(cursor.getString(idx));
                idx = cursor.getColumnIndex(data[2]);

                if(cursor.getString(idx).length()==10) {
                    if(cursor.getString(idx).startsWith("02")) {
                        phoneNum = cursor.getString(idx).substring(0, 2) + "-" +
                                cursor.getString(idx).substring(2, 6) + "-" +
                                cursor.getString(idx).substring(6);
                    } else {
                        phoneNum = cursor.getString(idx).substring(0,3) +"-" +
                                cursor.getString(idx).substring(3,6)+"-"+
                                cursor.getString(idx).substring(6);
                    }
                } else if(cursor.getString(idx).length()>10) {
                    if (cursor.getString(idx).startsWith("//")) {
                        phoneNum = cursor.getString(idx).substring(2, 5) + "-" +
                                cursor.getString(idx).substring(5, 9) + "-" +
                                cursor.getString(idx).substring(9);
                    } else if(!(cursor.getString(idx).contains("-"))) {
                        phoneNum = cursor.getString(idx).substring(0, 3) + "-" +
                                cursor.getString(idx).substring(3, 7) + "-" +
                                cursor.getString(idx).substring(7);
                    } else {
                        phoneNum = cursor.getString(idx);
                    }
                } else if(cursor.getString(idx).length()==9) {
                    phoneNum = cursor.getString(idx).substring(0, 2) + "-" +
                            cursor.getString(idx).substring(2, 5) + "-" +
                            cursor.getString(idx).substring(5);
                } else {
                    phoneNum = "";
                }
                mContacts.setPhoneNum(phoneNum);
                contact.add(mContacts);
            }
            cursor.close();
        }
        return contact;
    }
}
