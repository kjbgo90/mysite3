package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;

	/* 게시판 정보 가져오는 서비스 */
	public Map<String, Object> getBoardList(int page, String kwd) {
		//페이지당 보여지는 글의 갯수
		int listCnt = 10;
		
		//음수 값이 들어왔을때 처리
		page = (page > 0) ? page : (page = 1);
		
		int startRnum = (page - 1) * listCnt;
		int endRnum = startRnum + listCnt;
		
		List<BoardVo> boardList = boardDao.selectList(startRnum, endRnum, kwd);
		
		//전체 글 갯수
		int totalCount = boardDao.totalCount(kwd);
		
		//페이지 당 버튼 갯수
		int pageBtnCount = 5;
		
		
		int endPageBtnNo = (int)Math.ceil(page/(double)pageBtnCount) * pageBtnCount;
		int startPageBtnNo = endPageBtnNo - (pageBtnCount - 1);
		boolean next = false;
		if(endPageBtnNo*listCnt < totalCount) {
			next = true;
		}else {
			endPageBtnNo = (int)Math.ceil(totalCount / (double)listCnt);
		}
		boolean prev = false;
		if(startPageBtnNo != 1) {
			prev = true;
		}
		
		Map<String, Object> pageMap = new HashMap<String, Object>(); 
		pageMap.put("prev", prev);
		pageMap.put("next", next);
		pageMap.put("endPageBtnNo", endPageBtnNo);
		pageMap.put("startPageBtnNo", startPageBtnNo);
		pageMap.put("boardList", boardList);
		
		return pageMap;
	}

	/* 게시판에 글 작성하는 서비스 */
	public int writeBoard(BoardVo vo) {
		return boardDao.insertBoard(vo);
	}

	/* 글 읽는 서비스 */
	@Transactional
	public BoardVo readBoard(int no) {
		//글 가져와서 
		BoardVo vo = boardDao.select(no);
		
		//hit 1 up 한 뒤에 vo리턴
		boardDao.updateHit(no);
		
		return vo;
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
	
	/* board 1000개 넣기 */
	public void insert1000() {
		boardDao.insert1000();
	}
}
