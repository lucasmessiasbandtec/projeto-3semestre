package go.travels.backend.controller;

import go.travels.backend.archive.LeArquivo;
import go.travels.backend.document.Trip;
import go.travels.backend.dto.TripDTO;
import go.travels.backend.list.FilaObj;
import go.travels.backend.list.PilhaObj;
import go.travels.backend.services.TripService;
import go.travels.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientCodecCustomizer;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trip")
public class TripController {

    private Integer qtdPorPagina = 15;

    @Autowired
    TripService tripService;

    @Autowired
    UserService userService;

    PilhaObj pilha = new PilhaObj(10);

    FilaObj fila = new FilaObj(10);

    @PostMapping("/{userId}")
    public ResponseEntity<TripDTO> register(@RequestBody TripDTO tripDTO, @PathVariable String userId) {
        if (userService.exist(tripDTO.getIdUser())) {
            Trip trip = convertDtoForTrip(tripDTO);
            tripService.persist(trip);

            return ResponseEntity.created(null).body(convertTripForDto(trip));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/place_image/{photoreference}/{width}/{height}")
    public ResponseEntity getPlaceImage(
            @PathVariable String photoreference,
            @PathVariable String width,
            @PathVariable String height) {
        final String base_uri = "https://maps.googleapis.com/maps/api/place/photo?";
        final String uri = "https://maps.googleapis.com/maps/api/place/photo?key=AIzaSyBw46FEvXL1fBBgw8bocxI-fYTcva5yTeQ&photoreference=" + photoreference + "&maxwidth=" + width + "&maxheight= " + height;

        WebClient webClient = WebClient.create(uri);

        Mono<String> result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                .build())
                .retrieve()
                .bodyToMono(String.class);

        String[] splitted = result.block().split("<");

        for (int x = 0; x < splitted.length; x++) {

            if (splitted[x].startsWith("A HREF")) {
                String[] anotherSplitted = splitted[x].split("HREF=\"");
                String[] oneMore = anotherSplitted[1].split("\">here");
                return ResponseEntity.ok(oneMore[0]);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/place_location/{lat}/{lng}/{radius}/{filterSelected}")
    public ResponseEntity getPlaceLocation(@PathVariable String lat, @PathVariable String lng, @PathVariable String radius, @PathVariable String filterSelected) {
        final String uri = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + lng + "&radius=" + radius + "&language=pt-BR&type=" + filterSelected + "&key=AIzaSyBw46FEvXL1fBBgw8bocxI-fYTcva5yTeQ";

        WebClient webClient = WebClient.create(uri);

        Mono<String> result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .build())
                .retrieve()
                .bodyToMono(String.class);

        return ResponseEntity.ok(result.block());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Page<TripDTO>> findAll(
            @PathVariable String userId,
            @RequestParam(value = "pag", defaultValue = "0") Integer pag,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "dir", defaultValue = "DESC") String dir) {

        if (userService.exist(userId)) {
            PageRequest pageRequest = PageRequest.of(pag, qtdPorPagina, Sort.Direction.valueOf(dir), ord);
            Page<Trip> trip = tripService.findByUserId(userId, pageRequest);

            Page<TripDTO> tripDTO = trip.map(this::convertTripForDto);

            return ResponseEntity.ok(tripDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/id/{tripId}")
    public ResponseEntity<Optional<Trip>> findById(@PathVariable String tripId) {
        if (tripService.exist(tripId)) {
            Optional<Trip> trip = tripService.findById(tripId);

            return ResponseEntity.ok().body(trip);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        if (tripService.exist(id)) {
            tripService.delete(id);
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/undo")
    public ResponseEntity undo(){
        if(pilha.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else{
            Trip t = (Trip)pilha.pop();
            tripService.delete(t.getId());
            return ResponseEntity.ok().build();
        }
    }

    @Scheduled(fixedRate = 10000)
    public void schedullarInsert(){
        if (fila.isEmpty()) {
            System.out.println("Não há nenhuma requisição na fila!");

        } else {
            tripService.persist((Trip) fila.poll());
            System.out.println("Sua requisição foi registrada");
        }
    }

    @PostMapping("/upload")
    public ResponseEntity enviar(@RequestParam("arquivo") MultipartFile arquivo) throws IOException {

        if (arquivo.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo não enviado!");
        }

        System.out.println("Recebendo um arquivo do tipo: " + arquivo.getContentType());
        byte[] conteudo = arquivo.getBytes();

        Path path = Paths.get(arquivo.getOriginalFilename());
        Files.write(path, conteudo);

        String nomeArq = "a.txt";

        List<Trip> tripAdd = LeArquivo.leArquivo(nomeArq);

        for(Trip t : tripAdd){
            pilha.push(t);
            fila.insert(t);
        }


        return ResponseEntity.created(null).build();
    }

    private Trip convertDtoForTrip(TripDTO tripDTO) {
        return new Trip(
                tripDTO.getLatMatch(),
                tripDTO.getLngMatch(),
                tripDTO.getLatDestiny(),
                tripDTO.getLngDestiny(),
                tripDTO.getDestiny(),
                tripDTO.getIdUser()
        );
    }

    private TripDTO convertTripForDto(Trip trip) {
        return new TripDTO(
                trip.getId(),
                trip.getLatMatch(),
                trip.getLngMatch(),
                trip.getLatDestiny(),
                trip.getLngDestiny(),
                trip.getDestiny(),
                trip.getIdUser()
        );
    }
}
