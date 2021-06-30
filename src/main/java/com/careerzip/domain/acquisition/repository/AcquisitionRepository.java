package com.careerzip.domain.acquisition.repository;

import com.careerzip.domain.acquisition.entity.Acquisition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AcquisitionRepository extends JpaRepository<Acquisition, Long> {

    Optional<Acquisition> findByUtmSource(String utmSource);
}
