package kr.co.hk.board.service;

import kr.co.hk.board.dto.BoardDTO;
import kr.co.hk.board.dto.PageRequestDTO;
import kr.co.hk.board.dto.PageResultDTO;
import kr.co.hk.board.entity.Board;
import kr.co.hk.board.entity.Member;
import kr.co.hk.board.repository.BoardRepository;
import kr.co.hk.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;

    @Override
    public Long register(BoardDTO dto) {
        //등록을 위해서 Entity 객체로 변환
        Board board = dtoToEntity(dto);
        boardRepository.save(board);
        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO dto) {
        //Repository 메서드를 호출해서 결과 가져오기
        /*
        Page<Object []> result = boardRepository.getBoardWithReplyCount(
                dto.getPageable(Sort.by("bno").descending()));
        */

        Page<Object []> result = boardRepository.searchPage(
                dto.getType(), dto.getKeyword(),
                dto.getPageable(Sort.by("bno").descending()));

        Function<Object [], BoardDTO> fn = (
                en -> entityToDTO((Board)en[0],
                        (Member)en[1],
                        (Long)en[2]));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bno) {
        Object result = boardRepository.getBoardByBno(bno);
        Object [] ar = (Object[]) result;
        return entityToDTO((Board)ar[0], (Member)ar[1], (Long)ar[2]);
    }

    private final ReplyRepository replyRepository;

    @Override
    @Transactional
    public void removeWithReplies(Long bno) {
        //댓글부터 삭제
        replyRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);
    }

    @Override
    public void modify(BoardDTO dto) {
        //데이터를 조회해서 있으면 수정
        Optional<Board> board =  boardRepository.findById(dto.getBno());
        if(board.isPresent()){
            board.get().changeTitle(dto.getTitle());
            board.get().changeContent(dto.getContent());
            boardRepository.save(board.get());
        }

    }
}
