package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.entity.Member;
import com.example.service.MemberDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = { "/member" })
public class MemberController {

    // 설계 도면 가져오기
    // DB의 일을 수행하는 클래스
    // = 클래스명 obj = new 클래스명();
    @Autowired
    private MemberDB memberDB;

    // 마이페이지
    @GetMapping(value = "/mypage")
    public String mypageGET(
            HttpSession httpSession,
            Model model,
            @RequestParam(name = "menu,id", defaultValue = "0") String id, int menu) {

        if (menu == 0) {
            return "redirect:/member/mypage?menu=1";
        }
        // 세션에서 정보를 읽음
        String userid = (String) httpSession.getAttribute("USERID");

        Member member = memberDB.selectOneMember(id);
        model.addAttribute("member", member);

        // 세션에 정보가 없다면 )로그인 되지 않은 상태에서 mypage 접근)
        if (userid == null) {
            return "redirect:/member/login"; // 로그인 페이지로 이동
        }
        return "member/mypage"; // 정보변경, 암호변경, 탈퇴하기
    }

    // 로그인
    // 127.0.0.1:8080/member/login
    @GetMapping(value = "/login")
    public String longinGET() {
        return "member/login";
    }

    @PostMapping(value = "/login")
    public String loginPOST(@ModelAttribute Member member,
            HttpSession httpSession) {
        // DB에 아이디, 암호를 전달하여 일치하는 항목이 있는지 확인
        Member retMember = memberDB.selectLogin(member);
        System.out.println(retMember);
        if (retMember != null) {
            // 여기가 로그인이 되는 시점!!
            // 세션 : 서버에 기록되는 정보(모든 주소, 컨트롤러에서 공유 가능)
            // 비밀번호는 기록하면 안된다.
            httpSession.setAttribute("USERID", retMember.getId());
            httpSession.setAttribute("USERNAME", retMember.getName());
            return "redirect:/home";

        }
        return "redirect:/member/login";
    }

    // 로그아웃
    @GetMapping(value = "/logout")
    public String longinGETH(HttpSession httpSession) {
        // 세션을 완전히 삭제함. 로그인 기록을 삭제.
        httpSession.invalidate();
        return "redirect:/home";
    }

    // 수정
    // 127.0.0.1:8080/member/update?id=aaaa
    @GetMapping(value = "/update")
    public String updateGET(
            Model model,
            @RequestParam(name = "id") String id) {

        // DB에서 내용을 가져오기
        Member member = memberDB.selectOneMember(id);

        // jsp로 전달해줌
        model.addAttribute("member", member);

        return "/member/update"; // 표시할 jsp 파일명 확장자는 뺌
    }

    @PostMapping(value = "/update")
    public String updatePOST(@ModelAttribute Member member) {
        System.out.println(member.toString());

        int ret = memberDB.updateMember(member);

        if (ret == 1) {

            // post에서는 jsp를 표시X, redirect를 이용해 주소를 변경해야함.
            return "redirect:/member/selectlist";
        }

        // 127.0.0.1:8080/member/update?id=aaaa
        return "redirect:/member/update?id=" + member.getId();
    }

    // 삭제
    // 127.0.0.1:8080/member/delete?id=aaaa
    // <form action="" method="get or post">
    // <input type="text" name="id" value="1" />
    // </form>
    // 위 아래 같음
    // <a href="/member/delete?id=aaaa">버튼</a>
    @GetMapping(value = { "/delete" })
    public String deleteGET(@RequestParam(name = "id") String id) {
        int ret = memberDB.deleteMember(id);
        if (ret == 1) { // 성공
            return "redirect:/home";
        }
        // 실패
        return "redirect:/member/selectlist";
    }

    // 회원 목록
    // 127.0.0.1:8080/member/selectlist
    @GetMapping(value = { "/selectlist" })
    public String selectlistGET(Model model) { // 변수명 마음대로
        // 1. DB에서 목록 받아오기
        List<Member> list = memberDB.selectListMember();

        // 2. jsp로 전달하기(jsp에서의 변수명, 실제 전송값)
        model.addAttribute("list", list);

        // 3. member 폴더의 select.jsp를 표시하라.
        return "member/select";
    }

    // 회원가입
    // 127.0.0.1:8080/member/insert
    @GetMapping(value = { "/insert" })
    public String insertGET() {

        // member 폴더에 있는 insert.jps (쉬운 관리를 위해 폴더별로 관리)
        // 화면에 뭔가를 표시하는 return
        // member 폴더의 insert.jps를 표시하라.
        return "member/insert";
    }

    // post는 사용자가 입력한 내용이 전달되고 DB작업을 위해서 필요한 시점
    // jsp를 표시하는게 아닌 주소 입력후 엔터키를 누른 효과
    // Member.java의 변수명과 insert.jsp의 name 값이 일치해야 데이터가 들어간다.
    @PostMapping(value = { "/insert" })
    public String insertPOST(@ModelAttribute Member mem) {

        System.out.println(mem.toString());
        memberDB.insertMember(mem);

        // 주소창에 /member/insert를 입력 후 엔터키를 누르는 것과 같은 역할
        // 깜빡, 페이지를 이동시켜주는 return
        return "redirect:/member/insert";
    }
}
