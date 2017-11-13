package f1vote.newssplider.util;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class HtmlUtil {

        public static String doGet(String urlStr) throws IOException {

        StringBuffer sb = new StringBuffer();

            try {

            HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
            conn.setConnectTimeout(1000);
            conn.getUseCaches();

            if (conn.getResponseCode() == 200) {

                InputStreamReader in = new InputStreamReader(conn.getInputStream(),"utf-8");
                BufferedReader bufferedReader = new BufferedReader(in);
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                in.close();
            } else {
                throw new IOException("Failed to access the internet！");
            }
        } catch (IOException e) {
            throw new IOException("Failed to access the internet！");
        }
        return sb.toString();
    }

}