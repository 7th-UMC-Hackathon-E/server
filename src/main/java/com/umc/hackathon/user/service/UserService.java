package com.umc.hackathon.user.service;

import com.umc.hackathon.user.dto.UserRequestDTO;
import com.umc.hackathon.user.dto.UserResponseDTO;
import com.umc.hackathon.user.entity.User;
import com.umc.hackathon.user.entity.Zodiac;
import com.umc.hackathon.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void RegisterUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        LocalDate birth = userRequestDTO.getBirth();
        user.setName(userRequestDTO.getName());
        user.setBirth(birth);
        user.setZodiac(Zodiac.fromDate(birth));
        userRepository.save(user);
    }

    public UserResponseDTO GetUserData() {
        User user = userRepository.findByUserId(1L);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setBirth(user.getBirth());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setZodiac(user.getZodiac());
        return userResponseDTO;
    }
}
