package ro.cluj.products.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @JsonProperty("usernameOrEmail")
    private String usernameOrEmail;

    @JsonProperty("password")
    private String password;
}
