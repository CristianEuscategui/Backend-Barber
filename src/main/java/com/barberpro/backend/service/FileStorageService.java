package com.barberpro.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    public String guardarImagen(MultipartFile file) {
        try {
            Path directorio = Paths.get(uploadDir);
            if (!Files.exists(directorio)) {
                Files.createDirectories(directorio);
            }

            String extension = "";
            String nombreOriginal = file.getOriginalFilename();
            if (nombreOriginal != null && nombreOriginal.contains(".")) {
                extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
            }

            String nombreArchivo = UUID.randomUUID() + extension;
            Path destino = directorio.resolve(nombreArchivo);
            Files.copy(file.getInputStream(), destino);

            return "/images/" + nombreArchivo;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen: " + e.getMessage());
        }
    }
}