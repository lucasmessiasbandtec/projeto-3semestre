package go.travels.backend.repositories;

import go.travels.backend.document.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends MongoRepository<Trip, String> {
    Page<Trip> findByIdUser(String userId, Pageable pageable);
    List<Trip> findAllByIdUser(String idUser);
}
