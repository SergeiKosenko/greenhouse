package ru.kosenko.greenhouse.users.converters;

import org.springframework.stereotype.Component;
import ru.kosenko.greenhouse.users.dtos.UsersDto;
import ru.kosenko.greenhouse.users.entities.User;

@Component
public class UserConverter {
    public UsersDto entityToDto(User p) {
        UsersDto usersDto = new UsersDto();

        usersDto.setId(p.getId());
        usersDto.setUsername(p.getUsername());
        usersDto.setFirstName(p.getFirstName());
        usersDto.setLastName(p.getLastName());
        usersDto.setPhone(p.getPhone());
        usersDto.setEmail(p.getEmail());
        usersDto.setActive(p.isActive());
        usersDto.setPassword(p.getPassword());
        usersDto.setRole(p.getRoles());
        return usersDto;
    }
}