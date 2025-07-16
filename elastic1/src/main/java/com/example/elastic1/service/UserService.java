package com.example.elastic1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.elastic1.document.UserDocument;
import com.example.elastic1.dto.UserDocumentDTO;
import com.example.elastic1.repository.UserDocumentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserDocumentRepository userDocumentRepository;

    public List<UserDocumentDTO> list() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserDocument> result = userDocumentRepository.findAll(pageable);

        return result.get().map(doc -> docToDto(doc)).collect(Collectors.toList());

    }

    public UserDocumentDTO create(UserDocumentDTO userDocumentDTO) {

        UserDocument userDocument = dtoToDoc(userDocumentDTO);
        return docToDto(userDocumentRepository.save(userDocument));
    }

    public UserDocumentDTO getUser(String id) {
        return docToDto(userDocumentRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다.")));
    }

    public UserDocumentDTO updateUser(UserDocumentDTO userDocumentDTO) {
        UserDocument userDocumnet = userDocumentRepository.findById(userDocumentDTO.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        userDocumnet.setName(userDocumentDTO.getName());
        userDocumnet.setAge(userDocumentDTO.getAge());
        userDocumnet.setIsActive(userDocumentDTO.getIsActive());

        return docToDto(userDocumentRepository.save(userDocumnet));
    }

    public String removeUser(String id) {
        userDocumentRepository.deleteById(id);

        return id;
    }

    private UserDocumentDTO docToDto(UserDocument userDocument) {
        return UserDocumentDTO.builder()
                .id(userDocument.getId())
                .name(userDocument.getName())
                .age(userDocument.getAge())
                .isActive(userDocument.getIsActive())
                .build();
    }

    private UserDocument dtoToDoc(UserDocumentDTO userDocumentDTO) {
        return UserDocument.builder()
                .id(userDocumentDTO.getId())
                .name(userDocumentDTO.getName())
                .age(userDocumentDTO.getAge())
                .isActive(userDocumentDTO.getIsActive())
                .build();
    }
}
