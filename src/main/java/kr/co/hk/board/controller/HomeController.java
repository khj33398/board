package kr.co.hk.board.controller;

import kr.co.hk.board.dto.PageRequestDTO;
import kr.co.hk.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class HomeController {
    private final BoardService boardService;

    @GetMapping("/")
    public String List(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute(
                "result", boardService.getList(pageRequestDTO));
        return "/board/list";
    }
}
