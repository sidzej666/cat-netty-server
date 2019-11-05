package com.catAndDogStudio.cat.data;

import java.util.HashMap;
import java.util.Map;

public class CatsData {
    private final Map<String, String> catNames = new HashMap<>();

    public String getCatName(final String channelId) {
        return catNames.get(channelId);
    }
    public void setCatName(final String channelId, final String catName) {
        catNames.put(channelId, catName);
    }
}
