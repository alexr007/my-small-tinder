package ua.danit.utils;

import java.util.HashMap;

public interface Choice {
    void put(String login, boolean choice);
    String getByChioce();
}
