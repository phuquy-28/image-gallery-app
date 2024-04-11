package com.example.imagegallery;

public class UploadImage {
    private String mName;
    private String mUrlImage;

    public UploadImage() {
    }

    public UploadImage(String name, String urlImage) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        mName = name;
        mUrlImage = urlImage;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUrlImage() {
        return mUrlImage;
    }

    public void setUrlImage(String urlImage) {
        mUrlImage = urlImage;
    }
}