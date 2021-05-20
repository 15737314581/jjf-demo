package net.xdclass.online_xdclass.controller;

import net.xdclass.online_xdclass.domain.Video;
import net.xdclass.online_xdclass.domain.VideoBanner;
import net.xdclass.online_xdclass.service.VideoService;
import net.xdclass.online_xdclass.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pub/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    /**
     * 视频列表
     *
     * @return
     */
    @RequestMapping("list")
    public JsonData listVideo() {
        List<Video> data = videoService.listVideo();
        return JsonData.buildSuccess(data);

    }

    /**
     * 首页轮播图列表
     *
     * @return
     */
    @GetMapping("list_banner")
    public JsonData indexBanner() {
        List<VideoBanner> data = videoService.listVideoBanner();
        return JsonData.buildSuccess(data);
    }

    /**
     * 查询视频详情，包含章、集信息
     *
     * @param videoId
     * @return
     */
    @GetMapping("find_detail_by_id")
    public JsonData findDetailById(@RequestParam(value = "video_id", required = true) int videoId) {
        Video data = videoService.findDetailById(videoId);
        System.out.println(123123123);
        return JsonData.buildSuccess(data);
    }
}
