package ru.tarelkin.spring_backend_interview.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tarelkin.spring_backend_interview.model.Goods;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoodsDao extends JpaRepository<Goods, Integer> {
    @Query("select new ru.tarelkin.spring_backend_interview.model.Goods(g.id, g.name, g.price) from Goods g")
    List<Goods> findAll();

    @Query("select new ru.tarelkin.spring_backend_interview.model.Goods(g.id, g.name, g.price) from Goods g where id=:id")
    Optional<Goods> findById(Integer id);

    Optional<Goods> findByName(String name);
}
