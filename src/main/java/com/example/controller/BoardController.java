package com.example.controller;

import java.util.Date;
import java.util.List;

import com.example.entity.Board;
import com.example.entity.Book;
import com.example.repository.BoardRepository;
import com.example.service.SequenceService;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

    // 서비스 => mybatis => 설계 + 구현(SQL문)
    // 저장소 => jpa, hibernate => 설계 + 구현(SQL)
    @Autowired
    BoardRepository bRespository;

    @Autowired
    private SequenceService sequenceService;

    // 1개 삭제, 수정
    @PostMapping(value = "/action")
    public String actionPOST(
            Model model,
            @RequestParam(name = "btn") String btn,
            @RequestParam(name = "rad") long no) {

        System.out.println(btn);
        System.out.println(no);

        // equals는 문자, 공백까지 같아야함
        if (btn.equals("1개 삭제")) {
            bRespository.deleteById(no);

        } else if (btn.equals("1개 수정")) {
            // 화면 전환하기 /board/update
            // bRespository.save를 이용 단, _id가 조건으로 됨
            bRespository.save(Book);

        }

        return "redirect:/board/selectlist";
    }

    // 목록
    @GetMapping(value = "selectlist")
    public String selectGET(Model model) {
        List<Board> list = bRespository.findAll();
        model.addAttribute("list", list);

        return "board/selectlist";
    }

    // 글쓰기
    @GetMapping(value = "/insert")
    public String insertGET() {
        return "board/insert";
    }

    @PostMapping(value = "/insert")
    public String insertPOST(
            Model model,
            @ModelAttribute Board board) {
        System.out.println(board.toString());

        // "SEQ_BOARD4_NO"
        board.setNo(sequenceService.generatorSequence("SEQ_BOARD4_NO"));
        board.setRegdate(new Date());

        Board retBoard = bRespository.save(board);

        if (retBoard != null) {
            model.addAttribute("msg", "글쓰기 완료");
            model.addAttribute("url", "/board/selectlist");
            return "alert";
        }
        return "redirect:/board/insert";
    }
}
