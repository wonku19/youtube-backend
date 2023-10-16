package com.kh.youtube.service;

import com.kh.youtube.domain.QVideoComment;
import com.kh.youtube.domain.Subscribe;
import com.kh.youtube.domain.VideoComment;
import com.kh.youtube.repo.SubscribeDAO;
import com.kh.youtube.repo.VideoCommentDAO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoCommentService {
    @Autowired
    private VideoCommentDAO dao;

    @Autowired(required = true)
    private JPAQueryFactory queryFactory;

    private final QVideoComment qVideoComment = QVideoComment.videoComment;

    public List<VideoComment> showAll() {
        return dao.findAll();
    }

    public VideoComment show(int id) {
        return dao.findById(id).orElse(null);
    }

    public VideoComment create(VideoComment vo) {
        return dao.save(vo);
    }

    public VideoComment update(VideoComment vo) {
        VideoComment target = dao.findById(vo.getCommentCode()).orElse(null);
        if(target!=null) {
            return dao.save(vo);
        }
        return null;
    }

    public VideoComment delete(int id) {
        VideoComment target = dao.findById(id).orElse(null);
        dao.delete(target);
        return target;
    }

    public List<VideoComment> findByVideoCode(int id) {
        return dao.findByVideoCode(id);
    }

//    public List<VideoComment> getAllTopLevelComments(int videoCode) {
//        return queryFactory.selectFrom(qVideoComment)
//                .where(qVideoComment.parent.isNull())
//                .where(qVideoComment.videoCode.eq(videoCode))
//                .orderBy(qVideoComment.commentDate.desc())
//                .fetch();
//    }
//
//    public List<VideoComment> getRepliesByCommentId(Integer parentId, int videoCode) {
//        return queryFactory.selectFrom(qVideoComment)
//                .where(qVideoComment.commentParent.eq(parentId))
//                .where(qVideoComment.videoCode.eq(videoCode))
//                .orderBy(qVideoComment.commentDate.asc())
//                .fetch();
//    }

}