package go.travels.backend.archive;

import go.travels.backend.document.Trip;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeArquivo {
    public static List<Trip> leArquivo(String nomeArq) {
        BufferedReader entrada = null;
        List<Trip> lista = new ArrayList();
        String header, trailer, tipoRegistro, registro;
        String id, latMatch, lngMatch, latDestiny, lngDestiny, destiny, idUser;
        int contRegistro = 0;

        try {
            entrada = new BufferedReader(new FileReader(nomeArq));
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

    try {

            registro = entrada.readLine();

            header = registro.substring(0,49);
            System.out.println(header);
            registro = entrada.readLine();

        while (registro != null) {
                tipoRegistro = registro.substring(0, 30);

                if(tipoRegistro.equals("Fim do documento de importação")){
                    trailer = registro.substring(0,30);
                    System.out.println(trailer);
                }
                else {
                id = registro.substring(0, 24);
                latMatch = registro.substring(25, 31);
                lngMatch = registro.substring(32, 39);
                latDestiny = registro.substring(40, 46);
                lngDestiny = registro.substring(47, 54);
                destiny = registro.substring(55, 90);
                idUser = registro.substring(91, 115);

                Trip trip = new Trip(id,latMatch, lngMatch, latDestiny, lngDestiny, destiny);
                trip.setId(id);
                lista.add(trip);

                System.out.printf("%-2s %-10s %-13s %-13s %-13s %-13s %-13s\n", id, latMatch, lngMatch, latDestiny, lngDestiny, destiny, idUser);

                contRegistro++;
            }
            registro = entrada.readLine();
            }

            entrada.close();

        } catch (IOException e) {
            System.err.printf("Erro ao ler arquivo: %s.\n", e.getMessage());
    }
        return lista;
 }
}

