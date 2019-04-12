package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaex.dao.HierBoardDao;
import com.javaex.vo.HierBoardVo;

@Service
public class HierBoardService {

	@Autowired
	private HierBoardDao hbDao;

	/* list 가져오는 서비스 */
	public Map<String, Object> getList(int page, String kwd) {
		// 페이지당 보여지는 글의 갯수
		int listCnt = 10;

		// 음수 값이 들어왔을때 처리
		page = (page > 0) ? page : (page = 1);

		int startRnum = (page - 1) * listCnt;
		int endRnum = startRnum + listCnt;


		// 전체 글 갯수 구하는 dao실행
		int totalCount = hbDao.totalCount(kwd);
		
		// 리스트 가져오는 dao실행
		List<HierBoardVo> boardList = hbDao.selectList(startRnum, endRnum, kwd);
		
		// 페이지 당 버튼 갯수
		int pageBtnCount = 5;

		int endPageBtnNo = (int) Math.ceil(page / (double) pageBtnCount) * pageBtnCount;
		int startPageBtnNo = endPageBtnNo - (pageBtnCount - 1);
		boolean next = false;
		if (endPageBtnNo * listCnt < totalCount) {
			next = true;
		} else {
			endPageBtnNo = (int) Math.ceil(totalCount / (double) listCnt);
		}
		boolean prev = false;
		if (startPageBtnNo != 1) {
			prev = true;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("prev", prev);
		map.put("next", next);
		map.put("endPageBtnNo", endPageBtnNo);
		map.put("startPageBtnNo", startPageBtnNo);
		map.put("boardList", boardList);

		return map;
	}

	/* list에서 로그인 유저 일반 글 쓰는 서비스 */
	public int writeBoard(HierBoardVo vo) {
		return hbDao.insertBoard(vo);
	}

	/* 글을 읽어오고 해당 글의 hit를 1 증가 시켜주는 서비스 */
	@Transactional
	public HierBoardVo readBoard(int no) {
		//글을 가져오고
		HierBoardVo vo = hbDao.selectBoard(no);
		
		//해당 글의 조횟수를 1 증가 시킨다.
		int result = hbDao.updateHit(no);
		if (result == 1) //성공하면 vo를 실패하면 null을 리턴
			return vo;
		else 
			return null;
	}

	/* 리플 달아야 하는 글의 정보를 읽어오고 정보를 가지고 리플을 작성하는 서비스 */
	@Transactional
	public int replyWriteBoard(HierBoardVo vo) {
		//해당글의 정보를 가지고 온다.
		HierBoardVo boardVo = hbDao.selectBoard(vo.getNo());
		int groupNo = boardVo.getGroup_no();
		int targetOrderNo = boardVo.getOrder_no() + 1;
		
		//그룹 번호는 해당 그룹의 번호를 부여한다 
		vo.setGroup_no(groupNo);
		
		//depth는 해당 글의 depth보다 1 증가 시킨다
		vo.setDepth(boardVo.getDepth() + 1);
		
		//같은 그룹 안에서 targetOrderNo와 order_no가 같거나 큰 수들을 모두 1 증가시켜준 다음,(들어갈 자리 만들기)
		int result = hbDao.updateOrderNo(groupNo , targetOrderNo);
		System.out.println(result + "개update - replyWriteBoard Service");
		
		//order_no를 targetO_N으로 해준다
		vo.setOrder_no(targetOrderNo);
		
		//vo를 넘겨서 insert해준다 (user_no, title, content, group_no, order_no, depth)
		return hbDao.insertReply(vo);
	}

	/* 수정할 글의 정보를 가져옴 */
	public HierBoardVo modiGet(int no) {
		return hbDao.selectBoard(no);
	}

	/* 글을 수정하는 서비스 */
	public int modify(HierBoardVo vo) {
		return hbDao.updateBoard(vo);
	}

	/* 글을 삭제처리(update) 하는 서비스 */
	public int delete(int no) {
		return hbDao.updateState(no);
	}

}
