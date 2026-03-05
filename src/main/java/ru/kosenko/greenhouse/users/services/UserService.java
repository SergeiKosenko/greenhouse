package ru.kosenko.greenhouse.users.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kosenko.greenhouse.users.dtos.UsersDto;
import ru.kosenko.greenhouse.users.entities.Role;
import ru.kosenko.greenhouse.users.entities.User;
import ru.kosenko.greenhouse.users.exceptions.InvalidParamsException;
import ru.kosenko.greenhouse.users.exceptions.ResourceNotFoundException;
import ru.kosenko.greenhouse.users.repositories.RoleRepository;
import ru.kosenko.greenhouse.users.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

//    public void updateTelegramChatId(String username, String chatId) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//        userRepository.save(user);
//    }

    public boolean isAdmin(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Page<User> searchUsers(Integer page, Integer pageSize) {
        return userRepository.findAll(PageRequest.of(page - 1, pageSize));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public Optional<User> FindById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void createNewUser(UsersDto usersDto) {
        User user = new User();
        String bcryptCachedPassword = passwordEncoder.encode(usersDto.getPassword());
        user.setFirstName(usersDto.getFirstName());
        user.setLastName(usersDto.getLastName());
        user.setUsername(usersDto.getUsername());
        user.setPassword(bcryptCachedPassword);
        user.setEmail(usersDto.getEmail());
        user.setPhone(usersDto.getPhone());
        user.setRoles(Set.of(roleRepository.findByName("ROLE_USER")));
//        user.setRegiones(regionesService.FindByTitle(usersDto.getRegionesTitle()).get());

        userRepository.save(user);
    }

    @Transactional
    public void changeRole(String roleName, Long userId) {
        if (roleName == null || userId == null) {
            throw new InvalidParamsException("Невалидные параметры");
        }
        Long roleId;
        try {
            roleId = roleRepository.findByName(roleName).getId();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Ошибка поиска роли. Роль " + roleName + "не существует");
        }
        try {
            userRepository.changeRole(roleId, userId);
            userRepository.changeUpdateAt(userId);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Ошибка изменения роли. Пользователь " + userId + "не существует");
        }
    }

    @Transactional
    public void changeActive(String active, Long userId) {
        if (active == null || userId == null) {
            throw new InvalidParamsException("Невалидные параметры");
        }
        try {
            if (active.equals("true")) {userRepository.changeActive(true, userId);}
            if (active.equals("false")) {userRepository.changeActive(false, userId);}
            userRepository.changeUpdateAt(userId);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Ошибка активации. Пользователь " + userId + "не существует");
        }
    }

    public void deleteById(Long id) {
        if (id == null) {
            throw new InvalidParamsException("Невалидный параметр идентификатор:" + null);
        }
        try {
            userRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Ошибка удаления пользователя. Пользователь " + id + "не существует");
        }
    }

}