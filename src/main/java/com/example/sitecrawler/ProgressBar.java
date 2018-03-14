package com.example.sitecrawler;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProgressBar {
    public static void drawProgressBar(String filename, int current, int total){
        System.out.print("\r");

        int progress = (current*100)/total;
        final int count = progress>>1;

        String progressBar = Stream.iterate(0, n -> n+1).limit(50).map(n -> n <= count ? "#" : " ").collect(Collectors.joining());
        System.out.print(String.format("%s %02d [%s] %d/%d", filename, progress, progressBar, current, total));
        try{
            Thread.sleep(100);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
