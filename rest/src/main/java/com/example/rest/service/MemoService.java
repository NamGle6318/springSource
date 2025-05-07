package com.example.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import com.example.rest.dto.MemoDTO;
import com.example.rest.entity.Memo;
import com.example.rest.repository.MemoRepository;

@RequiredArgsConstructor
@Service
public class MemoService {
    // repository 메소드를 호출한 후 결과 받기

    private final MemoRepository memoRepository;
    private final ModelMapper modelMapper;

    public List<MemoDTO> getList() {
        List<Memo> list = memoRepository.findAll();

        // Repository -> Service = Entity -> EntityDTO로 변환
        List<MemoDTO> memos = new ArrayList<>();

        // for (Memo memo : list) {
        // MemoDTO dto = MemoDTO.builder()
        // .mno(memo.getMno())
        // .memoText(memo.getMemoText())
        // .build();
        // memos.add(dto);
        // }

        // list.stream().forEach(memo -> System.out.println(memo));
        memos = list.stream().map(memo -> modelMapper.map(memo, MemoDTO.class)).collect(Collectors.toList());

        return memos;
    }

    // public List<MemoDTO> getList() {
    // // memoDTO를 반환해야함
    // List<Memo> list = memoRepository.findAll();

    // List<MemoDTO> memos = list.stream().map(memo ->
    // entityToDto(memo)).collect(Collectors.toList());

    // return memos;
    // }

    // public MemoDTO getRow(long mno) {
    // Memo memo =
    // memoRepository.findById(mno).orElseThrow(EntityNotFoundException::new);
    // MemoDTO dto = entityToDto(memo);

    // return dto;

    // }

    // ModelMapper를 사용해서 getRow 실행하기
    public MemoDTO getRow(long mno) {
        Memo memo = memoRepository.findById(mno).orElseThrow(EntityNotFoundException::new);

        MemoDTO dto = modelMapper.map(memo, MemoDTO.class);

        return dto;
    }

    public long memoUpdate(MemoDTO memoDTO) {
        // DTO 받아서 해당 DTO와 같은 행을 찾은걸 return. / ID를 기준으로 찾기?

        Memo memo = memoRepository.findById(memoDTO.getMno()).orElseThrow(EntityNotFoundException::new);
        memo.changeMemoText(memoDTO.getMemoText());
        memo = memoRepository.save(memo);

        return memo.getMno();
    }

    public void memoDelete(long mno) {
        memoRepository.deleteById(mno);
    }

    public Long memoCreate(MemoDTO memoDTO) {
        // 새로 입력할 memo는 memoDTO에 이미 저장이 된 상태
        // save(entity)를 실행하기위해
        // DTO -> entity로 변환하는 작업 필요
        Memo memo = modelMapper.map(memoDTO, Memo.class);
        memo = memoRepository.save(memo);
        return memo.getMno();
    }

    private MemoDTO entityToDto(Memo memo) {

        MemoDTO dto = MemoDTO.builder()
                .mno(memo.getMno())
                .memoText(memo.getMemoText())
                .createdDate(memo.getCreatedDate())
                .updatedDate(memo.getUpdatedDate())
                .build();
        return dto;
    }

    private Memo dtoToEntity(MemoDTO memoDTO) {

        Memo memo = Memo.builder()
                .memoText(memoDTO.getMemoText())
                .createdDate(memoDTO.getCreatedDate())
                .updatedDate(memoDTO.getUpdatedDate())
                .build();
        return memo;
    }

}
