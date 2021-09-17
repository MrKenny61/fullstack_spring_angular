package ru.tarelkin.spring_backend_interview.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tarelkin.spring_backend_interview.model.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {
    @Query("select new ru.tarelkin.spring_backend_interview.model.Order(o.id, o.client, o.date, o.address) from Order o")
    List<Order> findAll();

    @Query("select new ru.tarelkin.spring_backend_interview.model.Order(o.id, o.client, o.date, o.address) from Order o where id=:id")
    Optional<Order> findById(Integer id);
}
