package com.springboot.todo.controller;

import com.springboot.todo.dto.ResponseDTO;
import com.springboot.todo.dto.TodoDTO;
import com.springboot.todo.dto.UserDTO;
import com.springboot.todo.entity.Todo;
import com.springboot.todo.entity.User;
import com.springboot.todo.security.TokenProvider;
import com.springboot.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 어드민 계정...
    @GetMapping
    public ResponseEntity<?> retrieveUserList(){
        List<User> list = userService.retrieveAll();
        ResponseDTO<User> response = ResponseDTO.<User>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    // 회원가입
    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        try{
            User user = User.builder()
                    .userId(userDTO.getUserId())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .userName(userDTO.getUserName())
                    .build();
            User registeredUser = userService.create(user);
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

    // 로그인
    @PostMapping("/login")
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

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveUser(@AuthenticationPrincipal String userId, @PathVariable Long id) {
        User user = userService.retrieveById(id);
        if (user != null) {
            if(user.getUserId().equals(userId)){
                UserDTO responseUserDTO = UserDTO.builder()
                        .id(user.getId())
                        .userName(user.getUserName())
                        .userId(user.getUserId())
                        .build();
                return ResponseEntity.ok().body(responseUserDTO);
            }else{
                ResponseDTO response = ResponseDTO.builder().error("No other user can read it").build();
                return ResponseEntity.badRequest().body(response);
            }
        }else{
            ResponseDTO response = ResponseDTO.builder().error("User is not existed").build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal String userId, @PathVariable Long id, @RequestBody UserDTO userDTO) {
        try{
            User original = userService.retrieveById(id);
            if(original.getUserId().equals(userId)){
                User user = UserDTO.toEntity(userDTO);
                user.setId(original.getId());
                User updated = userService.update(user);

                List<UserDTO> dtos = new ArrayList<>();
                dtos.add(new UserDTO(updated));
                ResponseDTO response = ResponseDTO.<UserDTO>builder().data(dtos).build();
                return ResponseEntity.ok().body(response);
            }else{
                ResponseDTO response = ResponseDTO.builder().error("No other user can update it").build();
                return ResponseEntity.badRequest().body(response);
            }
        }catch (NoSuchElementException e){
            ResponseDTO response = ResponseDTO.builder().error("Entity is not existed").build();
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e){
            ResponseDTO response = ResponseDTO.builder().error("An unexpected error occurred").build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal String userId, @PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            User original = userService.retrieveById(id);
            if(original.getUserId().equals(userId)){
                User user = UserDTO.toEntity(userDTO);
                user.setId(original.getId());

                userService.delete(user);
                return ResponseEntity.ok().body(String.format("User's id(%s) is deleted", id));
            }else{
                ResponseDTO response = ResponseDTO.builder().error("No other user can delete it").build();
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            ResponseDTO response = ResponseDTO.builder().error("An error occurred while deleting a Todo").build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
