package co.copper.test.services;

import co.copper.test.datamodel.User;
import co.copper.test.exception.ExternalAPIException;
import co.copper.test.externalapi.UserProvider;
import co.copper.test.storage.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(TestJavaService.class);
    private final UserRepository userRepository;
    private final UserProvider userProvider;

    @Autowired
    public UserService(UserRepository userRepository, UserProvider userProvider) {
        this.userRepository = userRepository;
        this.userProvider = userProvider;
    }

    public List<User> saveUsers() {
        try {
            List<User> users = userProvider.provideUser(20);
            userRepository.saveUsers(users);
            return users;
        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            log.error(e.getMessage());
            throw new ExternalAPIException(e.getMessage());
        }
    }

    public List<User> getUsers() {
        return userRepository.getAll();
    }
}
