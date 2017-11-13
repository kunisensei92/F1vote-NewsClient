package f1vote.newssplider.util;


public class URLUtil {

    public static final int NEWS_TYPE_NEWS = 1;
    public static final int NEWS_TYPE_MOBILE = 2;
    public static final int NEWS_TYPE_CLOUD = 3;
    public static final int NEWS_TYPE_SD = 4;
    public static final int NEWS_TYPE_LAST = 5;
    public static final int NEWS_TYPE_MEMES = 6;


    public static final String BASE_URL_NEWS = "https://www.f1vote.com/ru/f1vote-feed/";
    public static final String BASE_URL_MOBILE = "https://www.f1vote.com/ru/polls/";
    public static final String BASE_URL_CLOUD = "https://www.f1vote.com/ru/important/";
    public static final String BASE_URL_SD = "https://www.f1vote.com/ru/polls/";
    public static final String BASE_URL_LAST = "https://www.f1vote.com/ru/last-news/";
    public static final String BASE_URL_MEMES = "https://www.f1vote.com/ru/memes/";



    public static String getNewsUrl(int newsType) {
        String urlStr = "";
        switch (newsType) {
            case NEWS_TYPE_NEWS:
                urlStr = BASE_URL_NEWS;
                break;
            case NEWS_TYPE_MOBILE:
                urlStr = BASE_URL_MOBILE;
                break;
            case NEWS_TYPE_CLOUD:
                urlStr = BASE_URL_CLOUD;
                break;
            case NEWS_TYPE_SD:
                urlStr = BASE_URL_SD;
                break;
            case NEWS_TYPE_LAST:
                urlStr = BASE_URL_LAST;
                break;
            case NEWS_TYPE_MEMES:
                urlStr = BASE_URL_MEMES;
                break;
            default:
                break;
        }
        return urlStr;
    }

}
