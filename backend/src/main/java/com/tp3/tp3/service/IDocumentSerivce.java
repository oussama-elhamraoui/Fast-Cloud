package com.tp3.tp3.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.tp3.tp3.model.Document;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public interface IDocumentSerivce {
    public Document uploadDocument(MultipartFile file) throws IOException;

    public List<Document> getAllDocuments();

    public Document downloadDocument(Long id) throws IOException;

    public void deleteDocument(Long id) throws IOException;

    public List<Document> searchDocuments(String fileName);
}