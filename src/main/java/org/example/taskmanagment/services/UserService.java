package org.example.taskmanagment.services;

import org.example.taskmanagment.entities.User;
import org.example.taskmanagment.exceptions.InvalidSortDirectionException;
import org.example.taskmanagment.exceptions.InvalidSortFieldException;
import org.example.taskmanagment.exceptions.UserEmailAlreadyExistsException;
import org.example.taskmanagment.exceptions.UserNotFoundException;
import org.example.taskmanagment.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.sound.midi.MidiDevice;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User createUser(User user) {
        if(userRepository.existsByEmail(user.getEmail()))
            throw new UserEmailAlreadyExistsException("User email: " + user.getEmail() + " already exists");

        return userRepository.save(user);
    }

    private final static Set<String> allowedSortFields = new HashSet<>(Arrays.asList("id", "name"));
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        if(userRepository.existsByEmail(user.getEmail()))
            throw new UserEmailAlreadyExistsException("User email: " + user.getEmail() + "already exists");

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
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
