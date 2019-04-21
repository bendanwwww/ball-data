package com.ball.data.domain;

import java.util.Date;

public class News {

    /**  */
    private Integer id;
    /** 标题 */
    private String title;
    /** 链接 */
    private String href;
    /** 摘要1 */
    private String abstract1;
    /** 摘要2 */
    private String abstract2;
    /**  */
    private String pureText;
    /** 封面图片 */
    private String coverPic;
    /** [url1,url2,url3,.....] */
    private String newspic;
    /** 内容 */
    private String content;
    /** 抓取时间 */
    private Date getDate;
    /** 来源 */
    private String source;
    /** 是否为头条(0: 否 1: 是) */
    private String isTop;
    /** 点击量 */
    private Integer clicks;
    /** 新闻类型(0: 新闻 1: 图组) */
    private Integer newsType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getAbstract1() {
        return abstract1;
    }

    public void setAbstract1(String abstract1) {
        this.abstract1 = abstract1;
    }

    public String getAbstract2() {
        return abstract2;
    }

    public void setAbstract2(String abstract2) {
        this.abstract2 = abstract2;
    }

    public String getPureText() {
        return pureText;
    }

    public void setPureText(String pureText) {
        this.pureText = pureText;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public String getNewspic() {
        return newspic;
    }

    public void setNewspic(String newspic) {
        this.newspic = newspic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    public Integer getNewsType() {
        return newsType;
    }

    public void setNewsType(Integer newsType) {
        this.newsType = newsType;
    }

    public Date getGetDate() {
        return getDate;
    }

    public void setGetDate(Date getDate) {
        this.getDate = getDate;
    }

    public Integer getClicks() {
        return clicks;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }
}
