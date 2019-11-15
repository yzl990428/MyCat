package com.exmaple.myapplication;


import java.util.List;

public class CatDetail {



    private String id;
    private String url;
    private int width;
    private int height;
    private List<Cat> breeds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Cat> getBreeds() {
        return breeds;
    }

    public void setBreeds(List<Cat> breeds) {
        this.breeds = breeds;
    }



}
