package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaex.dao.GuestBookDao;
import com.javaex.vo.GuestBookVo;

@Service
public class GuestBookService {

	@Autowired
	private GuestBookDao guestbookDao;

	public List<GuestBookVo> getList() {
		return guestbookDao.getList();
	}

	public int delete(GuestBookVo vo) {
		return guestbookDao.delete(vo);
	}

	public int insert(GuestBookVo vo) {
		return guestbookDao.insert(vo);
	}

	@Transactional
	public GuestBookVo insertGuest(GuestBookVo vo) {
		guestbookDao.insert(vo);
		return guestbookDao.selectRecent();
	}

	public List<GuestBookVo> getList(int page, int delCnt, int insertCnt) {
		int contentCnt = 5;
		
		int end = page * contentCnt;
		int start = end - (contentCnt - 1);
		
		end = end - delCnt + insertCnt;
		start = start - delCnt + insertCnt;
		
		return guestbookDao.getListByPage(start, end);
	}
	
}
