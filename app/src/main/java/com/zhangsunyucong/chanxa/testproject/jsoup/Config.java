package com.zhangsunyucong.chanxa.testproject.jsoup;

/**
 * Created by hyj on 2017/8/15 0015.
 */

public class Config {

    /**
     * CSDN博客基础地址
     */
    public static final String BLOG_HOST = "http://blog.csdn.net/";

    /**
     * 博客分类--全部
     */
    public static final String BLOG_CATEGORY_ALL = "全部";

    /**
     * 博客类型
     */
    public class BLOG_TYPE {
        public static final String BLOG_TYPE_REPOST = "ico ico_type_Repost";
        public static final String BLOG_TYPE_ORIGINAL = "ico ico_type_Original";
        public static final String BLOG_TYPE_TRANSLATED = "ico ico_type_Translated";
    }

    /**
     * 博客每一项的类型
     */
    public class DEF_BLOG_ITEM_TYPE {
        public static final int TITLE = 1; // 标题
        @SuppressWarnings("unused")
        public static final int SUMMARY = 2; // 摘要
        public static final int CONTENT = 3; // 内容
        public static final int IMG = 4; // 图片
        public static final int BOLD_TITLE = 5; // 加粗标题
        public static final int CODE = 6; // 代码
    }

    /**
     * 评论类型
     */
    public class COMMENT_TYPE {
        public static final int PARENT = 1;
        public static final int CHILD = 2;
    }
}
