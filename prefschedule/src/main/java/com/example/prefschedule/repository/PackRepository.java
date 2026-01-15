package com.example.prefschedule.repository;

import com.example.prefschedule.model.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PackRepository extends JpaRepository<Pack, Long> {

    List<Pack> findByYearAndSemester(Integer year, Integer semester);
}