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
                 * ch4: 0
                 * ch4unit: 0
                 * co: 6553.2
                 * co2: 0
                 * co2unit: 0
                 * counit: 4
                 * createtime: 1587447643000
                 * gasFlag: 1
                 * gaspositionid: 30835
                 * groupName: "X煤集团/机电部门/机电三科"
                 * humidity: 15.6
                 * humidityunit: 0
                 * infotype: 0
                 * isore: 0
                 * o2: 20.7
                 * o2unit: 0
                 * positionx: 12956635.32653836
                 * positiony: 4851645.530286864
                 * positionz: 2
                 * sequenceid: 135
                 * staffid: 80
                 * staffname: "康超生"
                 * temperature: 29.2
                 * temperatureunit: 0
                 * temppositionname: "距离基站3#123.32#距离基站4#149.08"
                 */
                private String ch4;
                private String ch4unit;
                private String co;
                private String counit;
                private String o2;
                private String o2unit;
                private String humidity;
                private String humidityunit;
                private String temperature;
                private String temperatureunit;
                private String staffname;
                private String groupName;
                private String temppositionname;
                private String createtime;

                public String getCreatetime() {
                    return createtime;
                }

            public String getCh4() {
                    return ch4;
                }

                public String getCh4unit() {
                    return ch4unit;
                }

                public String getCo() {
                    return co;
                }

                public String getCounit() {
                    return counit;
                }

                public String getHumidity() {
                    return humidity;
                }

                public String getHumidityunit() {
                    return humidityunit;
                }

                public String getO2() {
                    return o2;
                }

                public String getO2unit() {
                    return o2unit;
                }

                public String getTemperature() {
                    return temperature;
                }

                public String getTemperatureunit() {
                    return temperatureunit;
                }

                public String getGroupName() {
                    return groupName;
                }

                public String getStaffname() {
                    return staffname;
                }

                public String getTemppositionname() {
                    return temppositionname;
                }

        }
    }

}
