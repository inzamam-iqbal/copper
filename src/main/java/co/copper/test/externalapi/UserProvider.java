package co.copper.test.externalapi;

import co.copper.test.datamodel.User;
import co.copper.test.externalapi.datamodel.ExternalUser;
import co.copper.test.externalapi.response.ExternalUsers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import com.typesafe.config.Config;
import org.asynchttpclient.AsyncHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class UserProvider {

    private final Config config;
    private final AsyncHttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserProvider(Config config, AsyncHttpClient httpClient, ObjectMapper objectMapper){
        this.config = config;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public List<User> provideUser(int numberOfUsers) throws ExecutionException, InterruptedException, JsonProcessingException {
        String baseUrl = config.getConfig("external.api").getString("url");
        String url = String.format("%s?results=%d", baseUrl, numberOfUsers);

        String response = httpClient.prepareGet(url).execute().toCompletableFuture()
                .handle((res, t) -> res.getResponseBody()).get();

        ExternalUsers externalUsers = objectMapper.readValue(response, ExternalUsers.class);

        return externalUsers.getResults().stream().map(this::externalUserToUser).collect(Collectors.toList());
    }

    private User externalUserToUser(ExternalUser externalUser) {
        return User.builder()
                .id(UUID.randomUUID())
                .firstName(externalUser.getName().getFirst())
                .lastName(externalUser.getName().getLast())
                .email(externalUser.getEmail())
                .password(getHashedPassword(externalUser.getLogin().getPassword()))
                .build();
    }

    private String getHashedPassword(String password) {
        return Hashing.sha512().hashString(password, StandardCharsets.UTF_8).toString();
    }
}
