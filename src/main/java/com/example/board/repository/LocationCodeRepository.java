package com.example.board.repository;

import com.example.board.domain.LocationCode;
import com.example.board.domain.LocationCodeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationCodeRepository extends JpaRepository<LocationCode, LocationCodeId> {
    LocationCode findByStep1AndStep2AndStep3(String city, String district, String neighborhood);
}
