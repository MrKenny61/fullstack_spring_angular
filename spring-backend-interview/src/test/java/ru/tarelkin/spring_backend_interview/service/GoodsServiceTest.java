package ru.tarelkin.spring_backend_interview.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.tarelkin.spring_backend_interview.dao.GoodsDao;
import ru.tarelkin.spring_backend_interview.model.Goods;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GoodsServiceTest {
    @InjectMocks
    GoodsServiceImpl goodsService;

    @Mock
    GoodsDao goodsDao;


    @Test
    public void findAllGoodsTest() {
        List<Goods> list = Arrays.asList(new Goods(1, "goods_1", 10),
                new Goods(2, "goods_2", 10),
                new Goods(3, "goods_3", 20));

        when(goodsDao.findAll()).thenReturn(list);

        List<Goods> goodsList = goodsService.findAll();

        Assertions.assertThat(goodsList.size()).isEqualTo(3);
        verify(goodsDao, times(1)).findAll();
    }

    @Test
    public void findGoodsByIdTest() {

        when(goodsDao.findById(1)).thenReturn(Optional.of(new Goods(1, "goods_1", 10)));

        Goods goods = goodsService.findById(1);

        Assertions.assertThat(goods.getName()).isEqualTo("goods_1");
        Assertions.assertThat(goods.getPrice()).isEqualTo(10);
        verify(goodsDao, times(1)).findById(1);
    }

    @Test
    public void findGoodsByNameTest() {

        when(goodsDao.findByName("goods_1")).thenReturn(Optional.of(new Goods(1, "goods_1", 10)));

        Goods goods = goodsService.findByName("goods_1");

        Assertions.assertThat(goods.getName()).isEqualTo("goods_1");
        Assertions.assertThat(goods.getPrice()).isEqualTo(10);
        verify(goodsDao, times(1)).findByName("goods_1");
    }

    @Test
    public void saveGoodsTest() {
        Goods goods = new Goods(1, "goods_1", 10);

        goodsService.save(goods);

        verify(goodsDao, times(1)).save(goods);
    }

    @Test
    public void deleteGoodsByIdTest() {
        goodsService.deleteById(1);

        verify(goodsDao, times(1)).deleteById(1);
    }
}
