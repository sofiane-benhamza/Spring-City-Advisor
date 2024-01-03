package all.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String username, String email, String password) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);

        return userRepository.save(newUser);
    }

    public boolean verifyUser(String mail, String password) {
        User existingUser = userRepository.findByEmailAndPassword(mail, password);
        return existingUser != null;
    }

    public String getNameOfUser(String mail) {
        User existingUser = userRepository.findByEmail(mail);
        return (existingUser != null) ? existingUser.getUsername() : null;
    }

}