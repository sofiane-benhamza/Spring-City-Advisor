package all.users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create")
    @CrossOrigin("*")
        public ResponseEntity<User> createUser(@RequestParam String name, @RequestParam String mail,
            @RequestParam String password) {
        User createdUser = userService.createUser(name, mail, password);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/verify")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
        public boolean verifyUser(@RequestParam String mail, @RequestParam String password) {
        boolean userExists = userService.verifyUser(mail, password);
        return userExists;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/getname")
        public String getNameOfUserUser(@RequestParam String mail) {
        String username = userService.getNameOfUser(mail);
        return username;
    }


    @GetMapping("/connected")
    @CrossOrigin("*")
        public boolean doSomething() {
        return true;
    }
}
