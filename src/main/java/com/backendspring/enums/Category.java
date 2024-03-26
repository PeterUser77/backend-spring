package com.backendspring.enums;


public enum Category {
    
    BACK_END("back-end"), 
    FRONT_END("front-end");

    private String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value; // required for @ValueOfEnum
    }

}
