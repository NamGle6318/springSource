package com.example.demo.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.CalcDTO;

@Log4j2
@Controller
public class AddController {
    // http:// localhost:8080/calc => calc.html

    @GetMapping("/calc")
    public void getCalc() {
        log.info("calc 접속 요청");

    }

    @PostMapping("/calc")
    public String postCalc(CalcDTO calcDTO) {

        log.info("calc 연산 요청");

        switch (calcDTO.getOp()) {
            case "+":
                calcDTO.setResult(calcDTO.getNum1() + calcDTO.getNum2());
                break;
            case "-":
                calcDTO.setResult(calcDTO.getNum1() - calcDTO.getNum2());
                break;
            case "*":
                calcDTO.setResult(calcDTO.getNum1() * calcDTO.getNum2());
                break;
            case "/":
                calcDTO.setResult((calcDTO.getNum1() / calcDTO.getNum2()));
                break;
            default:
                break;
        }
        log.info("{} {} {} = {}", calcDTO.getNum1(), calcDTO.getOp(), calcDTO.getNum2(), calcDTO.getResult());
        return "result";
    }
}