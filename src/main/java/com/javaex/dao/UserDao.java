package com.javaex.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;

	public int insert(UserVo vo) {
		return sqlSession.insert("user.insertUser", vo);
	}
	
	public UserVo select(String email, String password) {
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("email", email);
		userMap.put("password", password);
		UserVo vo = sqlSession.selectOne("user.selectUser", userMap);
		return vo;
	}

	public UserVo selectModi(UserVo vo) {
		return sqlSession.selectOne("user.selectModi", vo);
	}

	public int updateUser(UserVo vo) {
		return sqlSession.update("user.update", vo);
	}
}
