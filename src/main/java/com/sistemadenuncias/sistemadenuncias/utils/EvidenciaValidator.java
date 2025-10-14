package com.sistemadenuncias.sistemadenuncias.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class EvidenciaValidator {

    private static final List<String> EXTENSOES_PERMITIDAS = List.of("jpg", "jpeg", "png", "mp4", "pdf");
    private static final long TAMANHO_MAX = 5 * 1024 * 1024;

    public void validar(MultipartFile file) {
        String original = file.getOriginalFilename();
        String ext = (original != null) ? FilenameUtils.getExtension(original).toLowerCase() : "";
        long tamanho = file.getSize();

        if (file.isEmpty() || original == null) {
            throw new IllegalArgumentException("Arquivo vazio ou inválido");
        }

        if (!EXTENSOES_PERMITIDAS.contains(ext)) {
            throw new IllegalArgumentException("Extensão não permitida: " + ext);
        }

        if (tamanho > TAMANHO_MAX) {
            throw new IllegalArgumentException("Arquivo excede o tamanho máximo permitido");
        }

        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new IllegalArgumentException("Arquivo vazio ou sem nome");
        }

    }
}

