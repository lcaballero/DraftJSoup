package com.company;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;


public class Main {

    public static void main(String[] args) throws IOException {
        new Main().start();
    }

    public void start() throws IOException {
        Connection.Response res = scrapePage();
        Document doc = res.parse();
        parse(doc);
    }

    public Connection.Response scrapePage() throws IOException {
        Connection.Response response = connect("http://news.ycombinator.com/")
                .execute();

        Map<String,String> cookies = response.cookies();

        response = connect("https://news.ycombinator.com/")
                .cookies(cookies)
                .execute();

        return response;
    }

    public Connection connect(String url) {
        return addDefaultHeaders(Jsoup.connect(url));
    }

    public Connection addDefaultHeaders(Connection conn) {
        return
        conn.header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
            .header("accept-encoding","gzip,deflate,sdch")
            .header("accept-language", "en-US,en;q=0.8")
            .header("cache-control", "max-age=0")
            .header("referer", "https://news.ycombinator.com/")
            .header("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36");
    }

    public void parse(Document doc) {
        Elements titles = doc.select("td.title > a");

        for (Element e : titles) {
            System.out.println(e.html());
        }
    }
}
