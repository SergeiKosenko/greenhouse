package ru.kosenko.greenhouse.users.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kosenko.greenhouse.users.dtos.UsersDto;
import ru.kosenko.greenhouse.users.services.UserService;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
//@CrossOrigin("*")
public class RegisterController {
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewUser(@RequestBody UsersDto usersDto) {
        userService.createNewUser(usersDto);
    }
}
