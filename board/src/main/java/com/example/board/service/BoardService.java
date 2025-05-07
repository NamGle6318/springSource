package com.example.board.service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
// import org.springframework.data.spel.spi.Function;
import org.springframework.stereotype.Service;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResultDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.entity.Reply;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.ReplyRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class BoardService {

        private final BoardRepository boardRepository;
        private final ReplyRepository replyRepository;

        //
        public PageResultDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO) {
                Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                                Sort.by("bno").descending());
                Page<Object[]> result = boardRepository.list(pageRequestDTO.getType(), pageRequestDTO.getKeyword(),
                                pageable); // queryDSL과 pagable은 호환이 안된다.
                // Function<T, R> : T -> R 로 변환
                Function<Object[], BoardDTO> fn = (entity -> entityToDto((Board) entity[0], (Member) entity[1],
                                (Long) entity[2]));
                List<BoardDTO> dtoList = result.stream().map(fn).collect(Collectors.toList());
                Long totalCount = result.getTotalElements();
                PageResultDTO<BoardDTO> pageResultDTO = PageResultDTO.<BoardDTO>withAll()
                                .dtoList(dtoList)
                                .totalCount(totalCount)
                                .pageRequestDTO(pageRequestDTO)
                                .build();

                return pageResultDTO;

        }

        public BoardDTO getRow(Long bno) {
                Object[] entity = boardRepository.getBoardByBno(bno);
                BoardDTO boardDTO = entityToDto((Board) entity[0], (Member) entity[1], (Long) entity[2]);
                return boardDTO;
        }



        // 수정
        public Long update(BoardDTO dto) {
                // 수정할 대상 찾기
                Board board = boardRepository.findById(dto.getBno()).orElseThrow();

                // 내용 업데이트
                board.setTitle(dto.getTitle());
                board.setContent(dto.getContent());

                // 저장
                boardRepository.save(board);

                return board.getBno();
        }

        // 삭제
        @Transactional // Reply, boardTBL 서로 다른 테이블 두개 접근 => 한꺼번에 처리(하나라도 실패시 롤백)
        public void delete(Long bno) {
                Board board = boardRepository.findById(bno).get();
                // List<Reply> list = replyRepository.findByBoard(board);
                replyRepository.deleteByBoardBno(bno);
                // for (Reply reply : list) {
                //         replyRepository.deleteById(reply.getRno());
                // }
                boardRepository.deleteById(bno);
        }

        // 생성 
        public Long create(BoardDTO dto) {
                Board board = dtoToEntity(dto);

                Board newBoard = boardRepository.save(board);

                return newBoard.getBno();
        }

        private BoardDTO entityToDto(Board board, Member member, Long replyCount) {
                BoardDTO boardDTO = BoardDTO.builder()
                                .bno(board.getBno())
                                .title(board.getTitle())
                                .content(board.getContent())
                                .email(member.getEmail())
                                .name(member.getName())
                                .replyCount(replyCount)
                                .createdDate(board.getCreatedDate())
                                .build();

                return boardDTO;
        }

        private Board dtoToEntity(BoardDTO boardDTO) {
                Board board = Board.builder()
                                .bno(boardDTO.getBno())
                                .title(boardDTO.getTitle())
                                .content(boardDTO.getContent())
                                .member(Member.builder().email(boardDTO.getEmail()).build())
                                .build();

                return board;
        }
}
