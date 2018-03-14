package com.example.sitecrawler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpClient {
    private String url;
    private HashMap<String, String> queryStrings;
    private HashMap<String, String> headers;

    private HttpClient(String url) {
        this.url = url;
    }

    public static HttpClient get(String url){
        return new HttpClient(url);
    }

    public HttpClient queryString(String key, String value){
        if (queryStrings==null){
            queryStrings = new HashMap<>();
        }
        queryStrings.put(key, value);
        return this;
    }
    public HttpClient header(String key, String value){
        if (headers==null){
            headers = new HashMap<>();
        }
        headers.put(key, value);
        return this;
    }

    private String buildQueryString(){
        if (queryStrings==null || queryStrings.isEmpty()){
            return "";
        }else{
            return queryStrings.entrySet().stream()
                    .map(entry -> entry.getKey()+"="+entry.getValue())
                    .collect(Collectors.joining("&"));
        }

    }

    public String asString(){
        try {
            if (url.contains("?")){
                url = url+buildQueryString();
            }else{
                url = url+"?"+buildQueryString();
            }
            URL apiUrl = new URL(url);

            HttpURLConnection con = (HttpURLConnection)apiUrl.openConnection();
            con.setRequestMethod("GET");

            if (headers!=null && !headers.isEmpty()){
                headers.entrySet().forEach(entry -> {
                    con.setRequestProperty(entry.getKey(), entry.getValue());
                });
            }

            int responseCode = con.getResponseCode();

            BufferedReader br;
            if (responseCode == HttpURLConnection.HTTP_OK){
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }else{
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String line;
            StringBuffer response = new StringBuffer();
            while((line = br.readLine())!=null){
                response.append(line);
                response.append("\n");
            }
            br.close();

            return response.toString();

        }catch (Exception e){
            e.printStackTrace();
            return "Not Found";
        }
    }
}
