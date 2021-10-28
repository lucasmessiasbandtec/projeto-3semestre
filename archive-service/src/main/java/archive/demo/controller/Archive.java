package archive.demo.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.FormatterClosedException;

public class Archive {

    public static void gravaNaListaCSV(ListaObj<Trip> listaObj) {
        FileWriter arquivo = null;
        Formatter saida = null;
        String nomeDoArquivo = "trip.csv";
        boolean crashed = false;

            try {
                arquivo = new FileWriter(nomeDoArquivo, true);
                saida = new Formatter(arquivo);
            } catch (IOException erro) {
                System.err.println("Erro ao abrir arquivo");
                System.exit(1);
            }

        try {
            for (int i = 0; i < listaObj.getTamanho(); i++) {
                Trip trip = listaObj.getElemento(i);
                saida.format("%-25s; %-25s; %-25s; %-25s; %-25s;\n",

                        trip.getId(),
                        trip.getLatMatch(),
                        trip.getLngMatch(),
                        trip.getLatDestiny(),
                        trip.getLngDestiny());
            }
        } catch (FormatterClosedException erro) {
            System.err.println("Erro ao gravar no arquivo");
            crashed = true;
        } finally {
            saida.close();
            try {
                arquivo.close();
            } catch (IOException erro) {
                System.err.println("Erro ao fechar arquivo.");
                crashed = true;
            }
            if (crashed)
                System.exit(1);
        }
    }

    public static void gravaNaListaTXT(ListaObj<Trip> listaObj) {
        FileWriter arquivo = null;
        Formatter saida = null;
        String nomeDoArquivo = "trip.txt";
        boolean crashed = false;

        try {
            arquivo = new FileWriter(nomeDoArquivo, true);
            saida = new Formatter(arquivo);
        } catch (IOException erro) {
            System.err.println("Erro ao abrir arquivo");
            System.exit(1);
        }

        try {
            for (int i = 0; i < listaObj.getTamanho(); i++) {
                Trip trip = listaObj.getElemento(i);
                saida.format("%-25s %-25s %-25s %-25s %-25s \n",

                        trip.getId(),
                        trip.getLatMatch(),
                        trip.getLngMatch(),
                        trip.getLatDestiny(),
                        trip.getLngDestiny());
            }
        } catch (FormatterClosedException erro) {
            System.err.println("Erro ao gravar no arquivo");
            crashed = true;
        } finally {
            saida.close();
            try {
                arquivo.close();
            } catch (IOException erro) {
                System.err.println("Erro ao fechar arquivo.");
                crashed = true;
            }
            if (crashed)
                System.exit(1);
        }
    }


    public static void gravaRegistroTXT (String registro) {
        String nomeDoArquivo = "trip.txt";
        BufferedWriter saida = null;
        try {
            saida = new BufferedWriter(new FileWriter(nomeDoArquivo, true));
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

        try {
            saida.append(registro + "\n");
            saida.close();

        } catch (IOException e) {
            System.err.printf("Erro ao gravar arquivo: %s.\n", e.getMessage());
        }

    }
}
