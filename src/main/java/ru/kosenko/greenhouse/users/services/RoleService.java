package ru.kosenko.greenhouse.users.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kosenko.greenhouse.users.exceptions.ResourceNotFoundException;
import ru.kosenko.greenhouse.users.repositories.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Long getUserRole() {
        return roleRepository.findByName("ROLE_USER").getId();
    }

    public List<String> findAllRoleNames() {
        try {
            return roleRepository.findAllNames();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Роли не найдены");
        }
    }
}