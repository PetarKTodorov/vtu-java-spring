package com.example.taxi.services;

import com.example.taxi.models.binding.UserLoginBM;
import com.example.taxi.models.binding.UserRegisterBM;
import com.example.taxi.models.entities.Taxi;
import com.example.taxi.models.entities.User;
import com.example.taxi.models.view.UserVM;
import com.example.taxi.repositories.UserRepository;
import com.example.taxi.session.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, CurrentUser currentUser, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
        this.encoder = encoder;
    }

    public void loginUser(UserLoginBM userData) {
        if (currentUser.getId() != null) {
            throw new RuntimeException("User already logged!");
        }

        User userEntity = userRepository.findByEmail(userData.getEmail()).orElse(null);

        if (userEntity == null) {
            throw new RuntimeException("User not found!");
        }

        if (!encoder.matches(userData.getPassword(), userEntity.getPassword())) {
            throw new RuntimeException("Invalid Credentials!");
        }

        currentUser.setUsername(userEntity.getUsername());
        currentUser.setId(userEntity.getId());
    }

    public void registerUser(UserRegisterBM userData) {
        if (currentUser.getId() != null) {
            throw new RuntimeException("User already logged!");
        }

        if (findByUsername(userData.getUsername()) != null) {
            throw new RuntimeException("Username is taken!");
        }

        if (findByEmail(userData.getEmail()) != null) {
            throw new RuntimeException("Email is taken!");
        }

        User user = modelMapper.map(userData, User.class);
        user.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(user);

        currentUser.setUsername(user.getUsername());
        currentUser.setId(user.getId());
    }

    public void logout() {
        if (!this.isLogged()) {
            throw new RuntimeException("User is not logged!");
        }

        currentUser.setUsername(null);
        currentUser.setId(null);
    }

    public UserVM findByEmail(String email) {
        UserVM user = userRepository.findByEmail(email)
                .map(u -> modelMapper.map(u, UserVM.class))
                .orElse(null);

        return user;
    }

    public UserVM findByUsername(String username) {
        UserVM user = userRepository.findByUsername(username)
                .map(u -> modelMapper.map(u, UserVM.class))
                .orElse(null);

        return user;
    }

    public boolean isLogged() {
        return currentUser.getId() != null;
    }

    public User findCurrentUser() {
        if (!this.isLogged()) {
            throw new RuntimeException("User is not logged!");
        }

        User user = userRepository.findById(currentUser.getId()).orElse(null);

        if (user == null) {
            throw new RuntimeException("User is not found!");
        };

        return user;
    }

    public void likeTaxi(String username, Taxi taxi) {
        User user = userRepository.findByUsername(username).orElse(null);

        user.getLikedTaxis().add(taxi);

        userRepository.save(user);
    }

    public List<User> likedTaxi(Taxi taxi) {
        List<User> users = userRepository.findMassagesByStringIdJPQL(taxi.getId());

        return users;
    }
}
