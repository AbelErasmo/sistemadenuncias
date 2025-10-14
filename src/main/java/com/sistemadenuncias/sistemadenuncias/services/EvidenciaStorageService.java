package com.sistemadenuncias.sistemadenuncias.services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class EvidenciaStorageService {

    private static final String PASTA_UPLOAD = "uploads/evidencias";

    public String salvar(MultipartFile file) throws Exception {
        String original = file.getOriginalFilename();
        String ext = (original != null) ? FilenameUtils.getExtension(original).toLowerCase() : "";
        String nomeFinal = "evd_" + UUID.randomUUID() + "." + ext;
        Path destino = Paths.get(PASTA_UPLOAD, nomeFinal);
        Files.createDirectories(destino.getParent());
        Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
        return nomeFinal;
    }
}

