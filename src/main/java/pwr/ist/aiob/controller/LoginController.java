package pwr.ist.aiob.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pwr.ist.aiob.model.User;
import pwr.ist.aiob.service.UserService;

import javax.annotation.Resource;

@RestController
public class LoginController {
    @Resource
    private UserService userService;

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

    public User validateCredentials(User userAttempt) {
        return userService.validateCredentials(userAttempt);
    }
}
