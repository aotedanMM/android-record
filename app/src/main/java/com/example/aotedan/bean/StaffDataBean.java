package com.example.aotedan.bean;

import java.io.Serializable;
import java.util.List;

public class StaffDataBean {
    private int code ;
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
        private int total;
        private List<ListsBean> list;
        public int getTotal() {
            return total;
        }
        public List<ListsBean> getList() {
            return list;
        }
        public static class ListsBean implements Serializable{
                /**
                 * staffName:"腾东丽"
                 * deptName: "机电部门/机电三科"
                 * staffId： 15
                 * groupId: 12
                 *  baseStationId:3
                 *  timeLong: "0天0小时50分钟"
                 *  inOreTime: 1587629322000
                 *  finalTime:1587629322000
                 */
                private String staffName;
                private String deptName;
                private int staffId;
                private int groupId;
                private int baseStationId;
                private String timeLong;
                private String inOreTime;
                private  String finalTime;

            public String getStaffName() {
                return staffName;
            }

            public String getDeptName() {
                return deptName;
            }

            public int getStaffId() {
                return staffId;
            }

            public int getBaseStationId() {
                return baseStationId;
            }

            public int getGroupId() {
                return groupId;
            }

            public String getFinalTime() {
                return finalTime;
            }

            public String getInOreTime() {
                return inOreTime;
            }

            public String getTimeLong() {
                return timeLong;
            }
        }
    }
}
