package com.example.movie.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadResultDTO {

    private String fileName;
    private String uuid;
    private String folderPath;

    public String getImageURL() {
        String fullPath = "";

        try {
            // Spring Security의 encode : 원래의 값에 Salt값을 넣어서 해싱한 것 = {bcrypt} 방식(변경가능)
            fullPath = URLEncoder.encode(folderPath + "/" + uuid + "_" + fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

        return fullPath;
    }

    public String getThumbnailImageURL() {
        String fullPath = "";

        try {
            // Spring Security의 encode : 원래의 값에 Salt값을 넣어서 해싱한 것 = {bcrypt} 방식(변경가능)
            fullPath = URLEncoder.encode(folderPath + "/" + "s_" + uuid + "_" + fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

        return fullPath;
    }
}
