package com.example.movie.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieImageDTO {
    private Long inum;

    private String uuid;
    private String imgName;
    private String path;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // 이미지 주소(path) + uuid 붙혀서 FullPath 가져옴 (이름 중복 방지)
    public String getThumbnailURL() {
        String thumbFullPath = "";
        try {
            thumbFullPath = URLEncoder.encode(path + "/s_" + uuid + "_" + imgName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return thumbFullPath;
    }

    public String getImageURL() {
        String FullPath = "";
        try {
            FullPath = URLEncoder.encode(path + "/" + uuid + "_" + imgName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return FullPath;
    }

}
