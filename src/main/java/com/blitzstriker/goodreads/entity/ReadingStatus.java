package com.blitzstriker.goodreads.entity;

public enum ReadingStatus {
    READ ("Read"),
    READING ("Currently Reading"),
    WANT ("Want to Read");

    private final String name;

    ReadingStatus(String name) {
        this.name = name;
    }
}
