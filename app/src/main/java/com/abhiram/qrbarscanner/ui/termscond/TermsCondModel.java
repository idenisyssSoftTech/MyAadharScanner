package com.abhiram.qrbarscanner.ui.termscond;

public class TermsCondModel {
    String titlename;
    String descrtion;

    public TermsCondModel(String titlename, String descrtion) {
        this.titlename = titlename;
        this.descrtion = descrtion;
    }

    public String getTitlename() {
        return titlename;
    }

    public void setTitlename(String titlename) {
        this.titlename = titlename;
    }

    public String getDescrtion() {
        return descrtion;
    }

    public void setDescrtion(String descrtion) {
        this.descrtion = descrtion;
    }
}
