package birintsev.artplace.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = UserNotExistsValidator.class)
public @interface UserNotExists {

    String message()
        default "{birintsev.artplace.validation.UserNotExists.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
