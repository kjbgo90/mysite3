package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestBookVo;

@Repository
public class GuestBookDao {
	
	@Autowired
	private SqlSession sqlsession;
	
	public List<GuestBookVo> getList(){
		List<GuestBookVo> list = sqlsession.selectList("guestbook.selectList");
		return list;
	}
	
	public int insert(GuestBookVo vo) {
		int result = sqlsession.insert("guestbook.insertGuest", vo);
		return result;
	}
	
	public int delete(GuestBookVo vo) {
		int result = sqlsession.delete("guestbook.deleteGuest", vo);
		return result;
	}

	public GuestBookVo selectRecent(int no) {
		return sqlsession.selectOne("guestbook.selectOne", no);
	}

	public List<GuestBookVo> getListByPage(int start, int end) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);
		
		return sqlsession.selectList("guestbook.selectListByPage", map);
	}
	
}
