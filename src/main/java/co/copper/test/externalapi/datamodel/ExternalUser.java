package co.copper.test.externalapi.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalUser {

    Name name;
    Login login;
    String email;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Name {
        String first;
        String last;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Login {
        String password;
    }
}
