package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
}
