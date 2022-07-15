package com.flightapp.schema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.schema.model.PassengerDetail;

@Repository
public interface PassengerDetailRepository extends JpaRepository<PassengerDetail, Integer>{

}
