package com.example.shoppingmall.home.bean;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/27
 */
public class BannerResult extends JSONObject {

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
        @JSONField(name = "url")
        private String url;
        @JSONField(name = "img")
        private String img;
        @JSONField(name = "weight")
        private Integer weight;
        @JSONField(name = "create_time")
        private Object createTime;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "code=" + code +
                ", data=" + data +
                ", msg=" + msg +
                '}';
    }
}
