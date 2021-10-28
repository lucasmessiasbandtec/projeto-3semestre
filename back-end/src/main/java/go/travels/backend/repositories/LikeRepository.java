package go.travels.backend.repositories;

import go.travels.backend.document.Like;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends MongoRepository<Like, String> {
    Boolean existsByUserIdAndPostId(String userId, String postId);
    void deleteByUserId(String userId);
}
