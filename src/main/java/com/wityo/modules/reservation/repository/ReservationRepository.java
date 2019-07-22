package com.wityo.modules.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wityo.modules.reservation.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	
	@Query(value="select * from reservation where customer_id=?1", nativeQuery = true)
	Reservation findByCustomerId(Long id);

}
