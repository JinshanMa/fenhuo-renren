package io.renren.modules.fenhuo.utils;

public enum EPlatform {
    Linux("Linux"),
    Windows("windows"),
    Nnknow("unknow");

    private EPlatform(String desc) {
        this.description = desc;
    }

    public String toString() {
        return description;
    }

    private String description;
}
