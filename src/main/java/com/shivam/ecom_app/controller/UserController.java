package com.shivam.ecom_app.controller;
import com.shivam.ecom_app.controller.dto.UserRequestDto;
import com.shivam.ecom_app.controller.dto.UserResponseDto;
import com.shivam.ecom_app.model.User;
import com.shivam.ecom_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController //to declare the controller is a REST controller
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor // use this for creating the object without constructor declaration
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
         return new ResponseEntity<>( userService.fetchAllUser(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id){
        return new ResponseEntity<>( userService.findUserById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> changeUserData(@PathVariable Long id, @RequestBody UserRequestDto requestUser){
        return new ResponseEntity<>(userService.updateUser(id,requestUser), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<UserResponseDto> addUser(@RequestBody UserRequestDto requestUser){
        return new ResponseEntity<>(userService.createNewUser(requestUser),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeUser(@PathVariable Long id){
        return new ResponseEntity<>(userService.deleteUser(id),HttpStatus.OK);
    }
}
