package ru.kosenko.userservice.controllers;

import org.springframework.web.bind.annotation.*;
import ru.kosenko.userservice.converters.UserConverter;
import ru.kosenko.userservice.dtos.UsersDto;
import ru.kosenko.userservice.exceptions.ResourceNotFoundException;
import ru.kosenko.userservice.services.RoleService;
import ru.kosenko.userservice.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
//@CrossOrigin("*")
public class UserController {
    private final UserService userService;
    private final UserConverter userConverter;
    private final RoleService roleService;

    public UserController(UserService userService, UserConverter userConverter, RoleService roleService) {
        this.userService = userService;
        this.userConverter = userConverter;
        this.roleService = roleService;
    }

    @GetMapping()
    public List<UsersDto> getAllUsers() {
        return userService.findAll().stream().map(userConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UsersDto getUsersById(@PathVariable Long id) {
        return userConverter.entityToDto(userService.FindById(id).orElseThrow(() -> new ResourceNotFoundException("Пользователь с ID: " + id + " не найден")));
    }

//    @GetMapping("/regionid/{username}")
//    public UsersDto getUsersByRegionId(@PathVariable String username) {
//        User user = userService.findByUsername(username)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//        return userConverter.entityToDto(user);
//    }

    @GetMapping("/roles")
    public List<String> getAllRoleNames() {
        return roleService.findAllRoleNames();
    }

    @PatchMapping("/roles/{id}")
    public void changeRole(@RequestParam String roleName, @PathVariable Long id) {
        userService.changeRole(roleName, id);
    }

    @PatchMapping("/active/{id}")
    public void changeActive(@RequestParam String active, @PathVariable Long id) {
        userService.changeActive(active, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }

//    @PatchMapping("/{username}/telegram")
//    public ResponseEntity<?> updateTelegramChatId(
//            @PathVariable String username,
//            @RequestBody Map<String, String> request,
//            Principal principal) {
//
//        if (!principal.getName().equals(username) && !userService.isAdmin(principal.getName())) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//
////        userService.updateTelegramChatId(username, request.get("telegramChatId"));
//        return ResponseEntity.ok().build();
//    }
}

