package com.example.shoppingmall.home.bean;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/28
 */
public class ChannelResult extends JSONObject {


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

    public static class DataDTO {
        @JSONField(name = "id")
        private Integer id;
        @JSONField(name = "img")
        private String img;
        @JSONField(name = "title")
        private String title;
        @JSONField(name = "channelId")
        private Integer channelId;

        public Integer getId() {
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
