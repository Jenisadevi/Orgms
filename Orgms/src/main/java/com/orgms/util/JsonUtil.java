package com.orgms.util;

import com.google.gson.Gson;
public class JsonUtil {
    private static final Gson gson = new Gson();
    public static String toJson(Object o){ return gson.toJson(o); }
    public static <T> T fromJson(String json, Class<T> cls){ return gson.fromJson(json, cls); }
}
