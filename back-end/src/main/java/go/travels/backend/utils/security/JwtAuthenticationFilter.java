package go.travels.backend.utils.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import go.travels.backend.document.User;
import go.travels.backend.dto.LoginDTO;
import go.travels.backend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JWTUtil jwtUtil;

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    public JwtAuthenticationFilter(JWTUtil jwtUtil, AuthenticationManager authenticationManager, UserRepository userRepository) {
        setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
           try {
               LoginDTO creds = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);

               UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>());

               return authenticationManager.authenticate(authToken);
           } catch (IOException e) {
               throw new RuntimeException();
           }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = ((UserSS) authResult.getPrincipal()).getUsername();
        String id = ((UserSS) authResult.getPrincipal()).getId();
        String token = jwtUtil.generateToken(username, id);
        response.setContentType("application/json");
        response.getWriter().append(json(username));
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
    }

    private String json(String email) {
        User user = userRepository.findByEmail(email);

        return "{\"name\": \"" + user.getName() + "\", "
                + "\"email\": \"" + user.getEmail() + "\", "
                + "\"id\": \"" + user.getId() + "\"}";
    }

    private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().append(json());
        }

        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                    + "\"status\": 401, "
                    + "\"error\": \"Não autorizado\", "
                    + "\"message\": \"Email ou senha inválidos\", "
                    + "\"path\": \"/login\"}";
        }
    }
}
