package f1vote.newssplider.bean;


public class FiveNews {

    private String title;
    private String date;
    private String link;
    private String imgLink;
    private int newsType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    @Override
    public String toString() {
        return "FiveNews [title=" + title + ", date=" + date + ", newsType=" + newsType + ", link=" + link + ", imgLink=" + imgLink + "]";
    }


}
