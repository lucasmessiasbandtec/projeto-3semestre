package go.travels.backend.services;

import go.travels.backend.document.Trip;
import go.travels.backend.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    @Autowired
    TripRepository tripRepository;

    public Trip persist(Trip trip) { return  tripRepository.save(trip); }

    public Page<Trip> findByUserId(String userId, PageRequest pageRequest) { return tripRepository.findByIdUser(userId, pageRequest); }

    public Boolean exist(String id) { return tripRepository.existsById(id);}

    public void delete(String id) { tripRepository.deleteById(id);}

    public Optional<Trip> findById(String id) { return tripRepository.findById(id); }

    public List<Trip> findAllByIdUser(String idUser){
        return tripRepository.findAllByIdUser(idUser);
    }

}
