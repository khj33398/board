package kr.co.hk.board.service;

import kr.co.hk.board.dto.BoardDTO;
import kr.co.hk.board.dto.PageRequestDTO;
import kr.co.hk.board.dto.PageResultDTO;
import kr.co.hk.board.entity.Board;
import kr.co.hk.board.entity.Member;

public interface BoardService {
    //게시물 등록을 위한 메서드
    public Long register(BoardDTO dto);

    //목록 보기 요청을 처리할 메서드
    public PageResultDTO<BoardDTO, Object []> getList(PageRequestDTO dto);

    //상세 보기 요청을 처리할 메서드
    public BoardDTO get(Long bno);

    //게시글을 삭제하는 메서드
    public void removeWithReplies(Long bno);

    //게시글을 수정하는 메서드
    public void modify(BoardDTO dto);

    //이 메서드를 인터페이스에서 선언하고 클래스에서 구현해도 되고 클래스에 private 형태로 만들어도 되지만
    //인터페이스에 만든 이유는 클래스에는 실제 비즈니스 로직에 관련된 메서드만 존재하게 하고 싶어서이다.
    //이러한 메서드를 별도의 클래스에 static 메서드로 만들어 두어도 되는데
    //이러한 경우 클래스 이름에는 Wrapper를 붙이는 것이 좋음

    //Board DTO를 Board Entity로 변환해주는 메서드
    default Board dtoToEntity(BoardDTO dto){
        Member member = Member.builder()
                .email(dto.getWriterEmail())
                .build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();

        return board;
    }

    //Board Entity를 Board DTO 클래스로 변환해주는 메서드
    default BoardDTO entityToDTO(Board board,
                                 Member member,
                                 Long replyCount){
        BoardDTO dto = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())
                .build();
        return dto;
    }

}
