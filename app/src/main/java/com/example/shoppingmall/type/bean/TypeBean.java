package com.example.shoppingmall.type.bean;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.example.shoppingmall.home.bean.JsonResult;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/5/1
 */
public class TypeBean implements Serializable {


    @JSONField(name = "code")
    private Integer code;
    @JSONField(name = "data")
    private List<DataDTO> data;
    @JSONField(name = "msg")
    private Object msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public static class DataDTO implements Serializable{
        @JSONField(name = "id")
        private Integer id;
        @JSONField(name = "img")
        private String img;
        @JSONField(name = "title")
        private String title;
        @JSONField(name = "channelId")
        private Integer channelId;
        @JSONField(name = "video_list")
        private List<VideoListDTO> videoList;
        @JSONField(name = "common_categories")
        private List<CommonCategoriesDTO> commonCategories;

        public Object getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getChannelId() {
            return channelId;
        }

        public void setChannelId(Integer channelId) {
            this.channelId = channelId;
        }

        public List<VideoListDTO> getVideoList() {
            return videoList;
        }

        public void setVideoList(List<VideoListDTO> videoList) {
            this.videoList = videoList;
        }

        public List<CommonCategoriesDTO> getCommonCategories() {
            return commonCategories;
        }

        public void setCommonCategories(List<CommonCategoriesDTO> commonCategories) {
            this.commonCategories = commonCategories;
        }

        public static class VideoListDTO implements Serializable {
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
            @JSONField(name = "channel_id")
            private Integer channelId;
            @JSONField(name = "is_hot")
            private Integer isHot;
            @JSONField(name = "chapter_list")
            private List<JsonResult.ChapterDTO> chapterList;

            public VideoListDTO(Integer price, String title, String summary, Integer video_id, Double point, List<JsonResult.ChapterDTO> charpterList, String coverImg) {
                    this.price = price;
                    this.title = title;
                    this.summary = summary;
                    this.id = video_id;
                    this.chapterList = charpterList;
                    this.coverImg = coverImg;
                    this.point = point;
            }

            public VideoListDTO() {

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

            public Integer getChannelId() {
                return channelId;
            }

            public void setChannelId(Integer channelId) {
                this.channelId = channelId;
            }

            public Integer getIsHot() {
                return isHot;
            }

            public void setIsHot(Integer isHot) {
                this.isHot = isHot;
            }

            public List<?> getChapterList() {
                return chapterList;
            }

            public void setChapterList(List<JsonResult.ChapterDTO> chapterList) {
                this.chapterList = chapterList;
            }
        }

        public static class CommonCategoriesDTO implements Serializable{
            @JSONField(name = "id")
            private Integer id;
            @JSONField(name = "img")
            private String img;
            @JSONField(name = "title")
            private String title;
            @JSONField(name = "channel_id")
            private Integer channelId;

            public Object getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Integer getChannelId() {
                return channelId;
            }

            public void setChannelId(Integer channelId) {
                this.channelId = channelId;
            }
        }
    }
}
