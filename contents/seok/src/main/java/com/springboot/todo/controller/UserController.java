package com.springboot.todo.controller;

import com.springboot.todo.dto.ResponseDTO;
import com.springboot.todo.dto.UserDTO;
import com.springboot.todo.entity.User;
import com.springboot.todo.security.TokenProvider;
import com.springboot.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        try{
            User user = User.builder()
                    .userId(userDTO.getUserId())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .userName(userDTO.getUserName())
                    .build();
            User registeredUser = userService.createUser(user);
            UserDTO responseUserDTO = UserDTO.builder()
                    .userId(registeredUser.getUserId())
                    .id(registeredUser.getId())
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        }catch (Exception e){
            ResponseDTO response = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }

    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){
        User user = userService.getByCredentials(userDTO.getUserId(), userDTO.getPassword(), passwordEncoder);
        if(user!=null){
            final String token = tokenProvider.createToken(user);
            UserDTO responseUserDTO = UserDTO.builder()
                    .userId(user.getUserId())
                    .id(user.getId())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        }else{
            ResponseDTO response = ResponseDTO.builder().error("Login faild").build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
