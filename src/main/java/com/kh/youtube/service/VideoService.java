package com.kh.youtube.service;

import com.kh.youtube.domain.Video;
import com.kh.youtube.domain.VideoComment;
import com.kh.youtube.repo.VideoCommentDAO;
import com.kh.youtube.repo.VideoDAO;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {

    @Autowired
    private VideoDAO dao;

    public Page<Video> showAll(Pageable pageable, BooleanBuilder builder) {
        // dao.findAll() -> List<Video>
        // dao.findAll(pageable) -> Page<Video>
        return dao.findAll(builder, pageable);
    }

    public Video show(int id) {
        return dao.findById(id).orElse(null);
    }

    public Video create(Video vo) {
        return dao.save(vo);
    }

    public Video update(Video vo) {
        Video target = dao.findById(vo.getVideoCode()).orElse(null);
        if(target!=null) {
            return dao.save(vo);
        }
        return null;
    }

    public Video delete(int id) {
        Video target = dao.findById(id).orElse(null);
        dao.delete(target);
        return target;
    }

    public List<Video> findByChannelCode(int code) {
        return dao.findByChannelCode(code);
    }
}