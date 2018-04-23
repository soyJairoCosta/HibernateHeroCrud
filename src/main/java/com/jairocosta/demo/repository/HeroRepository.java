package com.jairocosta.demo.repository;

import com.jairocosta.demo.model.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Long> {

    List<Hero> findHeroByName(String name);

    List<Hero> findHeroByAlin(String alin);

}