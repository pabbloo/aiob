package pwr.ist.aiob.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pwr.ist.aiob.model.SignupResponse;
import pwr.ist.aiob.model.User;
import pwr.ist.aiob.service.UserService;

import javax.annotation.Resource;

@RestController
public class SignupController {

    @Resource
    private UserService userService;

    @PostMapping("/signup")
    public SignupResponse signUp(@RequestBody User newUser) {
        if(userService.ifUsernameUnique(newUser.getUsername())) {
            String secret = userService.registerWithSecret(newUser);
            return new SignupResponse(SignupResponse.Status.OK, newUser.getUsername(), secret);
        }
        return new SignupResponse(SignupResponse.Status.USERNAME_TAKEN, newUser.getUsername());
    }
}

