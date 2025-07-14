package com.example.blog.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.blog.DTO.BoardDTO;
import com.example.blog.Service.BoardService;
import com.example.blog.Service.MemberService;
import com.example.blog.entity.Board;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/rest/board")

@RestController
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardService boardService;

    @ResponseBody
    @GetMapping("/getBoards")
    public List<BoardDTO> getBoards() {
        return boardService.getBoards();
    }

    @ResponseBody
    @GetMapping("/getBoard/{id}")
    public BoardDTO getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    @PostMapping("/newBoard")
    public BoardDTO newBoard(@RequestBody BoardDTO boardDTO) {
        return boardService.newBoard(boardDTO);
    }

    @PutMapping("updateBoard")
    public BoardDTO updateBoard(@RequestBody BoardDTO boardDTO) {
        return boardService.updateBoard(boardDTO);
    }

    @DeleteMapping("/removeBoard")
    public void removeBoard(@RequestBody BoardDTO boardDTO) {
        boardService.removeBoard(boardDTO);
    }

}
