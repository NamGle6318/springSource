package com.example.blog.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.blog.DTO.BoardDTO;
import com.example.blog.entity.Board;
import com.example.blog.repository.BoardRepo;
import com.example.blog.repository.MemberRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final MemberRepo memberRepo;
    private final BoardRepo boardRepo;

    // 게시글 조회(전체)
    public List<BoardDTO> getBoards() {
        List<Board> boards = boardRepo.findAll();
        List<BoardDTO> boardDTOs = new ArrayList<>();
        boards.forEach(board -> {
            boardDTOs.add(entityToDto(board));
        });
        return boardDTOs;
    }

    // 게시글 조회(1개)
    public BoardDTO getBoard(Long id) {
        Board board = boardRepo.findById(id).get();

        BoardDTO boardDTO = entityToDto(board);

        return boardDTO;

    }

    // 게시글 작성
    public BoardDTO newBoard(BoardDTO boardDTO) {
        Board board = dtoToEntity(boardDTO);

        return entityToDto(boardRepo.save(board));
    }

    // 게시글 수정
    public BoardDTO updateBoard(BoardDTO boardDTO) {
        Board board = boardRepo.findById(boardDTO.getId()).get();
        board.updateBoard(boardDTO.getTitle(), boardDTO.getContent());

        return entityToDto(board);
    }

    // 게시글 삭제
    public void removeBoard(BoardDTO boardDTO) {
        boardRepo.deleteById(boardDTO.getId());
    }

    public BoardDTO entityToDto(Board board) {
        return BoardDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .authorId(board.getAuthor().getId())
                .authorName(board.getAuthor().getName())
                .build();
    }

    public Board dtoToEntity(BoardDTO boardDTO) {
        return Board.builder()
                .id(boardDTO.getId())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .author(memberRepo.findById(boardDTO.getAuthorId()).get())
                .build();
    }
}
