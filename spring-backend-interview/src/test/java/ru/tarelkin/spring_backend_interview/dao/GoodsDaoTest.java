package ru.tarelkin.spring_backend_interview.dao;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.tarelkin.spring_backend_interview.model.Goods;

import java.util.List;

@DataJpaTest
@RunWith(SpringRunner.class)
public class GoodsDaoTest {
    @Autowired
    private GoodsDao goodsDao;

    private static Integer id = 1;

    @Before
    public void setUpData(){
        System.out.println("GoodsDaoTest::setUpData()");

        Goods goods = new Goods();
        goods.setName("goods_" + id);
        goods.setPrice(10);

        Goods g = goodsDao.save(goods);
        id = g.getId();
    }

    @Test
    public void saveGoodsTest() {
        System.out.println("GoodsDaoTest::saveGoodsTest()");

        Goods goods = new Goods();
        goods.setName("test");
        goods.setPrice(10);

        Goods savedGoods = goodsDao.save(goods);

        Assertions.assertThat(savedGoods).isNotNull();
    }

    @Test
    public void findAllGoodsTest() {
        System.out.println("GoodsDaoTest::findAllGoodsTest()");

        List<Goods> goodsList = goodsDao.findAll();

        Assertions.assertThat(goodsList.size()).isGreaterThan(0);
    }

    @Test
    public void findGoodsByIdExistTest() {
        System.out.println("GoodsDaoTest::findGoodsByIdExistTest()");

        Boolean goods = goodsDao.findById(id).isPresent();

        Assertions.assertThat(goods).isTrue();
    }

    @Test
    public void findGoodsByIdNotExistTest() {
        System.out.println("GoodsDaoTest::findGoodsByIdNotExistTest()");

        Boolean goods = goodsDao.findById(-1).isPresent();

        Assertions.assertThat(goods).isFalse();
    }

    @Test
    public void findGoodsByNameExistTest() {
        System.out.println("GoodsDaoTest::findGoodsByNameExistTest()");

        String name = "goods_" + (id-1);
        Goods goods = goodsDao.findByName(name).get();

        Assertions.assertThat(goods.getName()).isEqualTo(name);
    }

    @Test
    public void findGoodsByNameNotExistTest() {
        System.out.println("GoodsDaoTest::findGoodsByNameNotExistTest()");

        String name = "notExist";
        Boolean goods = goodsDao.findByName(name).isPresent();

        Assertions.assertThat(goods).isFalse();
    }

    @Test
    public void updateGoodsTest() {
        System.out.println("GoodsDaoTest::updateGoodsTest()");

        Goods goods = goodsDao.findById(id).get();

        goods.setName("newName");
        goods.setPrice(50);

        Goods updatedGoods = goodsDao.save(goods);

        Assertions.assertThat(updatedGoods.getName()).isEqualTo(goods.getName());
        Assertions.assertThat(updatedGoods.getPrice()).isEqualTo(goods.getPrice());
    }

    @Test
    public void deleteGoodsByIdTest() {
        System.out.println("GoodsDaoTest::deleteGoodsByIdTest()");

        Boolean goodsExist = goodsDao.findById(id).isPresent();
        goodsDao.deleteById(id);
        Boolean goodsNotExist = goodsDao.findById(id).isPresent();

        Assertions.assertThat(goodsExist).isTrue();
        Assertions.assertThat(goodsNotExist).isFalse();
    }
}
