package f1vote.newssplider.biz;


import f1vote.newssplider.bean.FiveNews;
import f1vote.newssplider.util.HtmlUtil;
import f1vote.newssplider.util.URLUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FiveNewsBiz {
    public List<FiveNews> getFiveNews(int newsType) throws IOException {
        String urlStr = URLUtil.getNewsUrl(newsType);
        String htmlStr = HtmlUtil.doGet(urlStr);

        List<FiveNews> fiveNewsList = new ArrayList<FiveNews>();
        FiveNews fiveNews = null;

        Document doc = Jsoup.parse(htmlStr);

        Elements units = doc.getElementsByClass("post");
        for (int i = 0; i < 5; i++) {
            fiveNews = new FiveNews();
            fiveNews.setNewsType(newsType);

            Element unit_ele = units.get(i);

            try {
                Element h4_ele = unit_ele.getElementsByClass("post-meta").get(0);
                Element ago_ele = h4_ele.child(0);
                String date = ago_ele.text();
                fiveNews.setDate(date.substring(4));

                Element h1_ele = unit_ele.getElementsByClass("entry-title").get(0);
                Element h1_a_ele = h1_ele.child(0);

                String title = h1_a_ele.text();
                String href = h1_a_ele.attr("href");

                fiveNews.setLink(href);
                fiveNews.setTitle(title);

                Element dl_ele = unit_ele.getElementsByTag("a").get(0);// dl
                Element img_ele = dl_ele.child(0);
                String imgLink = img_ele.attr("src");
                fiveNews.setImgLink(imgLink);

            } catch (IndexOutOfBoundsException e) {

            }
            fiveNewsList.add(fiveNews);
        }

        return fiveNewsList;
    }
}
