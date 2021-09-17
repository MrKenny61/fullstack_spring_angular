package ru.tarelkin.spring_backend_interview.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tarelkin.spring_backend_interview.model.Goods;
import ru.tarelkin.spring_backend_interview.model.GoodsList;
import ru.tarelkin.spring_backend_interview.model.Order;
import ru.tarelkin.spring_backend_interview.model.OrderLine;

import java.util.List;

@Repository
public interface OrderLineDao extends JpaRepository<OrderLine, Integer> {
    @Query(value = "select new ru.tarelkin.spring_backend_interview.model.GoodsList(g.id, g.name, ol.count, g.price) "
            + "from OrderLine ol join ol.order o join ol.goods g where o.id = :id")
    List<GoodsList> getGoodsListByOrderId(Integer id);

    OrderLine findOrderLineByOrderAndGoods(Order order, Goods goods);
}
