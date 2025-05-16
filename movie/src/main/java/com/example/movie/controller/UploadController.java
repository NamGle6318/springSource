package com.example.movie.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.movie.dto.UploadResultDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RequestMapping("/upload")
@Controller
public class UploadController {

    // application.properties 에 작성한 값 불러오기
    @Value("${com.example.movie.upload.path}")
    private String uploadPath; // = /(상대경로)/upload

    @GetMapping("/create")
    public String getUploadForm() {

        return "/upload/test";
    }

    @PostMapping("/remove")
    public ResponseEntity<String> postRemoveFile(String fileName) {

        log.info("해당 파일 삭제 요청 : {}", fileName);
        try {
            String oriFileName = URLDecoder.decode(fileName, "utf-8"); // 2025~/upload/

            File file = new File(uploadPath + File.separator + oriFileName);
            File thumbnailFile = new File(file.getParent(), "s_" + file.getName()); // file.getParent() = 파일의 폴더 경로까지 다
                                                                                    // 끌어옴

            log.info("oriFileName {}", oriFileName);
            log.info("file {}", file);
            log.info("thumbnailFile {}", thumbnailFile);
            // 원본, 썸네일 파일 삭제
            file.delete();
            thumbnailFile.delete();
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Rest 방식으로 전달하기 위해 클래스 타입을 ResponseEntity<>로 설정
    @PostMapping("/files")
    public ResponseEntity<List<UploadResultDTO>> postUpload(MultipartFile[] uploadFiles) {

        List<UploadResultDTO> uploadResultDTOs = new ArrayList<>();
        // MultipartFile => 파일 받아와주는 클래스 , 여러개일시 (배열)[] 추가
        for (MultipartFile uploadFile : uploadFiles) {
            // String oriName = uploadFile.getOriginalFilename();
            // String fileName = oriName.substring(oriName.lastIndexOf("\\") + 1); //

            // log.info("oriName {}", oriName); // => 파일명.jpg
            // log.info("fileName {}", fileName); // => 파일명.jpg

            if (!uploadFile.getContentType().startsWith("image")) {
                return new ResponseEntity<List<UploadResultDTO>>(HttpStatus.BAD_REQUEST);
            }

            String oriName = uploadFile.getOriginalFilename();
            String saveFolderPath = makeFolder();

            String uuid = UUID.randomUUID().toString();
            // /upload/새폴더/uuid_파일명
            String saveName = uploadPath + File.separator + saveFolderPath + File.separator + uuid + "_" + oriName;
            Path savePath = Paths.get(saveName);

            try {
                // 디렉토리에 저장
                uploadFile.transferTo(savePath);
                // 썸네일 저장
                String thumbnailSaveName = uploadPath + File.separator + saveFolderPath + File.separator + "s_" + uuid
                        + "_" + oriName;
                File thumbFile = new File(thumbnailSaveName);
                Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 100, 100);

            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            uploadResultDTOs.add(new UploadResultDTO(oriName, uuid, saveFolderPath));
        }
        return new ResponseEntity<List<UploadResultDTO>>(uploadResultDTOs, HttpStatus.OK);
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName, String size) {
        ResponseEntity<byte[]> result = null;

        try {
            String srcFileName = URLDecoder.decode(fileName, "utf-8");

            File file = new File(uploadPath + File.separator + srcFileName);

            if (size != null && size.equals("1")) {
                // s_ 제거
                file = new File(file.getParent(), file.getName().substring(2));
            }

            HttpHeaders headers = new HttpHeaders();
            // toPath() : 해당 파일의 타입을 반환 =>
            // upload\2025\05\15\e59074b5-efbb-4777-b04b-d9750a845a82_alien1".png"
            headers.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    // 날짜(년-월-일) 단위로 분류할 폴더 생성
    private String makeFolder() {
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = dateStr.replace("/", File.separator);

        File uploadPathFolder = new File(uploadPath, folderPath); // File.separator : '/' => '\'(운영체제에 맞게 바꿔줌)
        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs(); // 디렉토리가 존재하지 않을 시 디렉토리 생성
        }
        return folderPath;
    }

}
