package com.example.NeedForThneed.controller;

import com.example.NeedForThneed.api.AbstractCRUDLApi;
import com.example.NeedForThneed.dto.UserDTO;
import com.example.NeedForThneed.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController extends AbstractCRUDLController<User, UserDTO> {
    public UserController(AbstractCRUDLApi<User, UserDTO> api) {
        super(api);
    }
}
