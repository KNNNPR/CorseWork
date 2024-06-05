package com.example.corsework;

import java.io.Serializable;

public class Item implements Serializable {
    private String imageUrl;
    private String name;
    private String price;
    private String key;

    public Item() {}

    public Item(String imageUrl, String name, String price) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
    }

    public Item(String imageUrl, String name, String price, String key) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
        this.key = key;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
