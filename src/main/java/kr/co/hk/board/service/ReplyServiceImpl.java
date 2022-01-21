package kr.co.hk.board.service;

import kr.co.hk.board.dto.ReplyDTO;
import kr.co.hk.board.entity.Board;
import kr.co.hk.board.entity.Reply;
import kr.co.hk.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{
    private final ReplyRepository replyRepository;
    @Override
    public Long register(ReplyDTO replyDTO) {
        Reply reply = dtoToEntity(replyDTO);
        replyRepository.save(reply);
        return reply.getRno();
    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        Reply reply = dtoToEntity(replyDTO);
        replyRepository.save(reply);
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }

    @Override
    public List<ReplyDTO> getList(Long bno) {
        //글 번호에 해당하는 댓글 가져오기
        List<Reply> result =
                replyRepository.getRepliesByBoardOrderByRno(
                        Board.builder().bno(bno).build());
        //댓글 정렬하기
        result.sort(new Comparator<Reply>() {
            @Override
            public int compare(Reply o1, Reply o2) {
                //return (int)(o2.getRno() - o1.getRno());
                //수정일 순으로 내림차순 정렬
                return o2.getModDate().compareTo(o1.getModDate());
            }
        });

        return result.stream()
                .map(reply -> entityToDTO(reply))
                .collect(Collectors.toList());
    }
}
