package com.idenisyss.qrbarscanner.ui.home;

public class HomeModel {

    private  String id;
    private  String title_name;
    private  int image_resurce_path;
    private  int next_page_image;


    public HomeModel(String id, String title_name, int image_resurce_path,int next_page_image) {
        this.id = id;
        this.title_name = title_name;
        this.image_resurce_path = image_resurce_path;
        this.next_page_image = next_page_image;

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

    public int getNext_page_image() {
        return next_page_image;
    }

    public void setNext_page_image(int next_page_image) {
        this.next_page_image = next_page_image;
    }
}
