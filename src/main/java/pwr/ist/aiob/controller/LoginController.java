package pwr.ist.aiob.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pwr.ist.aiob.model.EmployeeDAO;
import pwr.ist.aiob.model.User;
import pwr.ist.aiob.repository.UserRepository;
import pwr.ist.aiob.service.UserService;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class LoginController {
    @Resource
    private UserService userService;

    @Resource
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User userAttempt,
                                      @RequestParam String code) {
        User user = validateCredentials(userAttempt);
        if(user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if(user.has2FA()){
            if(userService.is2FACodeCorrect(code, user.getMfaSecret())) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/users")
    ResponseEntity<List<User>> all() {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    public User validateCredentials(User userAttempt) {
        return userService.validateCredentials(userAttempt);
    }
}
