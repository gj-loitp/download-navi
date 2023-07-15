package com.roy.downloader.core;

import android.webkit.CookieManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;

import javax.net.ssl.HttpsURLConnection;

import static android.text.format.DateUtils.SECOND_IN_MILLIS;
import static java.net.HttpURLConnection.HTTP_MOVED_PERM;
import static java.net.HttpURLConnection.HTTP_MOVED_TEMP;
import static java.net.HttpURLConnection.HTTP_SEE_OTHER;

/*
 * The wrapper for HttpUrlConnection.
 */

public class HttpConnection implements Runnable {
    /* Can't be more than 7 */
    private static final int MAX_REDIRECTS = 5;
    public static final int DEFAULT_TIMEOUT = (int) (20 * SECOND_IN_MILLIS);
    public static final int HTTP_TEMPORARY_REDIRECT = 307;
    public static final int HTTP_PERMANENT_REDIRECT = 308;

    private URL url;
    private final TLSSocketFactory socketFactory;
    private Listener listener;
    private int timeout = DEFAULT_TIMEOUT;
    private String referer;
    private boolean contentRangeLength = false;

    public interface Listener {
        void onConnectionCreated(HttpURLConnection conn);

        void onResponseHandle(HttpURLConnection conn, int code, String message);

        void onMoved(String newUrl, boolean permanently);

        void onIOException(IOException e);

        void onTooManyRedirects();
    }

    public HttpConnection(String url) throws MalformedURLException, GeneralSecurityException {
        this.url = new URL(url);
        this.socketFactory = new TLSSocketFactory();
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /*
     * Trying to get length via `Content-Range` if the server responds with
     * `Transfer-Encoding: chunked` and the `Content-Length` header
     * is not set as required by RFC 7230.
     */
    public void contentRangeLength(boolean contentRangeLength) {
        this.contentRangeLength = contentRangeLength;
    }

    /*
     * The non-negative number of milliseconds to wait before the connection timed out.
     * Zero is interpreted as an infinite timeout
     */

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public void run() {
        var redirectionCount = 0;
        var requestContentRange = false;
        while (redirectionCount++ < MAX_REDIRECTS) {
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setInstanceFollowRedirects(false);
                conn.setConnectTimeout(timeout);
                conn.setReadTimeout(timeout);
                conn.setRequestProperty("Accept-Encoding", "identity");

                // Get the cookies for the current domain.
                var cookiesString = CookieManager.getInstance().getCookie(url.toString());

                // Only add the cookies if they are not null.
                if (cookiesString != null) {
                    // Add the cookies to the header property.
                    conn.setRequestProperty("Cookie", cookiesString);
                }

                if (referer != null && !referer.isEmpty()) {
                    conn.setRequestProperty("Referer", referer);
                }
                if (requestContentRange) {
                    conn.setRequestProperty("Range", "bytes=0-");
                }

                if (conn instanceof HttpsURLConnection)
                    ((HttpsURLConnection) conn).setSSLSocketFactory(socketFactory);

                if (listener != null)
                    listener.onConnectionCreated(conn);

                int responseCode = conn.getResponseCode();
                switch (responseCode) {
                    case HTTP_MOVED_PERM:
                    case HTTP_MOVED_TEMP:
                    case HTTP_SEE_OTHER:
                    case HTTP_TEMPORARY_REDIRECT:
                    case HTTP_PERMANENT_REDIRECT:
                        String location = conn.getHeaderField("Location");
                        url = new URL(url, location);
                        if (listener != null)
                            listener.onMoved(
                                    url.toString(),
                                    responseCode == HTTP_MOVED_PERM
                                            || responseCode == HTTP_PERMANENT_REDIRECT
                            );
                        continue;
                    default:
                        if (requestContentRange) {
                            if (responseCode != HttpURLConnection.HTTP_OK &&
                                    responseCode != HttpURLConnection.HTTP_PARTIAL) {
                                // Try without range
                                requestContentRange = false;
                                continue;
                            }
                        } else if (contentRangeLength) {
                            var noContentLength = conn.getHeaderField("content-length") == null;
                            var chunked = "chunked".equals(conn.getHeaderField("Transfer-Encoding"));
                            if (chunked && noContentLength) {
                                // Server responds with `Transfer-Encoding: chunked` and
                                // the `Content-Length` header is not set as required by RFC 7230.
                                // Trying to get length via `Content-Range`.
                                requestContentRange = true;
                                continue;
                            }
                        }
                        if (listener != null)
                            listener.onResponseHandle(conn, responseCode, conn.getResponseMessage());
                        return;
                }

            } catch (IOException e) {
                if (listener != null)
                    listener.onIOException(e);
                return;

            } finally {
                if (conn != null)
                    conn.disconnect();
            }
        }

        if (listener != null)
            listener.onTooManyRedirects();
    }
}
