package com.kh.youtube.service;

import com.kh.youtube.domain.VideoLike;
import com.kh.youtube.repo.VideoLikeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoLikeService {

    @Autowired
    private VideoLikeDAO dao;

    public List<VideoLike> showAll() {
        return dao.findAll();
    }

    public VideoLike show(int id) {
        return dao.findById(id).orElse(null);
    }

    public VideoLike create(VideoLike vo) {
        return dao.save(vo);
    }

    public VideoLike update(VideoLike vo) {
        VideoLike target = dao.findById(vo.getVLikeCode()).orElse(null);
        if(target!=null) {
            return dao.save(vo);
        }
        return null;
    }

    public VideoLike delete(int id) {
        VideoLike target = dao.findById(id).orElse(null);
        dao.delete(target);
        return target;
    }

}
