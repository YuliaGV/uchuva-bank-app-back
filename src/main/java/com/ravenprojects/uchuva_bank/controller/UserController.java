package com.ravenprojects.uchuva_bank.controller;

import com.ravenprojects.uchuva_bank.DTO.UserRegistrationDTO;
import com.ravenprojects.uchuva_bank.model.IdDocumentType;
import com.ravenprojects.uchuva_bank.model.User;
import com.ravenprojects.uchuva_bank.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing user-related operations.
 * Exposes endpoints for user registration, retrieval, and deactivation.
 */
@RestController // Marks this class as a Spring REST Controller
@RequestMapping("/api/users") // Base path for all endpoints in this controller
public class UserController {

    private final UserService userService;

    /**
     * Constructs a new UserController with the injected UserService.
     * @param userService The service responsible for user business logic.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * Retrieves a list of all users in the system.
     * @return A ResponseEntity with a list of all users and HTTP status OK (200).
     */
    @GetMapping // Handles GET requests to /api/users
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK); // 200 OK
    }

    /**
     * Retrieves a list of all active users in the system.
     * @return A ResponseEntity with a list of active users and HTTP status OK (200).
     */
    @GetMapping("/active") // Handles GET requests to /api/users/active
    public ResponseEntity<List<User>> getAllActiveUsers() {
        List<User> activeUsers = userService.getAllActiveUsers();
        return new ResponseEntity<>(activeUsers, HttpStatus.OK); // 200 OK
    }

    /**
     * Finds a user by their document type and number.
     * Example URL: GET /api/users/document/CC/123456789
     * @param type The identification document type (e.g., "CC", "TI").
     * @param number The identification document number.
     * @return A ResponseEntity with the found user and HTTP status OK (200),
     * or HTTP status NOT_FOUND (404) if no user is found.
     */
    @GetMapping("/document/{type}/{number}") // Handles GET requests with path variables
    public ResponseEntity<User> findUserByIdDocument(
            @PathVariable("type") IdDocumentType type, // Spring can convert path variable to Enum
            @PathVariable("number") String number) {

        Optional<User> user = userService.findUserByIdDocument(type, number);

        return user.map(foundUser -> new ResponseEntity<>(foundUser, HttpStatus.OK)) // 200 OK
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 404 Not Found
    }

    /**
     * Registers a new user in the system.
     * @param registrationDTO The user's registration data.
     * @return A ResponseEntity with the registered user and HTTP status CREATED (201).
     * Returns HTTP status CONFLICT (409) if a user with the same document already exists.
     * Returns HTTP status BAD_REQUEST (400) if input validation fails.
     */
    @PostMapping("/register") // Handles POST requests to /api/users/register
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        try {
            User registeredUser = userService.registerUser(registrationDTO);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED); // 201 Created
        } catch (IllegalArgumentException e) {
            // Catches the exception thrown by UserService if user already exists
            return new ResponseEntity<>(null, HttpStatus.CONFLICT); // 409 Conflict
        }
    }

    /**
     * Deactivates a user by their unique ID.
     * @param id The ID of the user to deactivate.
     * @return A ResponseEntity with HTTP status NO_CONTENT (204) if successful,
     * or HTTP status NOT_FOUND (404) if the user is not found.
     */
    @PutMapping("/{id}/deactivate") // Handles PUT requests to /api/users/{id}/deactivate
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
        try {
            userService.deactivateUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } catch (IllegalArgumentException e) {
            // Catches the exception thrown by UserService if user not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }

}
