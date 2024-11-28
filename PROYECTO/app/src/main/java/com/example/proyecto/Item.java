package com.example.proyecto;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("id")
    private Integer id;

    @SerializedName("type")
    private String type;

    @SerializedName("value")
    private Integer value;

    public Item(Integer id, String type, Integer value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}