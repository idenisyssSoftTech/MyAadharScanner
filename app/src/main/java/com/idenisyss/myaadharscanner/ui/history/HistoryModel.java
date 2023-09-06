package com.idenisyss.myaadharscanner.ui.history;

public class HistoryModel {

    private  String id;
    private  String code_type;
    private  String date_time;
    private  String data_content;

    public HistoryModel() {
    }

    public HistoryModel(String id, String code_type, String date_time, String data_content) {
        this.id = id;
        this.code_type = code_type;
        this.date_time = date_time;
        this.data_content = data_content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode_type() {
        return code_type;
    }

    public void setCode_type(String code_type) {
        this.code_type = code_type;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getData_content() {
        return data_content;
    }

    public void setData_content(String data_content) {
        this.data_content = data_content;
    }
}
