package com.example.aotedan.ui.gas;

import java.io.Serializable;

public class GasEntity implements Serializable {
    public String ch4;
    public String co;
    public String o2;
    public String temperature;
    public String humidity;
    public String staff_name;
    public String group_name;
    public String createtime;
    public String temppositionname;
    public GasEntity(){

    }
    public GasEntity(String ch4,String co,String o2, String temperature,String humidity,String staff_name,String group_name,String createtime, String temppositionname){
        this.ch4 = ch4;
        this.co = co;
        this.o2 = o2;
        this.temperature = temperature;
        this.humidity = humidity;
        this.staff_name = staff_name;
        this.group_name = group_name;
        this.createtime = createtime;
        this.temppositionname = temppositionname;
    }

    public void setCh4(String ch4) {
        this.ch4 = ch4;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public void setO2(String o2) {
        this.o2 = o2;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setTemppositionname(String temppositionname) {
        this.temppositionname = temppositionname;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCh4() {
        return ch4;
    }

    public String getCo() {
        return co;
    }

    public String getO2() {
        return o2;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public String getGroup_name() {
        return group_name;
    }

    public String getCreatetime() {
        return createtime;
    }

    public String getTemppositionname() {
        return temppositionname;
    }
}
