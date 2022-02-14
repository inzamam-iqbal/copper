package co.copper.test.externalapi.response;

import co.copper.test.externalapi.datamodel.ExternalUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ExternalUsers {
    List<ExternalUser> results;
}
