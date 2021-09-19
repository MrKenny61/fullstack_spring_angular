package ru.tarelkin.spring_backend_interview.service;

import ru.tarelkin.spring_backend_interview.model.Goods;

import java.util.List;

public interface GoodsService {
    List<Goods> findAll();
    Goods findById(Integer id);
    Goods findByName(String name);
    Goods save(Goods goods);
    void deleteById(Integer id);
}
