package com.example.elastic2.document;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.annotations.Setting;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setting(settingPath = "/elasticsearch/product-settings.json")

@Document(indexName = "products")
public class ProductDocument {
        @Id
        private String id;

        @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "products_name_analyzer"), otherFields = {
                        @InnerField(suffix = "auto_complete", type = FieldType.Search_As_You_Type, analyzer = "nori")
        })
        private String name;

        @Field(type = FieldType.Text, analyzer = "products_description_analyzer")
        private String description;

        @Field(type = FieldType.Integer)
        private int price;

        @Field(type = FieldType.Double)
        private double rating;

        @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "products_category_analyzer"), otherFields = {
                        @InnerField(suffix = "raw", type = FieldType.Keyword)
        })
        private String category;

}
