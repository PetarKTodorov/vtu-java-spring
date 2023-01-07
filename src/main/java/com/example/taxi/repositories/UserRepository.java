package com.example.taxi.repositories;

import com.example.taxi.models.entities.Taxi;
import com.example.taxi.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    //find MessageEntities by String ID via JPQL
    @Query("select u from User u join u.likedTaxis string where string.id = :users_id")
    List<User> findMassagesByStringIdJPQL(@Param("users_id") long users_id);

}
