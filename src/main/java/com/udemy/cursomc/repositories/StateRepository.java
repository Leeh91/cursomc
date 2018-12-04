package com.udemy.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.udemy.cursomc.domain.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {

    @Transactional(readOnly=true)
    List<State> findAllByOrderByName();
}
