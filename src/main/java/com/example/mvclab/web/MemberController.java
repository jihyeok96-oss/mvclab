package com.example.mvclab.web;

import com.example.mvclab.member.Member;
import com.example.mvclab.exception.MemberNotFoundException;
import com.example.mvclab.member.MemberService;
import com.example.mvclab.member.dto.MemberForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    //localhost:8081/members
    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("members", memberService.findAll());
        return "members/list";
    }

    //localhost:8081/members/1
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, Model model) throws MemberNotFoundException {
        model.addAttribute("members", memberService.findById(id));
        return "members/detail";
    }

    //localhost:8081/members/join
    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/form";
    }

    @PostMapping("/join")
    public String join(
            @Valid
            @ModelAttribute("memberForm") MemberForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "members/form";
        }
        Member member = memberService.save(
                form.getName(),
                form.getPassword(),
                form.getEmail(),
                form.getAge()
        );

        redirectAttributes.addAttribute("message", "회원가입이 완료되었습니다.");
        redirectAttributes.addAttribute("id", member.getId());

        return "redirect:/members";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) throws MemberNotFoundException {
        //해당 회원 검색
        Member member = memberService.findById(id);
        //Member => MemberForm
        MemberForm form = new MemberForm();
        form.setId(member.getId());
        form.setName(member.getName());
        form.setPassword(member.getPassword());
        form.setPasswordConfirm(member.getPassword());
        form.setEmail(member.getEmail());
        form.setAge(member.getAge());
        model.addAttribute("memberForm", form);
        return "members/edit";
    }

    @PutMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,
                       @Valid
                       @ModelAttribute("memberForm") MemberForm form,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        //오류 처리 확인
        if (bindingResult.hasErrors()) {
            return "members/edit";
        }
        //MemberForm => Member
        memberService.update(
                id,
                form.getName(),
                form.getEmail(),
                form.getAge()
        );

        return "redirect:/members/" + id;
    }

    @DeleteMapping("/del/{id}")
    @ResponseBody()
    public String delete(@PathVariable("id") Long id) {
        //서비스를 호출하여 삭제 처리
        memberService.delete(id);
        return "deleted!";
    }
}
