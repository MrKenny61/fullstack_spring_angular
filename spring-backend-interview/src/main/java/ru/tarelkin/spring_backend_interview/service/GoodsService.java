package ru.tarelkin.spring_backend_interview.service;

import ru.tarelkin.spring_backend_interview.model.Goods;

import java.util.List;
import java.util.Optional;

public interface GoodsService {
    List<Goods> findAll();
    Goods findById(Integer id);
    Goods findByName(String name);
    Goods save(Goods goods);
    Goods update(Integer id, Goods goods);
    void deleteById(Integer id);
}
