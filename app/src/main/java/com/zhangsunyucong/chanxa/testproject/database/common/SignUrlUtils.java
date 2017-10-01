package com.zhangsunyucong.chanxa.testproject.database.common;

import com.zhangsunyucong.chanxa.testproject.App;
import com.zhangsunyucong.chanxa.testproject.manager.AppConfig;

import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Created by hyj on 2017/8/7 0007.
 */

public class SignUrlUtils {

    private static SignUrlUtils mSignUrlUtils;

    private SignUrlUtils() {}

    public static SignUrlUtils getInstance() {
        if(mSignUrlUtils == null) {
            synchronized (SignUrlUtils.class) {
                if(mSignUrlUtils == null) {
                    mSignUrlUtils = new SignUrlUtils();
                }
            }
        }

        return mSignUrlUtils;
    }

    private static final Random RANDOM = new Random();
    private static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyz";

    public String getSign(Map<String, Object> map) {
        //map.put("nonce", getRndStr(6 + RANDOM.nextInt(8)));
        //map.put("timestamp", "" + (System.currentTimeMillis() / 1000L));
        String sign = AESUtils.encryptData(App.getApp().getAESKey(),
                getSignParamsString(map) + "appId=" + AppConfig.AppId);
        return sign;
    }

    public String getSignParamsString(Map<String, Object> map) {
        //map.put("nonce", getRndStr(6 + RANDOM.nextInt(8)));
        //map.put("timestamp", "" + (System.currentTimeMillis() / 1000L));
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : getSortedMapByKey(map).entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return sb.toString();
    }


    /** 获取随机数 */
    private String getRndStr(int length) {
        StringBuilder sb = new StringBuilder();
        char ch;
        for (int i = 0; i < length; i++) {
            ch = CHARS.charAt(RANDOM.nextInt(CHARS.length()));
            sb.append(ch);
        }
        return sb.toString();
    }

    /** 按照key的自然顺序进行排序，并返回 */
    private Map<String, Object> getSortedMapByKey(Map<String, Object> map) {
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        };
        Map<String, Object> treeMap = new TreeMap<>(comparator);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            treeMap.put(entry.getKey(), entry.getValue());
        }
        return treeMap;
    }
}
