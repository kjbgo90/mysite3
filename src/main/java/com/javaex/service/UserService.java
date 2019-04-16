package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	/* 회원가입 */
	public int join(UserVo vo) {
		return userDao.insert(vo);
	}

	/* 로그인(map) */
	public UserVo login(String email, String password) {
		return userDao.select(email, password);
	}

	/* 회원정보 수정 - 정보가져오기 */
	public UserVo modiFormSel(UserVo vo) { 
		return userDao.selectModi(vo);
	}

	/* 회원정보 수정 */
	public UserVo modify(UserVo vo) {
		int result = userDao.updateUser(vo);
		if(result == 1)
			return userDao.selectModi(vo);
		else 
			return null;
	}
	
	/* 이메일 체크 */
	public boolean emailCheck(String email) {
		UserVo vo = userDao.selectEmail(email);
		//데이터가 없으면 true --> 가입할 수 있음
		if(vo == null)
			return true;
		else 
			return false;
	}
}
