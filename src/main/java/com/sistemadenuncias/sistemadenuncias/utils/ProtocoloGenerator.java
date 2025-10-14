package com.sistemadenuncias.sistemadenuncias.utils;

import com.sistemadenuncias.sistemadenuncias.models.Provincias;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProtocoloGenerator {

    public static String gerarProtocoloPorProvincia(Provincias provincia) {
        String prefixo = "DNC";
        String sigla = siglaProvincia(provincia);
        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String codigoHex = gerarHexAleatorio(6);

        return prefixo + "-" + sigla + "-" + data + "-" + codigoHex;
    }

    private static String gerarHexAleatorio(int tamanho) {
        SecureRandom random = new SecureRandom();
        StringBuilder hex = new StringBuilder();

        for (int i = 0; i < tamanho; i++) {
            int valor = random.nextInt(16);
            hex.append(Integer.toHexString(valor).toUpperCase());
        }

        return hex.toString();
    }

    private static String siglaProvincia(Provincias provincia) {
        String nome = provincia.getNome().toLowerCase().replace(" ", "_");

        return switch (nome) {
            case "gaza" -> "GA";
            case "cidade_de_maputo" -> "CM";
            case "maputo_provincia" -> "MP";
            case "inhambane" -> "IN";
            case "sofala" -> "SF";
            case "manica" -> "MA";
            case "tete" -> "TE";
            case "zambezia" -> "ZA";
            case "nampula" -> "NA";
            case "niassa" -> "NI";
            case "cabo_delgado" -> "CD";
            default -> "XX";
        };
    }
}

