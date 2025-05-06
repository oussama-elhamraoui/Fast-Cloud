package com.tp3.tp3.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tp3.tp3.model.Document;
import com.tp3.tp3.service.ImpDocumentService;

import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping
@CrossOrigin
public class DocumentController {

    private final ImpDocumentService service;
    private static final Logger log = Logger.getLogger(DocumentController.class.getName());

    public DocumentController(ImpDocumentService service) {
        this.service = service;
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<Document> uploadFile(@RequestParam MultipartFile file) throws Exception {
        try {
            Document document = service.uploadDocument(file);
            return new ResponseEntity<>(document, HttpStatus.OK);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Error while uploading file: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/get-documents")
    public ResponseEntity<List<Document>> getMethodName() {
        try {
            return new ResponseEntity<>(service.getAllDocuments(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/download/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) throws Exception {
        try {
            Document document = service.downloadDocument(id);
            return ResponseEntity.ok().contentLength(document.getFileSize())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFileName() + "\"")
                    .body(new FileSystemResource(Paths.get(document.getFilePath()).normalize().toAbsolutePath()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Resource> deleteDocument(@PathVariable Long id) throws IOException {
        try {
            service.deleteDocument(id);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/search")
    public ResponseEntity<List<Document>> searchDocuments(@RequestParam String fileName) throws Exception {
        try {
            return new ResponseEntity<>(service.searchDocuments(fileName), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
