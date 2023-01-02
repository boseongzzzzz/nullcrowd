package com.teamtwo.nullfunding.notice.service;

import com.teamtwo.nullfunding.common.Exception.notice.NoticeDeleteException;
import com.teamtwo.nullfunding.common.Exception.notice.NoticeInsertException;
import com.teamtwo.nullfunding.common.Exception.notice.NoticeUpdateException;
import com.teamtwo.nullfunding.notice.model.dao.NoticeMapper;
import com.teamtwo.nullfunding.notice.model.dto.NoticeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired // NoticeService 인터페이스의 sql문을 읽어와 의존주입으로 객체 생성.
    private NoticeMapper noticeMapper;

    // 모든 공지사항 게시글 조회하는 용도의 메서드
    @Override
    public List<NoticeDTO> selectAllNoticeList() {

        List<NoticeDTO> noticeList = noticeMapper.selectAllNoticeList();

        return noticeList;
    }

    // 공지사항 게시글 추가하는 용도의 메서드
    @Override
    @Transactional
    public void insertNotice(NoticeDTO notice) throws NoticeInsertException {

        int result = noticeMapper.insertNotice(notice);

        if(!(result > 0)) {
            throw new NoticeInsertException("공지사항 등록에 실패하셨습니다.");
        }
    }

    // 공지사항 게시글 수정하는 용도의 메서드
    @Override
    @Transactional
    public void updateNotice(NoticeDTO notice) throws NoticeUpdateException {

        int result = noticeMapper.updateNotice(notice);

        if(!(result > 0)){
            throw new NoticeUpdateException("공지사항 수정에 실패하셨습니다.");
        }
    }

    // 공지번호를 입력받아 게시글을 삭제하는 용도의 메서드
    @Override
    @Transactional
    public void deleteNotice(int noticeNo) throws NoticeDeleteException {

        int result = noticeMapper.deleteNotice(noticeNo);

        if(!(result > 0)){
            throw new NoticeDeleteException("공지사항 삭제에 실패하셨습니다.");
        }
    }

    // 공지사항 게시글의 상세 페이지를 조회하는 용도의 메서드
    // 게시글 클릭 시 조회수가 증가한다.
    @Override
    public NoticeDTO selectChoiceNotice(int no) {
        NoticeDTO noticeDetail = null;

        int result = noticeMapper.incrementNoticeCount(no);

        if(result > 0) {
            noticeDetail = noticeMapper.selectChoiceNotice(no);
        }

        return noticeDetail;
    }

    @Override
    public int incrementNoticeCount(int no) {
        return 0;
    }

}