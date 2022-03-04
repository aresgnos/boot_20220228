package com.example.controller;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.entity.Book;
import com.example.service.BookDB;
import com.example.service.SequenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    BookDB bookDB;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    HttpSession httpSession;

    // 일괄 등록
    // 127.0.0.1:8080/admin/insertbatch
    @GetMapping(value = "/insertbatch")
    public String insertGET() {
        return "admin/insertbatch";
    }

    @PostMapping(value = "/insertbatch")
    public String insertPOST(
            @RequestParam(name = "title") String[] title,
            @RequestParam(name = "price") long[] price,
            @RequestParam(name = "writer") String[] writer,
            @RequestParam(name = "category") String[] category) {

        // 1. 빈 리스트 만들기
        List<Book> list = new ArrayList<Book>();

        for (int i = 0; i < title.length; i++) { // 0 1
            System.out.println(
                    title[i] + "," + price[i] + ","
                            + writer[i] + "," + category[i]);
            Book book = new Book();
            book.setCode(sequenceService.generatorSequence("SEQ_BOOK4_CODE"));
            book.setTitle(title[i]);
            book.setPrice(price[i]);
            book.setWriter(writer[i]);
            book.setCategory(category[i]);
            book.setRegdate(new Date());

            list.add(book);
        }
        bookDB.insertBatchBook(list);
        return "redirect:/admin/insertbatch";
    }

    // 127.0.0.1:8080/admin/selectlist?page=1&text=
    @GetMapping(value = "selectlist")
    public String selectlistGET(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "text", defaultValue = "") String text) {

        // 페이지 + 검색
        List<Book> list = bookDB.selectListPageSearchBook(page, text);
        long pages = bookDB.countSearchBook(text);

        // jsp로 전달(변수, 값) => 변수
        model.addAttribute("list", list);
        model.addAttribute("pages", (pages - 1) / 10 + 1);

        return "/admin/selectlist";
    }

    @PostMapping(value = "/action")
    public String actionPOST(
            @RequestParam(name = "btn") String btn,
            @RequestParam(name = "chk") long[] code) {
        System.out.println(btn); // 일괄삭제, 일괄수정

        if (btn.equals("일괄삭제")) {
            // DB에 삭제하기 구현
            for (int i = 0; i < code.length; i++) {
                Book book = new Book();
                book.setCode(code[i]);
                System.out.println(code[i]);
            }
            int ret = bookDB.deleteBatchBook(btn, code);
            if (ret == 1) {
                return "redirect:/admin/selectlist";
            }

            // 회원목록, 물품목록에 검색기능 추가

        } else if (btn.equals("일괄수정")) {
            // 세션에 long[]의 code를 세션에 넣음
            httpSession.setAttribute("code", code);

            // 페이지를 이동함
            // long[] code1 = (long[]) httpSession.getAttribute("code");
            return "redirect:/admin/updatebatch";
        }

        // 목록으로 이동하기
        return "redirect:/admin/selectlist";
    }
}
