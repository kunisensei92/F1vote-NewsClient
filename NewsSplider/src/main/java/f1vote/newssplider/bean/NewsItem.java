package f1vote.newssplider.bean;

import android.widget.TextView;
import android.graphics.Typeface;

public class NewsItem {
    private int id;
    private String title;
    private String link;
    private String date;
    private String imgLink;
    private String content;
    private String caption;
    private String poll_button;
    private String category;
    private int newsType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCaption() {return caption; }

    public void setCaption(String caption) { this.caption = caption; }

    public String getPoll_button(){ return poll_button; }

    public void setPoll_button(String poll_button) { this.poll_button = poll_button; }

    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "NewsItem [id=" + id + ", title=" + title + ", link=" + link + ", date=" + date + ", imgLink=" + imgLink
                + ", content=" + content + ", caption=" + caption + ", poll_button=" + poll_button + ", newsType=" + newsType + ", category=" + category +"]";
    }


}
