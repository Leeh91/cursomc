package com.udemy.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udemy.cursomc.domain.Item;
import com.udemy.cursomc.domain.ItemPK;

public interface ItemRepository extends JpaRepository<Item, ItemPK> {

}
