package com.example.aotedan.bean;

public class LoginDataBean {
    private int code;
    private String msg;
    private DataBean data;
    public int getCode() {
        return code;
    }

    public DataBean getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    /**
     *         "account":"admin",
     *         "headimg":"/img/admin/1.jpg",
     *         "lastLoginTime":1534921785000,
     *         "phone":"13260084118",
     *         "token":"44d0e8bb3ed8401aab153fcde26507a2",
     *         "userId":0,
     *         "userName":"管理员"
     */

    public static class DataBean{
        private String account;
        private String phone;
        private String token;
        private int userId;
        private String userName;

        public String getAccount() {
            return account;
        }

        public String getPhone() {
            return phone;
        }

        public String getToken() {
            return token;
        }

        public int getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }
    }
}
