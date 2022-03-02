package com.example.controller;

import java.io.IOException;
import java.util.List;

import com.example.entity.Item;
import com.example.service.ItemDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = { "/item" })
public class ItemController {

    @Autowired
    private ItemDB itemDB;

    // 물품 목록
    // 127.0.0.1:8080/item/selectlist
    // 127.0.0.1:8080/item/selectlist?page=1
    @RequestMapping(value = "/selectlist", method = RequestMethod.GET)
    public String selectListGET(Model model,
            @RequestParam(name = "page", defaultValue = "0") int page) {

        if (page == 0) { // ?page=1을 추가하는 부분
            return "redirect:/item/selectlist?page=1";
        }

        Pageable pageable = PageRequest.of(page - 1, 10);
        List<Item> list = itemDB.selectListItem(pageable);

        // 목록
        model.addAttribute("list", list);
        model.addAttribute("pages", 10);

        return "/item/selectlist";
    }

    // 127.0.0.1:8080/item/image?code=2
    // 이미지 조회
    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> imageGET(
            @RequestParam(name = "code") long code) {
        try {
            System.out.println(code);
            Item item = itemDB.selectOneImage(code);
            System.out.println(item.getFilesize());
            if (item.getFilesize() > 0) { // 이미지가 있음

                HttpHeaders headers = new HttpHeaders();
                if (item.getFiletype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (item.getFiletype().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                } else if (item.getFiletype().equals("image/gif")) {
                    headers.setContentType(MediaType.IMAGE_GIF);
                }

                // 생성자 (실제 데이터, headers, 200)
                ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(item.getFiledata(), headers,
                        HttpStatus.OK);
                return response;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 물품 등록
    // 127.0.0.1:8080/item/insert
    @GetMapping(value = { "/insert" })
    public String insertGET() {

        // item 폴더의 insert.jsp를 표시
        return "item/insert";
    }

    @PostMapping(value = { "/insert" })
    public String insertPOST(
            @ModelAttribute Item item,
            @RequestParam(name = "image") MultipartFile file) throws IOException {
        if (file.isEmpty() == false) { // 첨부됨.
            item.setFilename(file.getOriginalFilename());
            item.setFilesize(file.getSize());
            item.setFiletype(file.getContentType());
            item.setFiledata(file.getBytes()); // => 오류를 위임받음
        }
        System.out.println(file.toString());

        int ret = itemDB.inserItem(item);
        if (ret == 1) {
            // 주소가 바뀌는지만 확인
            return "redirect:/item/selectlist";
        }

        // GET으로 변경 127.0.0.1:8080/item/insert
        return "redirect:/item/insert";
    }
}
