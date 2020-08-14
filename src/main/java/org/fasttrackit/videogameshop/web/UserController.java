package org.fasttrackit.videogameshop.web;

import org.fasttrackit.videogameshop.domain.User;
import org.fasttrackit.videogameshop.service.UserService;
import org.fasttrackit.videogameshop.transfer.user.SaveUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/users")
@RestController
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser( @Valid @RequestBody  SaveUserRequest request) {
        final User user = userService.createUser(request);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
@GetMapping("/id")
    public ResponseEntity<User> getUser(@PathVariable long id) {
    final User user = userService.getUser(id);
    return new ResponseEntity<>(user, HttpStatus.OK);
}
}
