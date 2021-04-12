package birintsev.artplace.dto;

import birintsev.artplace.validation.UserNotExists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Represents a user registration request
 *
 * @see birintsev.artplace.controllers.RegistrationController
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@UserNotExists(
    message = "The user with such email already exist. Please, try another address"
)
public class RegistrationRequest {

    @Length(
        min = 2,
        max = 128,
        message =
            "Your name should be of 2...128 characters in length"
    )
    private String name;

    @Pattern(
        regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
        message = "Oops, the email looks misspelled. Please, try another one"
    )
    @Length(
        max = 128,
        message = "Your email is too long. Please try another one"
    )
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String gender;

    @NotBlank
    @Length(
        min = 8,
        max = 128,
        message = "The password does not satisfy the requirements of length"
            + " (8...128 characters)"
    )
    private String password;
}
