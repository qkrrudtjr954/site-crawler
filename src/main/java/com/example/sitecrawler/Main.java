package com.example.sitecrawler;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Enter the URL of the website you want to crawl :");
        Scanner in = new Scanner(System.in);
        String url = in.next();
        System.out.println("Connecting to "+url);
        String html = HttpClient.get(url).asString();

        if (html.equals("Not Found")){
            System.out.println("Fail to connecting");
        }else{
            System.out.println("Success to Connecting");
            System.out.println("Parsing to response body ...");

            System.out.println("Crawling images ...");
            List<String> imgList = HtmlParser.findByImgSrc(html);

            imgList.stream().forEach(imgUrl -> {
                HttpDownloader.download(imgUrl, "./image", ((filename, currentLength, totalLength, status) -> {
                    ProgressBar.drawProgressBar(filename, currentLength, totalLength);
                }));
                System.out.println("");
            });
        }
    }
}
