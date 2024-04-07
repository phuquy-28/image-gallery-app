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
    //    public Image(int resourceImage, String name) {
//        this.resourceImage = resourceImage;
//        this.name = name;
//    }
//
//    public int getResourceImage() {
//        return resourceImage;
//    }
//
//    public void setResourceImage(int resourceImage) {
//        this.resourceImage = resourceImage;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
