package com.ravenprojects.uchuva_bank.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserRegistrationDTO {
    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres.")
    private String firstName;

    @NotBlank(message = "El apellido no puede estar vacío.")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres.")
    private String lastName;

    @NotNull(message = "El tipo de documento no puede ser nulo.")
    private String idDocumentType;

    @NotBlank(message = "El número de documento no puede estar vacío.")
    private String idDocumentNumber;

    @Email(message = "El formato del correo electrónico no es válido.")
    @NotBlank(message = "El correo electrónico no puede estar vacío.")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía.")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
    private String password;

    @NotBlank(message = "El teléfono no puede estar vacío.")
    @Size(min = 10, max = 10, message = "El teléfono debe tener 10 dígitos.")
    private String phone;

    @NotBlank(message = "La dirección no puede estar vacía.")
    private String address;
}
