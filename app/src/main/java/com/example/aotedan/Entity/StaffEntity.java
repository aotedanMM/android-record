package com.example.aotedan.Entity;

import java.io.Serializable;

public class StaffEntity implements Serializable {
    public String staffName;
    public String deptName;
    public int staffId;
    public int groupId;
    public int baseStationId;
    public String timeLong;
    public String inOreTime;
    public  String finalTime;
    public StaffEntity(){

    }
    public StaffEntity(String staffName, String deptName,int staffId,int groupId,int baseStationId,String timeLong,String inOreTime,String finalTime ){
            this.staffName = staffName;
            this.deptName = deptName;
            this.staffId = staffId;
            this.groupId = groupId;
            this.baseStationId = baseStationId;
            this.timeLong = timeLong;
            this.inOreTime = inOreTime;
            this.finalTime = finalTime;
    }

    public int getStaffId() {
        return staffId;
    }

    public String getTimeLong() {
        return timeLong;
    }

    public String getInOreTime() {
        return inOreTime;
    }

    public String getFinalTime() {
        return finalTime;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getBaseStationId() {
        return baseStationId;
    }

    public String getDeptName() {
        return deptName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setBaseStationId(int baseStationId) {
        this.baseStationId = baseStationId;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setFinalTime(String finalTime) {
        this.finalTime = finalTime;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setInOreTime(String inOreTime) {
        this.inOreTime = inOreTime;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public void setTimeLong(String timeLong) {
        this.timeLong = timeLong;
    }
}
