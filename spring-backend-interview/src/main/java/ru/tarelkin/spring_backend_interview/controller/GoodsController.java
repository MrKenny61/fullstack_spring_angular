package ru.tarelkin.spring_backend_interview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tarelkin.spring_backend_interview.dao.GoodsDao;
import ru.tarelkin.spring_backend_interview.model.Goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
@CrossOrigin(origins = "http://localhost:4200")
public class GoodsController {
    private GoodsDao goodsDao;

    @Autowired
    public GoodsController(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    //get all goods
    @GetMapping
    public List<Goods> goods() {
        return goodsDao.findAll();
    }

    //create goods rest api
    @PostMapping
    public Goods createGoods(@RequestBody Goods goods) {
        return goodsDao.save(goods);
    }

    //update goods
    @GetMapping("/{id}/edit")
    public ResponseEntity<Goods> editGoods(@PathVariable Integer id) {
        Goods goods = goodsDao.findById(id).orElseThrow();
        return ResponseEntity.ok(goods);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Goods> updateGoods(@PathVariable Integer id, @RequestBody Goods goodsDetails) {
        Goods goods = goodsDao.findById(id).orElseThrow();
        goods.setName(goodsDetails.getName());
        goods.setPrice(goodsDetails.getPrice());

        Goods goodsUpdated = goodsDao.save(goods);
        return ResponseEntity.ok(goodsUpdated);
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Map<String, Boolean>> deleteGoods(@PathVariable Integer id) {
        Goods goods = goodsDao.findById(id).orElseThrow();
        goodsDao.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
