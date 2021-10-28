package go.travels.backend.utils.security;

import go.travels.backend.document.User;
import go.travels.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = repository.findByEmail(email);

        if (u == null) {
            throw new UsernameNotFoundException(email);
        }

        return new UserSS(u.getId(), u.getEmail(), u.getPassword(), u.getProfile());
    }
}
