package hk.hku.cs.myapplication.entity;

import cn.bmob.v3.BmobUser;

/*
 *  项目名：    AndroidGroupProject_Wangmingsheng
 *  包名：      hk.hku.cs.myapplication.entity
 *  文件名：    MyUser
 *  创建者：    BENNETT
 *  创建时间：  2018/11/20 12:48
 *  描述：      TODO
 */public class MyUser extends BmobUser {
    private int age;
    private Boolean sex;
    private String des;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
