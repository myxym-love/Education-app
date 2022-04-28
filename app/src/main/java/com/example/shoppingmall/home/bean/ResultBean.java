package com.example.shoppingmall.home.bean;

import java.util.List;

/**
 * @Description
 * @Author Mao Yu
 * @Date 2022/4/28
 */
public class ResultBean {

    private List<BannerResult.DataDTO> banner_info;


    private List<ChannelResult.DataDTO> channel_info;

    private List<ActResult.DataDTO> act_info;

    private List<VideoResult> video_info;

    public List<VideoResult> getVideo_info() {
        return video_info;
    }

    public void setVideo_info(List<VideoResult> video_info) {
        this.video_info = video_info;
    }

    public List<ActResult.DataDTO> getAct_info() {
        return act_info;
    }

    public void setAct_info(List<ActResult.DataDTO> act_info) {
        this.act_info = act_info;
    }

    public List<BannerResult.DataDTO> getBanner_info() {
        return banner_info;
    }

    public void setBanner_info(List<BannerResult.DataDTO> banner_info) {
        this.banner_info = banner_info;
    }

    public List<ChannelResult.DataDTO> getChannel_info() {
        return channel_info;
    }

    public void setChannel_info(List<ChannelResult.DataDTO> channel_info) {
        this.channel_info = channel_info;
    }


}