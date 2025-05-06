package com.tp3.tp3.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Exception;

import org.springframework.core.io.Resource;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.core.io.InputStreamResource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.tp3.tp3.controller.DocumentController;
import com.tp3.tp3.model.Document;
import com.tp3.tp3.repository.DocumentRepository;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityNotFoundException;

@Service
@RequestMapping
public class ImpDocumentService implements IDocumentSerivce {

    private final DocumentRepository repository;
    private final String STORAGE_DIRECTORY = "C:\\Users\\oussa\\Desktop\\Data\\SoftwareEngineering\\Courses\\GI4 S2\\Spring\\TP\\Fast Cloud\\backend\\src\\main\\resources\\uploads";
    private final Logger log = Logger.getLogger(DocumentController.class.getName());

    public ImpDocumentService(DocumentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Document uploadDocument(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new NullPointerException("File is Empty");
        }
        Document document = new Document();
        Path targetDestination = Paths.get(STORAGE_DIRECTORY).resolve(file.getOriginalFilename()).normalize()
                .toAbsolutePath();

        if (!Objects.equals(targetDestination.getParent().toString(), STORAGE_DIRECTORY)) {
            throw new SecurityException("Unsupported file name");
        }
        document.setFileName(file.getOriginalFilename());
        document.setFileType(file.getContentType());
        document.setFileSize(file.getSize());
        document.setFilePath(targetDestination.toString());
        log.log(Level.SEVERE, document.getFileName());
        Files.copy(file.getInputStream(), targetDestination, StandardCopyOption.REPLACE_EXISTING);
        repository.save(document);

        return document;

    }

    @Override
    public List<Document> getAllDocuments() {
        return repository.findAll();
    }

    @Override
    public Document downloadDocument(Long id) {
        Document document = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Document with id [%d] was not found!", id)));
        // Path fileToDownload =
        // Paths.get(STORAGE_DIRECTORY).resolve(document.getFileName()).normalize().toAbsolutePath();
        return document;

    }

    @Override
    public void deleteDocument(Long id) throws IOException {
        Document document = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Document with id [%d] was not found!", id)));
        repository.delete(document);
        try {
            Files.delete(Paths.get(document.getFilePath()).toAbsolutePath());
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public List<Document> searchDocuments(String fileName) {
        return repository.searchDocuments(fileName);
    }

    @PreDestroy
    public void destroy() throws IOException {
        List<Document> listDocuments = repository.findAll();
        for (Document document : listDocuments) {
            Files.delete(Paths.get(document.getFilePath()).normalize().toAbsolutePath());
        }
    }

}
