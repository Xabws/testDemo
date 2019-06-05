package com.example.a1234.miracle.utils;

import com.example.baselib.retrofit.data.ZHCommend;
import com.example.baselib.retrofit.data.ZHCommendData;
import com.example.baselib.retrofit.data.ZHContent;

import java.util.List;

public class HtmlUtil {
  // css样式，隐藏header
  private static final String HIDE_HEADER_STYLE = "<style>div.headline{display:none;}</style>";

  // css style tag, 需要格式化
  private static final String NEEDED_FORMAT_CSS_TAG = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\"/>";

  // js script tag, 需要格式化
  private static final String NEEDED_FORMAT_JS_TAG = "<script src=\"%s\"></script>";

  public static final String MIME_TYPE = "text/html; charset=utf-8";

  public static final String ENCODING = "utf-8";

  public HtmlUtil() {
  }

  /**
   * 根据css链接生成Link标签
   * @param url String
   * @return String
   */
  public static String createCssTag(String url) {
    return String.format(NEEDED_FORMAT_CSS_TAG, url);
  }

  /**
   * 根据多个css链接生成Link标签
   * @param urls List<String>
   * @return String
   */
  public static String createCssTag(List<String> urls) {
    final StringBuilder sb = new StringBuilder();
    for (String url : urls) {
      sb.append(createCssTag(url));
    }
    return sb.toString();
  }

  /**
   * 根据js链接生成Script标签
   *
   * @param url String
   * @return String
   */
  public static String createJsTag(String url) {

    return String.format(NEEDED_FORMAT_JS_TAG, url);
  }

  /**
   * 根据多个js链接生成Script标签
   *
   * @param urls List<String>
   * @return String
   */
  public static String createJsTag(List<String> urls) {

    final StringBuilder sb = new StringBuilder();
    for (String url : urls) {
      sb.append(createJsTag(url));
    }
    return sb.toString();
  }

  /**
   * 根据样式标签,html字符串,js标签
   * 生成完整的HTML文档
   */
  public static String createHtmlData(String html, List<String> cssList, List<String> jsList) {
    final String css = HtmlUtil.createCssTag(cssList);
    final String js = HtmlUtil.createJsTag(jsList);
    return css.concat(HIDE_HEADER_STYLE).concat(html).concat(js);
  }

  /**
   * 生成知乎日报详情页(添加头布局)
   * @param storyDetailsEntity
   * @return
   */
  public static String structHtml(ZHContent storyDetailsEntity) {
    StringBuilder sb = new StringBuilder();
    sb.append("<div class=\"img-wrap\">")
            .append("<h1 class=\"headline-title\">")
            .append(storyDetailsEntity.getTitle()).append("</h1>")
            .append("<span class=\"img-source\">")
            .append(storyDetailsEntity.getImage_source()).append("</span>")
            .append("<img src=\"").append(storyDetailsEntity.getImage())
            .append("\" alt=\"\">")
            .append("<div class=\"img-mask\"></div>");
    //news_content_style.css和news_header_style.css都是在assets里的
    String mNewsContent = "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_content_style.css\"/>"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_header_style.css\"/>"
            + storyDetailsEntity.getBody().replace("<div class=\"img-place-holder\">", sb.toString());
    return mNewsContent;
  }
}
