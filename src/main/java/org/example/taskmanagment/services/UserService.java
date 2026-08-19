package org.example.taskmanagment.services;

import org.example.taskmanagment.dto.user.request.CreateUserRequest;
import org.example.taskmanagment.dto.user.request.UpdateUserRequest;
import org.example.taskmanagment.entities.User;
import org.example.taskmanagment.exceptions.InvalidSortDirectionException;
import org.example.taskmanagment.exceptions.InvalidSortFieldException;
import org.example.taskmanagment.exceptions.UserEmailAlreadyExistsException;
import org.example.taskmanagment.exceptions.UserNotFoundException;
import org.example.taskmanagment.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sound.midi.MidiDevice;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final static Set<String> allowedSortFields = new HashSet<>(Arrays.asList("id", "name"));

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(CreateUserRequest userDetails) {
        if(userRepository.existsByEmail(userDetails.getEmailID()))
            throw new UserEmailAlreadyExistsException("User email: " + userDetails.getEmailID() + " already exists");

        User user = new User();
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmailID());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        user.setRole(userDetails.getRole());
        return userRepository.save(user);
    }

    public User updateUser(Long id, UpdateUserRequest userDetails) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        if(userRepository.existsByEmail(userDetails.getEmailID()))
            throw new UserEmailAlreadyExistsException("User email: " + userDetails.getEmailID() + "already exists");

        if (userDetails.getName() != null && !userDetails.getName().isBlank()) {
            existingUser.setName(userDetails.getName());
        }
        if (userDetails.getEmailID() != null && !userDetails.getEmailID().isBlank()) {
            existingUser.setEmail(userDetails.getEmailID());
        }
        if (userDetails.getPassword() != null && !userDetails.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));;
        }

        if (userDetails.getRole() != null) {
            existingUser.setRole(userDetails.getRole());
        }

        return userRepository.save(existingUser);
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    public Page<User> getAllUsers(Integer page, Integer size, String sortField, String sortDirection) {
        page = page == null ? 0 : page;
        size = size == null ? 10 : size;
        sortField = sortField == null ? "id" : sortField;
        String direction = sortDirection == null ? "asc" : sortDirection;
        if(!allowedSortFields.contains(sortField)) throw new InvalidSortFieldException("Invalid sort field: " + sortField);

        Sort sort = Sort.by(Sort.Direction.fromOptionalString(direction)
        .orElseThrow(() -> new InvalidSortDirectionException("Invalid sort direction " + direction)),
        sortField);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageRequest);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        userRepository.deleteById(id);
    }
}
