package com.mvc_bd.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.mvc_bd.model.Cliente;
 
 
 
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Repository methods
}