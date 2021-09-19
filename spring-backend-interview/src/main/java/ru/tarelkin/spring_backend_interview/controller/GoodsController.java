package ru.tarelkin.spring_backend_interview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tarelkin.spring_backend_interview.dao.GoodsDao;
import ru.tarelkin.spring_backend_interview.model.Goods;
import ru.tarelkin.spring_backend_interview.service.GoodsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
@CrossOrigin(origins = "http://localhost:4200")
public class GoodsController {
    private GoodsService goodsService;

    @Autowired
    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @GetMapping
    public List<Goods> goods() {
        return goodsService.findAll();
    }

    @PostMapping
    public Goods createGoods(@RequestBody Goods goods) {
        return goodsService.save(goods);
    }

    //update goods
    @GetMapping("/{id}/edit")
    public Goods editGoods(@PathVariable Integer id) {
        return goodsService.findById(id);
    }

    @PatchMapping("/{id}")
    public Goods updateGoods(@PathVariable Integer id, @RequestBody Goods goodsUpdated) {
        Goods goods = goodsService.findById(id);
        goods.setName(goodsUpdated.getName());
        goods.setPrice(goodsUpdated.getPrice());

        return goodsService.save(goods);
    }

    @DeleteMapping({"/{id}"})
    public Map<String, Boolean> deleteGoods(@PathVariable Integer id) {
        goodsService.findById(id);
        goodsService.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
