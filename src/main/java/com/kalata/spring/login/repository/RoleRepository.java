package com.kalata.spring.login.repository;


import com.kalata.spring.login.models.ERole;
import com.kalata.spring.login.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Role findByName(ERole name);

}
