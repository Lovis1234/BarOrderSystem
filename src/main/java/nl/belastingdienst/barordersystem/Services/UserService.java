package nl.belastingdienst.barordersystem.Services;


import nl.belastingdienst.barordersystem.Dto.AuthorityRequestDto;
import nl.belastingdienst.barordersystem.Dto.UserDto;
import nl.belastingdienst.barordersystem.Dto.UserGetDto;
import nl.belastingdienst.barordersystem.Exceptions.RecordNotFoundException;
import nl.belastingdienst.barordersystem.Models.Authority;
import nl.belastingdienst.barordersystem.Models.User;
import nl.belastingdienst.barordersystem.Repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private UserRepository userRepository;


    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserGetDto> getUsers() {
        List<UserGetDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(toUserGetDto(user));
        }
        return collection;
    }

    public UserDto getUser(String username) {
        UserDto dto = new UserDto();
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()){
            dto = fromUser(user.get());
        }else {
            throw new UsernameNotFoundException(username);
        }
        return dto;
    }

    public UserGetDto getSingleUser(String username) {
        UserGetDto dto = new UserGetDto();
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()){
            dto = toUserGetDto(user.get());
        }else {
            throw new UsernameNotFoundException(username);
        }
        return dto;
    }

    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    public String createUser(UserDto userDto) {
        UserDto newuserDto = userDto;
        User newUser = userRepository.save(toUser(newuserDto));


        return newUser.getUsername();
    }

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    public void updateUser(String username, UserDto newUser) {
        if (!userRepository.existsById(username)) throw new RecordNotFoundException("User not found");
        User user = userRepository.findById(username).get();
        user.setPassword(newUser.getPassword());
        userRepository.save(user);
    }

    public Set<Authority> getAuthorities(String username) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        UserDto userDto = fromUser(user);
        return userDto.getAuthorities();
    }

    public void addAuthority(String username, String authority) {

        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

    public void removeAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

    public static UserDto fromUser(User user){

        var dto = new UserDto();

        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.authorities = user.getAuthorities();

        return dto;
    }

    public User toUser(UserDto userDto) {

        var user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAuthorities(userDto.getAuthorities());

        return user;
    }

    public UserGetDto toUserGetDto(User userDto) {

        var user = new UserGetDto();

        user.setUsername(userDto.getUsername());
        user.setAuthorities(toAuthorityRequestDtoList(userDto.getAuthorities()));

        return user;

    }
    private Set<AuthorityRequestDto> toAuthorityRequestDtoList(Set<Authority> authorities){
        Set<AuthorityRequestDto> authorityRequestDtos = new HashSet<>();
        for(Authority authority : authorities){
            AuthorityRequestDto authorityRequestDto = new AuthorityRequestDto(authority.getAuthority());
            authorityRequestDtos.add(authorityRequestDto);
        }
        return authorityRequestDtos;
    }

}
