package com.example.mrc.campus_social.provider;

import java.util.HashMap;
import java.util.Map;

public class ModelType {

    public static final String TYPE_MOOD = "mood";
    public static final String TYPE_MODULES = "modules";

    public static final int MODE_NEWEST_MESSAGE = 0;
    public static final int MODE_EVENT_ORGNIZE = 1;
    public static final int MODE_FLEA_STREET = 2;
    public static final int MODE_LOSE_AND_FOUND = 3;
    public static final int MODE_CONFRESSION = 4;
    public static final int MODE_ANROUND = 5;
    public static final int MODE_ANSWER_AND_QUESTION = 6;
    public static final int MODE_OTHER = 7;
    public static final int MOOD_MINE_POST = 0;
    public static final int MOOD_ALL_POST = 1;

    public static final Map<Integer, String> MODEL_STRING = new HashMap<Integer, String>() {
        {
            put(0, "最新闻");
            put(1, "活动组织");
            put(2, "跳蚤街");
            put(3, "失物招领");
            put(4, "表白墙");
            put(5, "校园周边");
            put(6, "知识问答");
            put(7, "其他");
        }
    };

    public static final Map<Integer, String> POST_STRING = new HashMap<Integer, String>() {
        {
            put(0, "仅好友可见");
            put(1, "所有人可见");
        }
    };
}
