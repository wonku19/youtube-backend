package com.kh.youtube.service;

import com.kh.youtube.domain.Subscribe;
import com.kh.youtube.repo.SubscribeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscribeService {

    @Autowired
    private SubscribeDAO dao;

    public List<Subscribe> showAll() {
        return dao.findAll();
    }

    public Subscribe show(int id) {
        return dao.findById(id).orElse(null);
    }

    public Subscribe create(Subscribe vo) {
        return dao.save(vo);
    }

    public Subscribe update(Subscribe vo) {
        Subscribe target = dao.findById(vo.getSubsCode()).orElse(null);
        if(target!=null) {
            return dao.save(vo);
        }
        return null;
    }

    public Subscribe delete(int id) {
        Subscribe target = dao.findById(id).orElse(null);
        dao.delete(target);
        return target;
    }

    public List<Subscribe> findByMemberId(String id) {
        return dao.findByMemberId(id);
    }

}
