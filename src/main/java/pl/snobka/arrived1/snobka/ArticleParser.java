package pl.snobka.arrived1.snobka;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


public class ArticleParser {
    private Document doc = null;

    public void parse(String url)
    {
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Elements masthead = doc.select("div");
//
//        for(Element element: masthead) {
//            Log.d("DUPA", element.toString());
//            Log.d("DUPA", "--------------------------------------------------->>>>>>>");
//        }
        String title = doc.title();
    }
}
