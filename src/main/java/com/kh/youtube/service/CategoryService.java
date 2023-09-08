package com.kh.youtube.service;

import com.kh.youtube.domain.Category;
import com.kh.youtube.repo.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryDAO dao;

    public List<Category> showAll(){
        return dao.findAll();
    }

    public Category show(int code){
        return dao.findById(code).orElse(null);
    }

    public Category create(Category category){
        return dao.save(category);
    }

    public Category update(Category category){
        return dao.save(category);
    }

    public Category delete(int code){
        Category data = dao.findById(code).orElse(null);
        dao.delete(data);
        return data;
    }
}
