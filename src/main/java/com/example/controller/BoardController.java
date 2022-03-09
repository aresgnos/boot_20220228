package com.example.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.example.entity.Board;

import com.example.repository.BoardRepository;
import com.example.service.SequenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

    // 서비스 => mybatis => 설계 + 구현(SQL문)
    // 저장소 => jpa, hibernate => 설계 + 구현(SQL)
    @Autowired
    BoardRepository bRepository;

    @Autowired
    private SequenceService sequenceService;

    // 1개 삭제, 수정
    // RedirectAttributes => POST에서 GET으로 데이터 전송
    @PostMapping(value = "/action")
    public String actionPOST(
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam(name = "btn") String btn,
            @RequestParam(name = "rad") long no) {

        try {
            System.out.println(btn);
            System.out.println(no);

            // == => 비교가능 int long, char
            // equals는 문자, 공백까지 같아야함, Sting(문자열은 안됨)
            if (btn.equals("1개 삭제")) {
                bRepository.deleteById(no);
                model.addAttribute("msg", "삭제되었습니다.");
                model.addAttribute("url", "/board/selectlist");
                return "alert"; // 알림창 띄우고 url 자동 변경

            } else if (btn.equals("1개 수정")) {
                // url에 parameter로 붙임, 글번호가 보임
                redirectAttributes.addAttribute("no", no);

                // POST방식 1회성, 새로고침시 데이터 소멸됨, 글번호가 보이지 않음
                // 기능이 있어서 써본 것이다.
                // 이것도 세션에 추가하는 방식
                redirectAttributes.addFlashAttribute("no1", no);

                return "redirect:/board/update";
            }
            return "redirect:/board/selectlist";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home";
        }
    }

    // 글수정
    // http://127.0.0.1:8080/board/update?no=2
    @GetMapping(value = "/update")
    public String updateGET(
            Model model,
            HttpServletRequest request,
            @RequestParam(name = "no") long no) {

        System.out.println(no);

        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
        if (map != null) {
            long no1 = (long) map.get("no1");
            System.out.println("no1 : " + no1);
        }

        // null에 대한 처리
        // 자동으로 if문 처리가 되는 것이다.
        // => 있으면 정상적으로 들어가고 없으면 null
        Board board = bRepository.findById(no).orElse(null);
        model.addAttribute("board", board);

        return "board/update";
    }

    @PostMapping(value = "/update")
    public String updatePOST(
            Model model,
            @ModelAttribute Board board) {
        try {
            // 추가 => 기본키를 다르게 해서 저장
            // 수정 => 기본키를 해당하는 글번호에 동일하게 해서 새로 저장
            // 수정의 개념이 아니라 save의 개념

            // 기존 내용을 읽음
            Board board1 = bRepository.findById(board.getNo()).orElse(null);

            // 변경할 항목만 다시 저장
            board1.setTitle(board.getTitle());
            board1.setContent(board.getContent());
            board1.setWriter(board.getWriter());

            // 최종적으로 board1의 값을 저장
            bRepository.save(board1);

            model.addAttribute("msg", "수정되었습니다.");
            model.addAttribute("url", "/board/selectlist");
            return "alert"; // 알림창 띄우고 url변경 자동화
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home";
        }
    }

    // 테스트 목록
    // 127.0.0.1:8080/board/selectfind?type=3&text=5
    @GetMapping(value = "/selectfind")
    public String selectfindGET(Model model,
            @RequestParam(name = "text", defaultValue = "", required = false) String text,
            @RequestParam(name = "type", defaultValue = "", required = false) String type,

            @RequestParam(name = "type1", defaultValue = "0", required = false) String type1,
            @RequestParam(name = "hit", defaultValue = "0", required = false) long hit1,

            @RequestParam(name = "type2", defaultValue = "0", required = false) String type2,
            @RequestParam(name = "no", defaultValue = "0", required = false) List<Long> no) {

        List<Board> list = null;

        // 일치하는 항목 가져오기
        if (type.equals("title")) {
            // list = bRepository.findByTitle(text);
            list = bRepository.getBoardTitle(text);

        } else if (type.equals("writer")) {
            // list = bRepository.findByWriter(text);
            list = bRepository.getBoardWriter(text);

        } else if (type.equals("hit")) {
            long hit = 0L;
            try {
                // 문자로 되어 있는 숫자를 숫자형으로변경
                // "1234" => 1234
                // "" => X
                hit = Long.parseLong(text);
            } catch (Exception e) {
                hit = 0L;
            }
            // list = bRepository.findByHit(hit);
            list = bRepository.getBoardHit(hit);
        }

        if (text.length() == 0) {
            list = bRepository.findAll();
        }

        // 조회수 이상, 미만
        if (type1.equals("1")) { // 이상
            list = bRepository.findByHitGreaterThanEqual(hit1);
        } else if (type1.equals("2")) { // 미만
            list = bRepository.findByHitLessThan(hit1);
        }

        // 글번호 포함
        if (type2.equals("1")) { // 포함
            list = bRepository.findByNoIn(no);
        } else if (type2.equals("2")) { // 포함X
            list = bRepository.findByNoNotIn(no);
        }

        model.addAttribute("list", list);
        return "board/selectfind";
    }

    // 목록
    @GetMapping(value = "selectlist")
    public String selectGET(Model model) {
        List<Board> list = bRepository.findAll();
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

        Board retBoard = bRepository.save(board);

        if (retBoard != null) {
            model.addAttribute("msg", "글쓰기 완료");
            model.addAttribute("url", "/board/selectlist");
            return "alert";
        }
        return "redirect:/board/insert";
    }
}
