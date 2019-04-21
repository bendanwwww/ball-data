package com.ball.data.dto;

public class NewsDto {

    /** 标题 */
    private String title;
    /** 链接 */
    private String href;
    /** 封面图片 */
    private String coverPic;
    /** [url1,url2,url3,.....] */
    private String newspic;
    /** 内容 */
    private String content;
    /** 来源(0:china.nba,1:hupu,2:sina,3:sohu,4:qq) */
    private String source;
    /** 新闻类型(0: 新闻 1: 图组) */
    private Integer newsType;

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

    public Integer getNewsType() {
        return newsType;
    }

    public void setNewsType(Integer newsType) {
        this.newsType = newsType;
    }
}
