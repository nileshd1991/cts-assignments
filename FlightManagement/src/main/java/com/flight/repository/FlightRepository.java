package com.flight.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flight.entity.FlightEntity;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Integer> {

	@Query("select f from FlightEntity f where "
			+ "f.wayType=:wayType"
			+ " and f.fromLocation=:fromLocation"
			+ " and f.toLocation=:toLocation"
			+ " and CAST(f.startTime as date)=CAST(:startTime as date)"
			+ " and CAST(f.returnTime as date)=CAST(:returnTime as date)")
	List<FlightEntity> searchFlights(@Param("wayType") String wayType, @Param("fromLocation") 
			String fromLocation, @Param("toLocation") String toLocation, @Param("startTime") Timestamp startTime,
			@Param("returnTime") Timestamp returnTime);

}
