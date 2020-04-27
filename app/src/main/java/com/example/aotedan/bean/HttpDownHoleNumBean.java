package com.example.aotedan.bean;

public class HttpDownHoleNumBean {
    /**
     * code : 200
     * data : {"limitStaff":0,"seriousTimeStaff":0,"unAttendanceStaff":14999,"overTimeStaff":0,"sumStaff":0,"importantStaff":0}
     * msg : 数据获取成功.
     */
    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {return msg;}
    public void setMsg(String msg) {this.msg = msg;}

    public DataBean getData() {return  data;}
    public void setData(DataBean data) {this.data = data;}

    public static class DataBean {
        private String sumStaff;
        private String overTimeStaff;
        private String seriousTimeStaff;
        private String unAttendanceStaff;
        private String importantStaff;
        private String limitStaff;

        public String getSumStaff() {return sumStaff;}
        public void setSumStaff(String sumStaff) {this.sumStaff = sumStaff;}

        public String getOverTimeStaff() {return overTimeStaff;}
        public void setOverTimeStaff(String overTimeStaff) {this.overTimeStaff = overTimeStaff;}

        public String getSeriousTimeStaff() {return seriousTimeStaff;}
        public void setSeriousTimeStaff(String seriousTimeStaff) {this.seriousTimeStaff = seriousTimeStaff;}

        public String getUnAttendanceStaff() {return unAttendanceStaff;}
        public void setUnAttendanceStaff(String unAttendanceStaff) {this.unAttendanceStaff = unAttendanceStaff ;}

        public String  getImportantStaff() {return importantStaff;}
        public void setImportantStaff(String importantStaff) {this.importantStaff = importantStaff;}

        public String getLimitStaff() {return limitStaff;}
        public void setLimitStaff(String limitStaff){this.limitStaff = limitStaff;}
    }
}
