package com.flightapp.schema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flightapp.schema.model.AirlineEntity;

@Repository
public interface AirlineRepository extends JpaRepository<AirlineEntity, Integer>{

}
