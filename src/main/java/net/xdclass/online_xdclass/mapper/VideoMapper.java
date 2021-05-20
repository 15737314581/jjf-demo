package net.xdclass.online_xdclass.mapper;

import net.xdclass.online_xdclass.domain.Video;
import net.xdclass.online_xdclass.domain.VideoBanner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoMapper {
    /**
     * 查询全部视频
     * @return
     */
    List<Video> listVideo();

    /**
     * 查询全部轮播图
     * @return
     */
    List<VideoBanner> listVideoBanner();

    /**
     * 查询视频详情，包含章，集信息
     * @param videoId
     * @return
     */
    Video findDetailById(@Param(value = "video_id") int videoId);
}
