package com.example.sitecrawler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponse<T> {
    private int statusCode;
    private String statusText;
    private T body;
}
