package com.example.sitecrawler;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HtmlParser {
    public static List<String> findByImgSrc(String html){
        if (html.isEmpty()){
            return Collections.emptyList();
        }

        List<String> imgSrc = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        Elements img = doc.getElementsByTag("img");

        for (Element element : img) {
            String src = element.absUrl("src");
            if (StringUtils.isNotBlank(src)){
                imgSrc.add(src);
            }
        }

        return imgSrc;
    }
}
