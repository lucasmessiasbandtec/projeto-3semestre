package archive.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/archive")
public class ArchiveController {

    @Autowired
    TripService tripService;

    @GetMapping("/csv/{idUser}")
    public HttpEntity<byte[]> csv(@PathVariable String idUser) throws IOException {

        List<Trip> o = tripService.findAllByIdUser(idUser);
        ListaObj<Trip> listaObj = new ListaObj<>(o.size());
        for(Trip t : o){
            listaObj.adiciona(t);
        }

        Archive.gravaNaListaCSV(listaObj);

        byte[] arquivo = Files.readAllBytes( Paths.get("trip.csv") );

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Disposition", "attachment;filename=\"trip.csv\"");

        HttpEntity<byte[]> entity = new HttpEntity<byte[]>( arquivo, httpHeaders);

        return entity;
    }

    @GetMapping("/txt/{idUser}")
    public HttpEntity<byte[]> txt(@PathVariable String idUser) throws IOException {

        List<Trip> o = tripService.findAllByIdUser(idUser);
        ListaObj<Trip> listaObj = new ListaObj<>(o.size());
        for(Trip t : o){
            listaObj.adiciona(t);
        }
        String header = "Exportação de dados via arquivo de texto: Descrição das viagens do usuário\n" +
                "ID do usuário: " + idUser + "\n";


        String cabecalho = String.format("\n%-25s %-25s %-25s %-25s %-25s \n", "ID VIAGEM", "LAT PARTIDA", "LNG PARTIDA" , "LAT DESTINO", "LNG DESTINO");
        String trailer = String.format("%-25s \n", "FIM DO DOCUMENTO");

        Archive.gravaRegistroTXT(header);
        Archive.gravaRegistroTXT(cabecalho);
        Archive.gravaNaListaTXT(listaObj);
        Archive.gravaRegistroTXT(trailer);


        byte[] arquivo = Files.readAllBytes( Paths.get("trip.txt") );

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Content-Disposition", "attachment;filename=\"trip.txt\"");

        HttpEntity<byte[]> entity = new HttpEntity<byte[]>( arquivo, httpHeaders);

        return entity;
    }
}
