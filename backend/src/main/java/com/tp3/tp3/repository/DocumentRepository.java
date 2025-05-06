package com.tp3.tp3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tp3.tp3.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query("Select d from Document d where LOWER(d.fileName) LIKE LOWER(CONCAT('%',:fileName,'%'))")
    public List<Document> searchDocuments(String fileName);
}