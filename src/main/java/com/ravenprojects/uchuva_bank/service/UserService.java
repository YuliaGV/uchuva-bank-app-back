package com.ravenprojects.uchuva_bank.service;

import com.ravenprojects.uchuva_bank.DTO.UserRegistrationDTO;
import com.ravenprojects.uchuva_bank.model.IdDocumentType;
import com.ravenprojects.uchuva_bank.model.User;
import com.ravenprojects.uchuva_bank.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * Service class for managing user-related operations.
 * This includes user registration, retrieval, and status management.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a new UserService with the necessary dependencies.
     * @param userRepository The repository for user data access.
     * @param passwordEncoder The encoder used for hashing user passwords.
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Retrieves a list of all users, regardless of their active status.
     * @return A list containing all users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a list of all active users.
     * @return A list containing only active users.
     */
    public List<User> getAllActiveUsers() {
        return userRepository.findByActiveTrue();
    }

    /**
     * Finds a user by their document type and document number.
     * @param type The identification document type (e.g., CC, TI).
     * @param number The identification document number.
     * @return An {@link Optional} containing the found user, or an empty Optional if no user is found.
     */
    public Optional<User> findUserByIdDocument(IdDocumentType type, String number) {
        return userRepository.findByIdDocumentTypeAndIdDocumentNumber(type, number);
    }

    /**
     * Registers a new user in the system.
     * It checks for the existence of a user with the same document type and number
     * before proceeding with registration. The password from the DTO is hashed before saving.
     * @param registrationDTO The Data Transfer Object containing user registration details.
     * @return The newly registered {@link User} entity.
     * @throws IllegalArgumentException If a user with the provided document type and number already exists.
     */
    public User registerUser(UserRegistrationDTO registrationDTO) {
        boolean exists = userRepository.existsByIdDocumentTypeAndIdDocumentNumber(
                IdDocumentType.valueOf(registrationDTO.getIdDocumentType()),
                registrationDTO.getIdDocumentNumber()
        );

        if (exists) {
            throw new IllegalArgumentException("A user with that document type and number already exists.");
        }

        User user = User.builder()
                .firstName(registrationDTO.getFirstName())
                .lastName(registrationDTO.getLastName())
                .idDocumentType(IdDocumentType.valueOf(registrationDTO.getIdDocumentType()))
                .idDocumentNumber(registrationDTO.getIdDocumentNumber())
                .email(registrationDTO.getEmail())
                .passwordHash(passwordEncoder.protectPassword(registrationDTO.getPassword()))
                .phone(registrationDTO.getPhone())
                .address(registrationDTO.getAddress())
                .active(true)
                .build();

        return userRepository.save(user);
    }

    /**
     * Deactivates an existing user by setting their active status to false.
     * @param id The unique identifier of the user to deactivate.
     * @throws IllegalArgumentException If no user is found with the given ID.
     */
    public void deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        user.setActive(false);
        userRepository.save(user);
    }
}