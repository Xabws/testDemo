package com.example.baselib.retrofit;

/**
 * Created by a1234 on 2018/9/7.
 * https://github.com/izzyleung/ZhihuDailyPurify/wiki/%E7%9F%A5%E4%B9%8E%E6%97%A5%E6%8A%A5-API-%E5%88%86%E6%9E%90
 */
public class API {
    //Retrofit2 的baseUlr 必须以 /（斜线） 结束
    public static final String BASEURL = "https://news-at.zhihu.com/api/4/";
    public static final String DETAILS = "news/{newsId}";
    /**
     * 获取最新的消息
     */
    public static final String LATEST = "news/latest";
    /**
     * 需要查询 11 月 18 日的消息，before 后的数字应为 20131119
     **/
    public static final String NEWS_BEFORE = "news/before/{date}";
    /**
     * 输入新闻的ID，获取对应新闻的额外信息，如评论数量，所获的『赞』的数量。
     **/
    public static final String NEWS_EXTRA = "story-extra/{newsId}";
    /***
     * 新闻对应长评论查看
     * 使用在 最新消息 中获得的 id，
     * 在 https://news-at.zhihu.com/api/4/story/#{id}/long-comments 中将 id 替换为对应的 id
     * 得到长评论 JSON 格式的内容
     */
    public static final String NEWS_COMMENT_LONG = "story/{newsId}/long-comments";

    /***
     * 新闻对应短评论查看
     * 使用在 最新消息 中获得的 id，
     * 在 https://news-at.zhihu.com/api/4/story/#{id}/long-comments 中将 id 替换为对应的 id
     * 得到长评论 JSON 格式的内容
     */
    public static final String NEWS_COMMENT_SHORT = "story/{newsId}/short-comments";

}
