package com.example.elastic2.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightParameters;
import org.springframework.stereotype.Service;

import com.example.elastic2.document.ProductDocument;
import com.example.elastic2.dto.ProductDTO;
import com.example.elastic2.entity.Product;
import com.example.elastic2.repository.ProductDocumentRepository;
import com.example.elastic2.repository.ProductRepository;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.NumberRangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductService {
        private final ProductDocumentRepository productDocumentRepository;
        private final ProductRepository productRepository;
        private final ElasticsearchOperations elasticsearchOperations;

        public List<ProductDocument> getProducts(int page, int size) {

                Pageable pageable = PageRequest.of(page, size);

                return productDocumentRepository.findAll(pageable).getContent();
        }

        public Product createProduct(ProductDTO productDTO) {
                Product product = Product.builder()
                                .name(productDTO.getName())
                                .description(productDTO.getDescription())
                                .price(productDTO.getPrice())
                                .rating(productDTO.getRating())
                                .category(productDTO.getCategory())
                                .build();

                Product saveProduct = productRepository.save(product);

                ProductDocument productDocument = ProductDocument.builder()
                                .id(saveProduct.getId().toString())
                                .name(saveProduct.getName())
                                .description(saveProduct.getDescription())
                                .price(saveProduct.getPrice())
                                .rating(saveProduct.getRating())
                                .category(saveProduct.getCategory())
                                .build();
                productDocumentRepository.save(productDocument);
                return saveProduct;
        }

        public void deleteProduct(Long id) {
                productRepository.deleteById(id);
                // 엘라스틱서치에서도 제거
                productDocumentRepository.deleteById(id.toString());
        }

        // 검색어 자동완성
        public List<String> getSuggestions(String query) {
                Query multiMatchQuery = MultiMatchQuery.of(m -> m.query(query)
                                .type(TextQueryType.BoolPrefix)
                                .fields("name"))._toQuery();

                NativeQuery nativeQuery = NativeQuery.builder()
                                .withQuery(multiMatchQuery)
                                .withPageable(PageRequest.of(0, 5))
                                .build();

                SearchHits<ProductDocument> searchHits = this.elasticsearchOperations.search(nativeQuery,
                                ProductDocument.class);

                return searchHits.getSearchHits().stream().map(hit -> hit.getContent().getName()).toList();
        }

        public List<ProductDocument> searchProduct(String query, String category, double minPrice, double maxPrice,
                        int page, int size) {
                Query multiMatchQuery = MultiMatchQuery.of(m -> m
                                .query(query)
                                .fields("name^3", "description^1", "category^2")
                                .fuzziness("AUTO"))._toQuery();

                List<Query> filters = new ArrayList<>();
                if (category != null && !category.isEmpty()) {
                        Query categoryFilter = TermQuery.of(t -> t.field("category.raw").value(category))._toQuery();
                        filters.add(categoryFilter);
                }

                Query priceRangeFilter = NumberRangeQuery.of(r -> r.field("price").gte(minPrice).lte(maxPrice))
                                ._toRangeQuery()
                                ._toQuery();
                filters.add(priceRangeFilter);

                Query ratingShould = NumberRangeQuery.of(r -> r.field("rating").gte(4.0))._toRangeQuery()._toQuery();

                Query boolQuery = BoolQuery.of(b -> b.must(multiMatchQuery).filter(filters).should(ratingShould))
                                ._toQuery();

                HighlightParameters highlightParameters = HighlightParameters
                                .builder()
                                .withPreTags("<b>")
                                .withPostTags("</b>")
                                .build();

                Highlight highlight = new Highlight(highlightParameters, List.of(new HighlightField("name")));
                HighlightQuery highlightQuery = new HighlightQuery(highlight, ProductDocument.class);

                NativeQuery nativeQuery = NativeQuery.builder()
                                .withQuery(boolQuery)
                                .withHighlightQuery(highlightQuery)
                                .withPageable(PageRequest.of(page - 1, size))
                                .build();

                SearchHits<ProductDocument> searchHits = this.elasticsearchOperations.search(nativeQuery,
                                ProductDocument.class);

                return searchHits.getSearchHits().stream().map(hit -> {
                        ProductDocument productDocument = hit.getContent();
                        String highlightedName = hit.getHighlightField("name").get(0);
                        productDocument.setName(highlightedName);
                        return productDocument;
                }).toList();
        }

}
