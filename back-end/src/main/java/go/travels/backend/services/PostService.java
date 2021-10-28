package go.travels.backend.services;

import go.travels.backend.document.Post;
import go.travels.backend.dto.FilterDTO;
import go.travels.backend.dto.PostDTO;
import go.travels.backend.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public Post persist(Post post) { return  postRepository.save(post); }

    public Page<Post> findAll(PageRequest pageRequest) { return postRepository.findAll(pageRequest); }

    public Optional<Post> findById(String id) { return postRepository.findById(id);}

    public Boolean exist(String id) { return postRepository.existsById(id);}
}
