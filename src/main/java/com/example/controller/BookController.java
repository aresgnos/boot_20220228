package com.example.controller;

import java.util.Date;
import java.util.List;

import com.example.entity.Book;
import com.example.repository.BookRepository;
import com.example.service.SequenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/book")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    private SequenceService sequenceService;

    // 목록
    // 127.0.0.1:8080/book/selectlist?page=1&text=한글
    @GetMapping(value = "/selectlist")
    public String selectGET(Model model,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "text", defaultValue = "") String text) {

        // 페이지네이션(0, 개수) + code를 기준으로 내림차순 정렬
        PageRequest pagerequest = PageRequest.of(page - 1, 10,
                Sort.Direction.DESC, "code");
        List<Book> list = bookRepository.getBookList(text, pagerequest);
        model.addAttribute("list", list);

        // 페이지네이션 번호 생성
        long pages = bookRepository.getBookCount(text);
        model.addAttribute("pages", (pages - 1) / 10 + 1);

        return "book/selectlist";
    }

    // 책 등록
    @GetMapping(value = "/insert")
    public String insertPOST() {
        return "book/insert";
    }

    @PostMapping(value = "/insert")
    public String insertPOST(
            Model model,
            @ModelAttribute Book book) {
        System.out.println(book.toString());

        book.setCode(sequenceService.generatorSequence("SEQ_BOOK4_CODE"));
        book.setRegdate(new Date());

        Book retBook = bookRepository.save(book);

        if (retBook != null) {
            model.addAttribute("msg", "등록완료");
            model.addAttribute("url", "/book/selectlist");
            return "alert";
        }

        return "redirect:/book/insert";

    }

}
