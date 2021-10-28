package go.travels.backend.services;

import go.travels.backend.document.User;
import go.travels.backend.repositories.UserRepository;
import go.travels.backend.utils.security.UserSS;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User persist(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Boolean exist(String id) { return userRepository.existsById(id);}

    public void addPhoto(String id, MultipartFile file) throws IOException {
        Optional<User> user = userRepository.findById(id);
        user.get().setImage(
                new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        userRepository.save(user.get());
    }

    public User getPhoto(String id) {
        return userRepository.findById(id).get();
    }

    public UserSS authenticated() {
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        catch (Exception e) {
            return null;
        }
    }
}
