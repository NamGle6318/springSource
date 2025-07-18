package com.example.elastic2.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.elastic2.document.ProductDocument;

public interface ProductDocumentRepository extends ElasticsearchRepository<ProductDocument, String> {

}
