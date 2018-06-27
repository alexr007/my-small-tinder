package ua.danit.utils.coockies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CookiesStandart implements GreatCookies {
    static Logger log = LoggerFactory.getLogger(CookiesStandart.class);

    private final Map<CharSequence, Cookie> storage;

    public CookiesStandart() {
        this(new Cookie[]{});
    }

    public CookiesStandart(final ServletRequest request) {
        this((HttpServletRequest)request);
    }

    public CookiesStandart(final HttpServletRequest req) {
        this(req.getCookies());
    }

    public CookiesStandart(final Cookie[] cookies) {
        this.storage = new NullableArrayWrapper<>(cookies)
                .get()
                .stream()
                .collect(Collectors.toMap(Cookie::getName,
                        (Function<Cookie, CookieTimed>) cookie -> new CookieTimed(cookie)
                        //       Function.identity()
                ));
    }

    @Override
    public boolean exists(final CharSequence name) {
        return storage.containsKey(name);
    }

    @Override
    public Cookie get(final CharSequence name) {
        return storage.get(name);
    }

    @Override
    public CharSequence getValue(final CharSequence name) {
        return storage.get(name).getValue();
    }

    @Override
    public void add(final Cookie c) {
        storage.put(c.getName(), c);
    }

    @Override
    public void remove(final CharSequence name) {
        if (storage.containsKey(name)) {
            Cookie c = storage.get(name);
            c.setMaxAge(0);
            storage.put(name, c);
        }
    }

    @Override
    public void spill(final HttpServletResponse response) {
        log.trace("entering CookiesStandard spill");
        storage.forEach((name, cookie) -> response.addCookie(cookie));
        log.trace("leaving CookiesStandard spill");
    }

    @Override
    public List<Cookie> all() {
        return storage.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public void die(CharSequence name) {
        storage.get(name).setMaxAge(0);
    }

    public static void main(String[] args) {
        Cookie[] cc = new Cookie[0];
        CookiesStandart cookies = new CookiesStandart(cc);
        if (cookies.exists("U_ID")) {

        }
    }
}
