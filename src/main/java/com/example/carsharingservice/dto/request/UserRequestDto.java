package com.example.carsharingservice.dto.request;

import com.example.carsharingservice.lib.Email;
import com.example.carsharingservice.lib.Password;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Password(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords do not match!"
)
public class UserRequestDto {
    @Email
    private String email;
    private String firstName;
    private String lastName;
    @Size(min = 8, max = 40)
    private String password;
    private String repeatPassword;
}
