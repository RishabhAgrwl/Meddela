package com.example.rishabh.meddela;

public class DashboardNotice {

    private String heading;
    private String desc;

    public DashboardNotice(String heading, String desc) {
        this.heading = heading;
        this.desc = desc;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public DashboardNotice(){

    }

}