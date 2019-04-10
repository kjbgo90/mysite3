package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;

	/* 게시판 정보 가져오는 서비스 */
	public List<BoardVo> getBoardList() {
		return boardDao.selectList();
	}

	/* 게시판에 글 작성하는 서비스 */
	public int writeBoard(BoardVo vo) {
		return boardDao.insertBoard(vo);
	}

	/* 글 읽는 서비스 */
	public BoardVo readBoard(int no) {
		//hit 1 up 한 뒤에
		boardDao.updateHit(no);
		
		//글 가져와서 리턴
		return boardDao.select(no);
	}

	/* 수정 할 글 가져오는 서비스 */
	public BoardVo modiGet(int no) {
		return boardDao.select(no);
	}
	
	/* 글을 수정 하는 서비스 */
	public int modify(BoardVo vo) {
		return boardDao.update(vo);
	}

	/* controller에서 비교 후 삭제하는 서비스 */
	public int deleteCon(int no) {
		return boardDao.delete(no);
	}
	
	/* Service 에서 비교 후 삭제하는 서비스 */
	public int deleteServ(int no, int write_no, int auth_no) {
		if(write_no == auth_no)
			return boardDao.delete(no);
		else
			return 0;
	}
}
