package net.xdclass.online_xdclass.service.impl;

import net.xdclass.online_xdclass.config.CacheKeyManager;
import net.xdclass.online_xdclass.model.entity.Video;
import net.xdclass.online_xdclass.model.entity.VideoBanner;
import net.xdclass.online_xdclass.mapper.VideoMapper;
import net.xdclass.online_xdclass.service.VideoService;
import net.xdclass.online_xdclass.utils.BaseCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private BaseCache baseCache;

    /**
     * 首页视频列表
     * @return
     */
    @Override
    public List<Video> listVideo() {
        try {
            Object cacheObj = baseCache.getTenMinuteCache().get(CacheKeyManager.INDEX_VIDEO_LIST, () -> {
                List<Video> videoList = videoMapper.listVideo();
                System.out.println("从数据库查询视频列表");
                return videoList;
            });
            if (cacheObj instanceof List){
                List<Video> videoList = (List<Video>) cacheObj;
                return videoList;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 首页轮播图列表
     * @return
     */
    @Override
    public List<VideoBanner> listVideoBanner() {
        try{
            Object cacheObj = baseCache.getTenMinuteCache().get(CacheKeyManager.INDEX_BANNER_KEY, () -> {
                List<VideoBanner> videoBannerList = videoMapper.listVideoBanner();
                return videoBannerList;
            });
            if (cacheObj instanceof List){
                List<VideoBanner> videoBannerList = (List<VideoBanner>) cacheObj;
                return videoBannerList;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 视频详情
     * @param videoId
     * @return
     */
    @Override
    public Video findDetailById(int videoId) {
        // 需要mybatis关联复杂查询

        // 单独构建一个缓存Key，每个视频的key不一样
        String videoDetailKey = String.format(CacheKeyManager.VIDEO_DETAIL,videoId);
        try {

            Object cacheObj = baseCache.getOneHourCache().get(videoDetailKey, () -> {
                Video video = videoMapper.findDetailById(videoId);
                return video;
            });
            if (cacheObj instanceof Video){
                Video video = (Video) cacheObj;
                return video;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
