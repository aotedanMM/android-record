package com.example.aotedan.bean;
/**
 * {"code":200,
 * "data":{"deptId":12,"deptName":"X煤集团/机电部门/机电三科","isPerson":3,"jobId":7,"jobName":"技增","remark":"0",
 * "staffIdCard":"136263199102162681","staffName":"祁翰继","staffPhone":"13243210010","staffSex":0},
 * "msg":"成功！"}
 */

public class LoginDataBean {
    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

    public DataBean getData() {
        return data;
    }

    public static class DataBean{
        private int deptId;
        private String deptName;
        private int isPerson;
        private int jobId;
        private String jobName;
        private int remark;
        private String staffIdCard;
        private String staffName;
        private String staffPhone;
        private int staffSex;

        public int getDeptId() {
            return deptId;
        }

        public String getDeptName() {
            return deptName;
        }

        public int getIsPerson() {
            return isPerson;
        }

        public int getJobId() {
            return jobId;
        }

        public String getJobName() {
            return jobName;
        }
        public int getRemark() {
            return remark;
        }

        public String getStaffIdCard() {
            return staffIdCard;
        }

        public String getStaffName() {
            return staffName;
        }

        public String getStaffPhone() {
            return staffPhone;
        }

        public int getStaffSex() {
            return staffSex;
        }
    }
}
