package com.example.aotedan.bean;

import android.provider.ContactsContract;

public class DownHoleNumBean {
    /**
     * code: 200
     * data:
     * list: (10) [{…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}, {…}]
     * total: 40
     * msg: "成功！"
     */
    private int code ;
    private String msg;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }


        public DataBean getData() {
        return data;
    }
    public static class DataBean{
        private int total;

        public int getTotal() {
            return total;
        }
    }
}
