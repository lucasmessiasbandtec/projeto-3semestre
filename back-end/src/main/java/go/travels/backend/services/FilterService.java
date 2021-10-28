package go.travels.backend.services;

import go.travels.backend.document.Filter;
import go.travels.backend.dto.FilterDTO;
import go.travels.backend.repositories.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilterService {

    @Autowired
    FilterRepository filterRepository;

    public List<FilterDTO> findAllByTripId(String id){
        List<Filter> list = filterRepository.findAllByTripId(id);
        return list.stream().map(filter -> new FilterDTO(
                filter.getLocalName(),
                filter.getLatitude(),
                filter.getLongitude(),
                filter.getTripId(),
                filter.getUrl(),
                filter.getId())).collect(Collectors.toList());
    }

    public Boolean exist(String id) { return filterRepository.existsById(id);}

    public Filter persist(Filter filter){
        return filterRepository.save(filter);
    }

    public void delete(String id) { filterRepository.deleteById(id);}
}
