package com.example.imagegallery;

public class Image {
//    private int resourceImage;
    private String name;
    private String urlImage;

    public Image() {
    }

    public Image(String name, String urlImage) {
        this.name = name;
        this.urlImage = urlImage;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
