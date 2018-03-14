package com.example.sitecrawler;

@FunctionalInterface
public interface DownloadCallback {
    void callback(String filename, int currentLength, int totalLength, int status);
}
