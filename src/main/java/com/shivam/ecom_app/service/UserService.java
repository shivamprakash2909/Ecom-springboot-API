package com.shivam.ecom_app.service;
import com.shivam.ecom_app.Exception.ResourceNotFoundException;
import com.shivam.ecom_app.controller.dto.AddressDto;
import com.shivam.ecom_app.controller.dto.UserRequestDto;
import com.shivam.ecom_app.controller.dto.UserResponseDto;
import com.shivam.ecom_app.model.Address;
import com.shivam.ecom_app.model.User;
import com.shivam.ecom_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // use this for creating the object without constructor declaration
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponseDto> fetchAllUser(){
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public UserResponseDto findUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        return mapToResponse(user);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto requestUser){
        User u = userRepository.findById(id).orElseThrow();
        u.setFirstName(requestUser.getFirstName());
        u.setLastName(requestUser.getLastName());
        u.setEmail(requestUser.getEmail());
        if (requestUser.getPassword() != null) {
            u.setPassword(requestUser.getPassword());
        }
        if (requestUser.getAddress() != null) {
            Address address = new Address();
            address.setStreet(requestUser.getAddress().getStreet());
            address.setCity(requestUser.getAddress().getCity());
            address.setState(requestUser.getAddress().getState());
            address.setCountry(requestUser.getAddress().getCountry());
            address.setZipcode(requestUser.getAddress().getZipcode());
            u.setAddress(address);
        }
        return mapToResponse(userRepository.save(u));
    }

    public UserResponseDto createNewUser(UserRequestDto requestUser){
        User user = new User();
        user.setFirstName(requestUser.getFirstName());
        user.setLastName(requestUser.getLastName());
        user.setEmail(requestUser.getEmail());
        user.setPassword(requestUser.getPassword());
        if(requestUser.getAddress() != null){
            Address address = new Address();
            address.setStreet(requestUser.getAddress().getStreet());
            address.setCity(requestUser.getAddress().getCity());
            address.setState(requestUser.getAddress().getState());
            address.setCountry(requestUser.getAddress().getCountry());
            address.setZipcode(requestUser.getAddress().getZipcode());
            user.setAddress(address);
        }
        return mapToResponse(userRepository.save(user));
    }

    public String deleteUser(Long id){
        userRepository.deleteById(id);
        return "User deleted";
    }

    private UserResponseDto mapToResponse(User user){
        UserResponseDto response = new UserResponseDto();
        response.setId(user.getUserId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        if(user.getAddress() != null){
            AddressDto addressDTO = new AddressDto();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDTO);
        }
        return response;
    }
}
