package net.xdclass.online_xdclass.service.impl;

import net.xdclass.online_xdclass.exception.XDException;
import net.xdclass.online_xdclass.mapper.EpisodeMapper;
import net.xdclass.online_xdclass.mapper.PlayRecordMapper;
import net.xdclass.online_xdclass.mapper.VideoMapper;
import net.xdclass.online_xdclass.mapper.VideoOrderMapper;
import net.xdclass.online_xdclass.model.entity.Episode;
import net.xdclass.online_xdclass.model.entity.PlayRecord;
import net.xdclass.online_xdclass.model.entity.Video;
import net.xdclass.online_xdclass.model.entity.VideoOrder;
import net.xdclass.online_xdclass.service.VideoOrderServlet;
import net.xdclass.online_xdclass.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class VideoOederServletImpl implements VideoOrderServlet {
    @Autowired
    private VideoOrderMapper videoOrderMapper;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private EpisodeMapper episodeMapper;
    @Autowired
    private PlayRecordMapper playRecordMapper;


    @Override
    @Transactional
    public int save(int userId, int videoId) {
        VideoOrder videoOrder_old = videoOrderMapper.findByUserIdAndVideoIdAndState(userId,videoId,1);
        if (videoOrder_old != null){
            return 0;
        }
        Video video = videoMapper.findByVideoId(videoId);
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setOutTradeNo(UUID.randomUUID().toString());
        videoOrder.setCreateTime(new Date());
        videoOrder.setState(1);
        videoOrder.setTotalFee(video.getPrice());
        videoOrder.setUserId(userId);
        videoOrder.setVideoId(videoId);
        videoOrder.setVideoImg(video.getCoverImg());
        videoOrder.setVideoTitle(video.getTitle());
        int rows = videoOrderMapper.save(videoOrder);


        // 生成播放纪录
        if (rows == 1){
            Episode episode = episodeMapper.findByVideoId(videoId);
            if (episode == null){
                throw new XDException(-1,"视频没有集信息，清运营人员检查");
            }

            PlayRecord playRecord = new PlayRecord();
            playRecord.setCreateTime(new Date());
            playRecord.setUserId(userId);
            playRecord.setCurrentNum(episode.getNum());
            playRecord.setEpisodeId(episode.getId());
            playRecord.setVideoId(videoId);
            playRecordMapper.save(playRecord);
        }

        return rows;

    }

    @Override
    public List<VideoOrder> listOrderByUserId(Integer userId) {
        List<VideoOrder> videoOrderList = videoOrderMapper.listOrderByUserId(userId);
        return videoOrderList;
    }
}
