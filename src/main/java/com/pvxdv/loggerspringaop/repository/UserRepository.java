package com.pvxdv.loggerspringaop.repository;


import com.pvxdv.loggerspringaop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
