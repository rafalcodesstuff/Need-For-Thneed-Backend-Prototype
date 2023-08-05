package com.example.NeedForThneed.service;

import com.example.NeedForThneed.api.UserApi;
import com.example.NeedForThneed.dto.UserDTO;
import com.example.NeedForThneed.entity.User;
import com.example.NeedForThneed.repository.DistributedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractCRUDLService<User, UserDTO> implements UserApi {
    @Autowired
    public UserService(DistributedRepository<User> repository) {
        super(repository);
    }
}
