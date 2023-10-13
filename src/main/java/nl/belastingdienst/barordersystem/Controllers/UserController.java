package nl.belastingdienst.barordersystem.Controllers;


import nl.belastingdienst.barordersystem.Dto.UserDto;
import nl.belastingdienst.barordersystem.Dto.UserGetDto;
import nl.belastingdienst.barordersystem.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<UserGetDto>> getUsers() {

        List<UserGetDto> userDtos = userService.getUsers();

        return ResponseEntity.ok().body(userDtos);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserGetDto> getUser(@PathVariable String username) {

        UserGetDto optionalUser = userService.getSingleUser(username);


        return ResponseEntity.ok().body(optionalUser);

    }

    @PostMapping(value = "")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {
        String newUsername = userService.createUser(dto);
        userService.addAuthority(newUsername, "ROLE_CUSTOMER");


        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/staff")
    public ResponseEntity<UserDto> createBarkeeper(@RequestBody UserDto dto) {
        String newUsername = userService.createUser(dto);
        userService.addAuthority(newUsername, "ROLE_STAFF");
        userService.addAuthority(newUsername, "ROLE_CUSTOMER");


        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(newUsername).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String username, @RequestBody UserDto dto) {

        userService.updateUser(username, dto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable String username, @PathVariable String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }

}