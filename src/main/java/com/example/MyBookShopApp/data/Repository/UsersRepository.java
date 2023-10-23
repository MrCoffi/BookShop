package com.example.MyBookShopApp.data.Repository;


import com.example.MyBookShopApp.data.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String name);

    Boolean existsByGitId(Integer gitId);

    Users findUsersByGitId(Integer gitId);

    Users findUsersByPhone(String s);
}
