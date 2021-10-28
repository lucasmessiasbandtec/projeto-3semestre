package go.travels.backend.controller;

import go.travels.backend.document.User;
import go.travels.backend.dto.UserDTO;
import go.travels.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private BCryptPasswordEncoder bc;

    @PostMapping("/register")
    public ResponseEntity<Optional<UserDTO>> register(@RequestBody UserDTO userDTO) {

        if (validateExistingData(userDTO)) {
            User user = convertDtoForUser(userDTO);
            userService.persist(user);

            return ResponseEntity.created(null).body(Optional.of(convertUserForDto(user)));
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PostMapping("/photos/add/{id}")
    public ResponseEntity<Object> addPhoto(@PathVariable String id, MultipartFile image) throws IOException {
        userService.addPhoto(id, image);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/photos/{id}")
    public String getPhoto(@PathVariable String id) {
        User photo = userService.getPhoto(id);
        return Base64.getEncoder().encodeToString(photo.getImage().getData());
    }

    private Boolean validateExistingData(UserDTO userDTO){
        User user = userService.findByEmail(userDTO.getEmail());
        return user == null;
    }

    private User convertDtoForUser(UserDTO userDTO) {
        return new User(userDTO.getName(), userDTO.getEmail(), bc.encode(userDTO.getPassword()));
    }

    private UserDTO convertUserForDto(User user) {
        return new UserDTO(user.getName(), user.getEmail(), "", user.getId());
    }
}
