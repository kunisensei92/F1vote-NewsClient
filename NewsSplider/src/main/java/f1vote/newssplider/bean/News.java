package f1vote.newssplider.bean;


public class News {

    public static interface NewsType {
        public static final int TITLE = 1;
        public static final int SUMMARY = 2;
        public static final int CONTENT = 3;
        public static final int CAPTION = 4;
        public static final int IMG = 5;
        public static final int POLL_BUTTON = 6;
    }

    /**
     * 标题
     */
    private String title;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 内容
     */
    private String content;

    private String caption;

    private String poll_button;
    /**
     * 图片链接
     */
    private String imageLink;

    /**
     * 类型
     */
    private int type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
        this.type = NewsType.SUMMARY;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCaption() { return caption; }

    public void setCaption(String caption) { this.caption = caption; }

    public String getPoll_button(){ return poll_button; }

    public void setPoll_button(String poll_button) { this.poll_button = poll_button; }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
        this.type = NewsType.IMG;

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "News [title=" + title + ", summary=" + summary + ", content=" + content + ", caption=" + caption +  ", imageLink=" + imageLink
                + ", poll_button=" + poll_button + ", type=" + type + "]";
    }

}
