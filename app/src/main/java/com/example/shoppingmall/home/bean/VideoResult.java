package com.example.shoppingmall.home.bean;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author MaoYu
 * 2021/7/6
 */
public class VideoResult  implements Serializable {

    @JSONField(name = "id")
    private Integer id;

    @JSONField(name = "title")
    private String title;

    @JSONField(name = "summary")
    private String summary;;

    @JSONField(name = "cover_img")
    @JsonProperty("cover_img")
    private String coverImg;

    @JSONField(name = "price")
    private Integer price;

    @JSONField(name = "create_time")
    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @JSONField(name = "point")
    private Double point;

    @JSONField(name = "chapter_list")
    @JsonProperty("chapter_list")
    private List<ChapterResult> chapterResultList;

    public List<ChapterResult> getChapterList() {
        return chapterResultList;
    }

    public void setChapterList(List<ChapterResult> chapterResultList) {
        this.chapterResultList = chapterResultList;
    }

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", coverImg='" + coverImg + '\'' +
                ", price=" + price +
                ", createTime=" + createTime +
                ", point=" + point +
                ", chapterList=" + chapterResultList +
                '}';
    }
}
