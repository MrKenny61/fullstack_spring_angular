package ru.tarelkin.spring_backend_interview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tarelkin.spring_backend_interview.dao.GoodsDao;
import ru.tarelkin.spring_backend_interview.model.Goods;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService{
    private GoodsDao goodsDao;

    @Autowired
    public GoodsServiceImpl(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    @Override
    public List<Goods> findAll() {
        return goodsDao.findAll();
    }

    @Override
    public Goods findById(Integer id) {
        return goodsDao.findById(id).orElseThrow();
    }

    @Override
    public Goods findByName(String name) {
        return goodsDao.findByName(name).orElseThrow();
    }

    @Override
    public Goods save(Goods goods) {
        return goodsDao.save(goods);
    }

    @Override
    public Goods update(Integer id, Goods goodsUpdated) {
        Goods goods = goodsDao.findById(id).orElseThrow();
        goods.setName(goodsUpdated.getName());
        goods.setPrice(goodsUpdated.getPrice());

        return goodsDao.save(goods);
    }

    @Override
    public void deleteById(Integer id) {
        goodsDao.deleteById(id);
    }
}
