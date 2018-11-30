package hk.hku.cs.myapplication.entity;

/*
 *  项目名：    AndroidGroupProject_Wangmingsheng
 *  包名：      hk.hku.cs.myapplication.entity
 *  文件名：    CourierData
 *  创建者：    BENNETT
 *  创建时间：  2018/11/20 12:48
 *  描述：      TODO
 */public class CourierData {
    //时间
    private String datatime;
    //状态
    private String remark;
    //城市
    private String zone;

    public String getDatatime() {
        return datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zoon) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return "CouierData{" +
                "datatime='" + datatime + '\'' +
                ", remark='" + remark + '\'' +
                ", zoon='" + zone + '\'' +
                '}';
    }
}
