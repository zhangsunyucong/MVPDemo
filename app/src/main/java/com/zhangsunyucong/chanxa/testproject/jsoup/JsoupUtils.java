package com.zhangsunyucong.chanxa.testproject.jsoup;

import android.text.TextUtils;

import com.zhangsunyucong.chanxa.testproject.ui.main.firstpage.BlogCategory;
import com.zhangsunyucong.chanxa.testproject.ui.main.firstpage.BlogItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyj on 2017/8/15 0015.
 */

public class JsoupUtils {
    private static final String BLOG_URL = "http://blog.csdn.net";

  /*  public static Blogger getBlogger(String html) {
        if (TextUtils.isEmpty(html)) {
            return null;
        }

        Document doc = Jsoup.parse(html);
        String str;
        try {
            str = doc.getElementById("blog_userface").select("a").select("img").attr("src");
        } catch (Exception e) {
            e.printStackTrace();
            str = "";
        }

        Elements headerElement = doc.getElementsByClass("header");
        String title = headerElement.select("h2").text();
        String description = headerElement.select("h3").text();
        String imgUrl = str;
        if (TextUtils.isEmpty(title)) {
            return null;
        }

        Blogger blogger = new Blogger();
        blogger.setTitle(title);
        blogger.setDescription(description);
        blogger.setImgUrl(imgUrl);
        return blogger;
    }*/

    public static List<BlogItem> getBlogList(String category, String html, List<BlogCategory>
            blogCategoryList) {
        List<BlogItem> list = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        Elements blogList = doc.getElementsByClass("article_item");

        String page = doc.getElementsByClass("pagelist").select("span").text().trim();
        int totalPage = getBlogTotalPage(page);

        for (Element blogItem : blogList) {
            BlogItem item = new BlogItem();
            String title = blogItem.select("h1").text();

            String icoType = blogItem.getElementsByClass("ico").get(0).className();
            if (title.contains("置顶")) {
                item.topFlag = 1;
            }
            String description = blogItem.select("div.article_description").text();

            String date = blogItem.getElementsByClass("article_manage").get(0).text();
            String link = BLOG_URL + blogItem.select("h1").select("a").attr("href");

            item.title = title;
            item.content = description;
            item.date = date;
            item.link = link;
            item.totalPage = totalPage;
            item.category = category;
            item.icoType = icoType;
            list.add(item);
        }

        // Blog category
        Elements panelElements = doc.getElementsByClass("panel");
        for (Element panelElement : panelElements) {
            try {
                String panelHead = panelElement.select("ul.panel_head").text();
                if ("文章分类".equals(panelHead)) {
                    Elements categoryElements = panelElement.select("ul.panel_body").select("li");

                    if (categoryElements != null) {
                        blogCategoryList.clear();
                        BlogCategory allBlogCategory = new BlogCategory();
                        allBlogCategory.name = "全部";
                        blogCategoryList.add(0, allBlogCategory);

                        for (Element categoryElement : categoryElements) {
                            BlogCategory blogCategory = new BlogCategory();
                            String name = categoryElement.select("a").text().trim().replace("【", "")
                                    .replace("】", "");
                            String link = categoryElement.select("a").attr("href");
                            blogCategory.name = name.trim();
                            blogCategory.link = link.trim();

                            blogCategoryList.add(blogCategory);
                        }
                    }
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    private static int getBlogTotalPage(String page) {
        int totalPage = 0;
        if (!TextUtils.isEmpty(page)) {
            int pageStart = page.lastIndexOf("共");
            int pageEnd = page.lastIndexOf("页");
            String pageStr = page.substring(pageStart + 1, pageEnd);
            try {
                totalPage = Integer.parseInt(pageStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return totalPage;
    }

}
