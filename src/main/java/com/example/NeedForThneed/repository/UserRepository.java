package com.example.NeedForThneed.repository;

import com.example.NeedForThneed.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends DistributedRepository<User> {
}
