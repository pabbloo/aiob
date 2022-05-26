package pwr.ist.aiob.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pwr.ist.aiob.model.LoginRequest;
import pwr.ist.aiob.model.LoginResponse;
import pwr.ist.aiob.model.User;
import pwr.ist.aiob.repository.UserRepository;
import pwr.ist.aiob.model.CustomUserDetails;
import pwr.ist.aiob.service.CustomUserDetailsService;
import pwr.ist.aiob.service.UserService;
import pwr.ist.aiob.util.JwtUtil;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class LoginController {
    @Resource
    private UserService userService;

    @Resource
    private UserRepository userRepository;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    CustomUserDetailsService customUserDetailsService;

    @Resource
    private JwtUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
        if(!userService.is2FACodeCorrect(loginRequest.getMfaCode(), userDetails.getMfaSecret())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("2FA Failed");
        }

        String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(jwt));
    }

    @GetMapping("/users")
    ResponseEntity<List<User>> all() {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }
}
