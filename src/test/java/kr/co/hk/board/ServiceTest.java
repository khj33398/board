package kr.co.hk.board;

import kr.co.hk.board.dto.BoardDTO;
import kr.co.hk.board.dto.PageRequestDTO;
import kr.co.hk.board.dto.PageResultDTO;
import kr.co.hk.board.dto.ReplyDTO;
import kr.co.hk.board.service.BoardService;
import kr.co.hk.board.service.ReplyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ServiceTest {
    @Autowired
    private BoardService boardService;

    //@Test
    public void testInsert(){
        BoardDTO dto = BoardDTO.builder()
                .title("삽입 테스트1")
                .content("삽입을 테스트 합니다.")
                .writerEmail("user1@gmail.com")
                .build();
        Long bno = boardService.register(dto);
        System.out.println("삽입한 글 번호 : "+bno);
    }

    //@Test
    public void testList(){
        PageRequestDTO dto = new PageRequestDTO();
        PageResultDTO<BoardDTO, Object []> result =
                boardService.getList(dto);
        for(BoardDTO boardDTO: result.getDtoList()){
            System.out.println(boardDTO);
        }
        System.out.println(result.getPageList());
    }

    //@Test
    public void testGet(){
        BoardDTO dto = boardService.get(100L);
        System.out.println(dto);
    }

    //@Test
    public void testDelete(){
        boardService.removeWithReplies(67L);
    }

    //@Test
    public void testModify(){
        BoardDTO dto = BoardDTO.builder()
                .bno(100L)
                .title("수정한 제목")
                .content("수정한 내용")
                .build();

        boardService.modify(dto);
    }

    @Autowired
    private ReplyService replyService;

    @Test
    public void testGetReplies(){
        List<ReplyDTO> list = replyService.getList(2L);
        System.out.println(list);
    }

}
