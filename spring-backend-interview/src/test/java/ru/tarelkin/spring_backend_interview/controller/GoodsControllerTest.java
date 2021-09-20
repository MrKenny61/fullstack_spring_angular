package ru.tarelkin.spring_backend_interview.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.tarelkin.spring_backend_interview.model.Goods;
import ru.tarelkin.spring_backend_interview.service.GoodsService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = GoodsController.class)
public class GoodsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private GoodsService goodsService;

    @Test
    public void getAllGoodsTest() throws Exception {
        List<Goods> goodsList = Arrays.asList(new Goods(1, "goods_1", 10),
                new Goods(2, "goods_2", 10));

        when(goodsService.findAll()).thenReturn(goodsList);

        mockMvc.perform(MockMvcRequestBuilders.get("/goods"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("goods_1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(10));
    }

    @Test
    public void createGoodsTest() throws Exception {
        Integer id = 1;
        Goods goods = new Goods(null, "goods_1", 10);
        Goods savedGoods = new Goods(id, "goods_1", 10);

        when(goodsService.save(goods)).thenReturn(savedGoods);

        mockMvc.perform(post("/goods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(goods)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));
        verify(goodsService, times(1)).save(goods);
    }

    @Test
    public void getGoodsForEditTest() throws Exception {
        Integer id = 1;
        Goods goods = new Goods(id, "goods_1", 10);

        when(goodsService.findById(id)).thenReturn(goods);

        mockMvc.perform(MockMvcRequestBuilders.get("/goods/{id}/edit", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("goods_1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(10));
    }

    @Test
    public void updateGoodsTest() throws Exception {
        Integer id = 1;
        Goods goods = new Goods(id, "Goods_1", 10);
        Goods updatedGoods = new Goods(id, "updateGoods", 30);

        when(goodsService.findById(id)).thenReturn(goods);
        when(goodsService.save(updatedGoods)).thenReturn(updatedGoods);

        mockMvc.perform(MockMvcRequestBuilders.patch("/goods/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedGoods)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("updateGoods"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(30));
        verify(goodsService, times(1)).findById(id);
        verify(goodsService, times(1)).save(updatedGoods);
    }

    @Test
    public void deleteGoodsTest() throws Exception {
        Integer id = 1;
        Goods goods = new Goods(1, "goods_1", 10);

        when(goodsService.findById(id)).thenReturn(goods);

        mockMvc.perform(MockMvcRequestBuilders.delete("/goods/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*").exists());

        verify(goodsService, times(1)).findById(id);
        verify(goodsService, times(1)).deleteById(id);
    }
}
