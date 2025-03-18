package com.example.AdminXpert.Repository;

import com.example.AdminXpert.Entity.ConnectionString;
import com.example.AdminXpert.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConnectionStringRepository extends JpaRepository<ConnectionString,Long> {
    Optional<ConnectionString> findTopByUserOrderByIdDesc(Users user);
}
