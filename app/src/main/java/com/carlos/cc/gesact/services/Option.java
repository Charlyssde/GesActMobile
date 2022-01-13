package com.carlos.cc.gesact.services;

public class Option {

    String value;
    int key;

    public Option(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public Option() {
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
