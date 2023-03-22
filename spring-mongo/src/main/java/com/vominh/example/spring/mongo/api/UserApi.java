package com.vominh.example.spring.mongo.api;

import com.vominh.example.spring.mongo.config.security.AuthenticationFilter;
import com.vominh.example.spring.mongo.data.model.UserModel;
import com.vominh.example.spring.mongo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
public class UserApi extends GenericApi<UserModel> {

    private final UserService service;

    public UserApi(UserService service) {
        super(service);
        this.service = service;
    }

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> signUp(@RequestBody UserModel user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.signup(user));
    }

    @PostMapping(value = "/signin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity authenticate(@RequestParam("user") String user, @RequestParam("password") String password, HttpServletResponse response) {
        var token = service.signIn(user, password);

        var cookie = new Cookie(AuthenticationFilter.TOKEN_NAME, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("success");
    }

    @GetMapping(value = "/{id}/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> info(@PathVariable("id") Integer userId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getInfo(userId));
    }
}
