package com.kalata.spring.login.repository;

import com.kalata.spring.login.models.Pays;
import com.kalata.spring.login.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    Region findByNomregion(String nomregion);
}
