package com.example.sitecrawler;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownloader {
    public static boolean download(String imgUrl, String saveDir, DownloadCallback listener) {
        try{
            URL url = new URL(imgUrl);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            int responseCode = httpCon.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK){
                listener.callback(null, 0, 0 , responseCode);
            }
            String filename = "";
            String disposition = httpCon.getHeaderField("Content-Disposition");
            int contentLength = httpCon.getContentLength();

            if (disposition!=null){
                int index = disposition.indexOf("filename=");
                if (index > 0){
                    filename = disposition.substring(index+10, disposition.length()-1);
                }
            }else{
                int indexName = imgUrl.lastIndexOf("/");
                if (indexName == imgUrl.length()-1){
                    imgUrl = imgUrl.substring(1, indexName);
                }
                indexName = imgUrl.lastIndexOf("/");
                filename = imgUrl.substring(indexName, imgUrl.length());
            }
            listener.callback(filename, 0, contentLength, HttpStatus.SC_OK);
            InputStream inputStream = httpCon.getInputStream();
            String saveFilePath = saveDir + File.separator + filename;

            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int currentByteRead = 0;
            int bytesRead = -1;
            byte[] buffer = new byte[1024];

            while((bytesRead = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, bytesRead);
                currentByteRead += bytesRead;
                listener.callback(filename, currentByteRead, contentLength, HttpStatus.SC_OK);
            }

            outputStream.close();
            inputStream.close();

            return true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
