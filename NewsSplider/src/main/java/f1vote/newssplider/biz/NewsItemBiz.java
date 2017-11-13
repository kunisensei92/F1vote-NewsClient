package f1vote.newssplider.biz;


import f1vote.newssplider.bean.NewsItem;
import f1vote.newssplider.util.HtmlUtil;
import f1vote.newssplider.util.URLUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewsItemBiz {

    public List<NewsItem> getNewsItems(int newsType) throws IOException {

        String urlStr = URLUtil.getNewsUrl(newsType);
        String htmlStr = HtmlUtil.doGet(urlStr);

        List<NewsItem> newsItems = new ArrayList<NewsItem>();
        NewsItem newsItem = null;

        Document doc = Jsoup.parse(htmlStr);
        Elements units = doc.getElementsByClass("post");
        for (int i = 0; i < units.size(); i++) {
            newsItem = new NewsItem();
            newsItem.setNewsType(newsType);


            Element unit_ele = units.get(i);

                try {
                    Element h4_ele = unit_ele.getElementsByClass("post-meta").get(0);
                    Element ago_ele = h4_ele.child(0);
                    String date = ago_ele.text();
                    newsItem.setDate(date.substring(4));

                    Element content_ele = unit_ele.getElementsByClass("post-desc").get(0);// dd
                    String content = content_ele.text();

                    if (unit_ele.hasClass("category-memes")) {
                        newsItem.setCategory("memes");
                    } else if (unit_ele.hasClass("category-razbor")) {
                        newsItem.setCategory("razbor");
                    } else if (date.contains("f1news")) {
                        newsItem.setCategory("f1news");
                    } else if (unit_ele.hasClass("category-podborka")) {
                        newsItem.setCategory("podborka");
                    } else if (unit_ele.hasClass("category-partners")) {
                        newsItem.setCategory("partners");
                    }

                    Element h1_ele = unit_ele.getElementsByClass("entry-title").get(0);
                    Element h1_a_ele = h1_ele.child(0);

                    String title = h1_a_ele.text();
                    String href = h1_a_ele.attr("href");

                    newsItem.setLink(href);
                    newsItem.setTitle(title);
                    newsItem.setContent(content);

                    Element dl_ele = unit_ele.getElementsByTag("a").get(0);
                    Element img_ele = dl_ele.child(0);
                    String imgLink = img_ele.attr("src");
                    newsItem.setImgLink(imgLink);

                    Element poll_button = unit_ele.getElementsByClass("poll-button").get(0);
                    String p_button = poll_button.text();
                    newsItem.setPoll_button(p_button);

                    Element caption_ele = unit_ele.getElementsByClass("post-caption").get(0);
                    String caption = caption_ele.text();
                    newsItem.setCaption(caption);

                } catch (IndexOutOfBoundsException e) {

            }
            newsItems.add(newsItem);

        }
        return newsItems;
    }

}