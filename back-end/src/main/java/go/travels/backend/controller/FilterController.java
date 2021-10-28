package go.travels.backend.controller;

import go.travels.backend.document.Filter;
import go.travels.backend.dto.FilterDTO;
import go.travels.backend.services.FilterService;
import go.travels.backend.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filter")
public class FilterController {

    @Autowired
    FilterService filterService;

    @Autowired
    TripService tripService;

    @PostMapping("/{tripId}")
    public ResponseEntity<FilterDTO> register(@RequestBody FilterDTO filterDTO, @PathVariable String tripId){
        if (tripService.exist(tripId)) {
            Filter filter = convertDtoForFilter(filterDTO);
            filterService.persist(filter);

            return ResponseEntity.ok().body(convertFilterForDto(filter));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<FilterDTO>> findAll(@PathVariable String id){
            if (tripService.exist(id)){
                List<FilterDTO> list = filterService.findAllByTripId(id);
                return ResponseEntity.ok().body(list);
            } else {
                return ResponseEntity.notFound().build();
            }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        if (filterService.exist(id)){
            filterService.delete(id);
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private Filter convertDtoForFilter(FilterDTO filterDTO){
        return new Filter(
                filterDTO.getLocalName(),
                filterDTO.getLatitude(),
                filterDTO.getLongitude(),
                filterDTO.getTripId(),
                filterDTO.getUrl()
        );
    }

    private FilterDTO convertFilterForDto(Filter filter){
        return new FilterDTO(
                filter.getLocalName(),
                filter.getLatitude(),
                filter.getLongitude(),
                filter.getTripId(),
                filter.getId(),
                filter.getUrl()
        );
    }
}
