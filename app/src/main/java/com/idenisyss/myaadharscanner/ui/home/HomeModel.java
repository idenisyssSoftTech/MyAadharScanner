package com.idenisyss.myaadharscanner.ui.home;

public class HomeModel {

    private  String id;
    private  String title_name;
    private  int image_resurce_path;


    public HomeModel(String id, String title_name, int image_resurce_path) {
        this.id = id;
        this.title_name = title_name;
        this.image_resurce_path = image_resurce_path;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle_name() {
        return title_name;
    }

    public void setTitle_name(String title_name) {
        this.title_name = title_name;
    }

    public int getImage_resurce_path() {
        return image_resurce_path;
    }

    public void setImage_resurce_path(int image_resurce_path) {
        this.image_resurce_path = image_resurce_path;
    }
}
