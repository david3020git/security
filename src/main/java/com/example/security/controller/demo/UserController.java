package com.example.security.controller.demo;


import com.example.security.controller.AuthenticationRequest;
import com.example.security.controller.AuthenticationResponse;
import com.example.security.services.AuthenticationService;
import com.example.security.controller.RegisterRequest;
import com.example.security.exception.UserNotFoundException;
import com.example.security.user.User;
import com.example.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"})
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/demo-users")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService service;
    @Autowired
    private UserRepository userRepository;





    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){


        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/user")
    public User newUser(@RequestBody User newUser){


        return userRepository.save(newUser);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }

    //ver todos los usuarios con la cntraseña de codificada

    @GetMapping("/view")
    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        for (User user : users) {
            String encodedPassword = user.getPassword();
            String decodedPassword = passwordEncoder.encode(encodedPassword);
            user.setPassword(decodedPassword);
        }
        return users;
    }




    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    public User updateUser(@RequestBody User newUSer, @PathVariable Long id){

        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUSer.getUsername());
                    user.setName(newUSer.getName());
                    user.setEmail(newUSer.getEmail());
                    return userRepository.save(user);
                }).orElseThrow(()->new UserNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return "User with id " + id + "has been deleted success";

    }



}
