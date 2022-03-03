package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.example.entity.Item;
import com.example.service.ItemDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
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

    // resources/하위 폴더의 파일을 읽기 위한 객체 생성
    // ResourceLoader resource = new ResourceLoader();
    @Autowired
    private ResourceLoader resource;

    // 물품 목록
    // 127.0.0.1:8080/item/selectlist
    // 127.0.0.1:8080/item/selectlist?page=1
    @RequestMapping(value = "/selectlist", method = RequestMethod.GET)
    public String selectListGET(Model model,
            @RequestParam(name = "page", defaultValue = "0") int page) {

        if (page == 0) { // ?page=1을 추가하는 부분
            return "redirect:/item/selectlist?page=1";
        }

        // 1. 목록
        Pageable pageable = PageRequest.of(page - 1, 10);
        List<Item> list = itemDB.selectListItem(pageable);

        // 2. 페이지네이션 개수
        long pages = itemDB.selectItemCount();

        // 목록
        model.addAttribute("list", list);

        // 10개일 때 1
        // 11개일 때 2
        // 23개일 때 3
        model.addAttribute("pages", (pages - 1) / 10 + 1);

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

            } else { // 이미지가 없는 경우
                InputStream stream = resource.getResource("classpath:/static/img/default1.jpg").getInputStream();

                // ex) default.jpg, gif, png
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);

                ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(stream.readAllBytes(), headers,
                        HttpStatus.OK);
                return response;
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

    // 수정
    // 127.0.0.1:8080/item/update?code=33
    @GetMapping(value = "/update")
    public String updateGET(Model model, @RequestParam(name = "code") long code) {
        // DB에서 1개의 정보 읽음
        Item item = itemDB.selectOneItem(code);
        // JSP로 전달
        model.addAttribute("item", item);
        // update.jsp 표시
        return "item/update";
    }

    @PostMapping(value = "/update")
    public String updatePOST(@ModelAttribute Item item,
            @RequestParam(name = "image") MultipartFile file) throws IOException {

        if (!file.isEmpty()) { // 첨부됨
            item.setFilename(file.getOriginalFilename());
            item.setFilesize(file.getSize());
            item.setFiletype(file.getContentType());
            item.setFiledata(file.getBytes());
        }

        int ret = itemDB.updateItemOne(item);
        if (ret == 1) {
            return "redirect:/item/selectlist";
        }

        return "redirect:/item/selectlist";
    }

    // 삭제
    @GetMapping(value = "/delete")
    public String deleteGET(@RequestParam(name = "code") long code) {
        int ret = itemDB.deleteItemOne(code);
        if (ret == 1) {
            return "redirect:/item/selectlist";
        }
        // DB에서 삭제
        return "redirect:/item/selectlist";
    }

}
