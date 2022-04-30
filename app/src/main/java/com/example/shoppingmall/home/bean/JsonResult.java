package com.example.shoppingmall.home.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/29
 */
public class JsonResult implements Serializable {

    private int number = 1;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isEditing() {
        return isEditing;
    }

    public void setEditing(boolean editing) {
        isEditing = editing;
    }

    public boolean isChildSelected() {
        return isChildSelected;
    }

    public void setChildSelected(boolean childSelected) {
        isChildSelected = childSelected;
    }

    /**
     * 是否处于编辑状态
     */
    private boolean isEditing;
    /**
     * 是否被选中
     */
    private boolean isChildSelected;

    @JSONField(name = "id")
    private Integer id;
    @JSONField(name = "title")
    private String title;
    @JSONField(name = "summary")
    private String summary;
    @JSONField(name = "price")
    private Integer price;
    @JSONField(name = "point")
    private Double point;
    @JSONField(name = "cover_img")
    private String coverImg;
    @JSONField(name = "create_time")
    private String createTime;
    @JSONField(name = "chapter_list")
    private List<ChapterDTO> chapterList;

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<?> getChapterList() {
        return chapterList;
    }

    public void setChapterList(List<ChapterDTO> chapterList) {
        this.chapterList = chapterList;
    }

    public static class ChapterDTO implements Serializable{

        @JSONField(name = "id")
        private Integer id;
        @JSONField(name = "title")
        private String title;
        @JSONField(name = "ordered")
        private Integer ordered;
        @JSONField(name = "video_id")
        private Object videoId;
        @JSONField(name = "create_time")
        private String createTime;
        @JSONField(name = "episode_list")
        private List<EpisodeListDTO> episodeList;

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

        public Integer getOrdered() {
            return ordered;
        }

        public void setOrdered(Integer ordered) {
            this.ordered = ordered;
        }

        public Object getVideoId() {
            return videoId;
        }

        public void setVideoId(Object videoId) {
            this.videoId = videoId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public List<EpisodeListDTO> getEpisodeList() {
            return episodeList;
        }

        public void setEpisodeList(List<EpisodeListDTO> episodeList) {
            this.episodeList = episodeList;
        }
    }


    public static class EpisodeListDTO implements Serializable{
        @JSONField(name = "id")
        private Integer id;
        @JSONField(name = "title")
        private String title;
        @JSONField(name = "num")
        private Integer num;
        @JSONField(name = "ordered")
        private Integer ordered;
        @JSONField(name = "free")
        private Integer free;
        @JSONField(name = "play_url")
        private String playUrl;
        @JSONField(name = "chapter_id")
        private Object chapterId;
        @JSONField(name = "video_id")
        private Object videoId;
        @JSONField(name = "create_time")
        private Object createTime;

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

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        public Integer getOrdered() {
            return ordered;
        }

        @Override
        public String toString() {
            return "EpisodeListDTO{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", num=" + num +
                    ", ordered=" + ordered +
                    ", free=" + free +
                    ", playUrl='" + playUrl + '\'' +
                    ", chapterId=" + chapterId +
                    ", videoId=" + videoId +
                    ", createTime=" + createTime +
                    '}';
        }

        public void setOrdered(Integer ordered) {
            this.ordered = ordered;
        }

        public Integer getFree() {
            return free;
        }

        public void setFree(Integer free) {
            this.free = free;
        }

        public String getPlayUrl() {
            return playUrl;
        }

        public void setPlayUrl(String playUrl) {
            this.playUrl = playUrl;
        }

        public Object getChapterId() {
            return chapterId;
        }

        public void setChapterId(Object chapterId) {
            this.chapterId = chapterId;
        }

        public Object getVideoId() {
            return videoId;
        }

        public void setVideoId(Object videoId) {
            this.videoId = videoId;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }
    }


}
