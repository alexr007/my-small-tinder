package ua.danit.utils.coockies;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface GreatCookies {
    boolean exists(final CharSequence name);
    Cookie get(final CharSequence name);
    CharSequence getValue(final CharSequence name);
    void add(final Cookie c);
    void remove(final CharSequence name);
    void spill(final HttpServletResponse response);
    List<Cookie> all();
    void die(final CharSequence name);
}
