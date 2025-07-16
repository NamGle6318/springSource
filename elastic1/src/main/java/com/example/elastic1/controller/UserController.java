package com.example.elastic1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.elastic1.dto.UserDocumentDTO;
import com.example.elastic1.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@Log4j2
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public List<UserDocumentDTO> getList() {
        return userService.list();
    }

    @GetMapping("/{id}")
    public UserDocumentDTO getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public UserDocumentDTO putUser(@PathVariable String id, @RequestBody UserDocumentDTO userDocumentDTO) {
        userDocumentDTO.setId(id);
        return userService.updateUser(userDocumentDTO);
    }

    @DeleteMapping("/{id}")
    public String removeUser(@PathVariable String id) {
        userService.removeUser(id);
        return id;
    }

}
