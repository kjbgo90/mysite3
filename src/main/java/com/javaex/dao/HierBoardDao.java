package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.HierBoardVo;

@Repository
public class HierBoardDao {
	
	@Autowired
	private SqlSession sqlSession;

	public List<HierBoardVo> selectList(int startRnum, int endRnum, String kwd) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", startRnum);
		map.put("end", endRnum);
		map.put("kwd", kwd);
		
		return sqlSession.selectList("hierboard.selectList", map);
	}

	public int totalCount(String kwd) {
		return sqlSession.selectOne("hierboard.selectCount", kwd);
	}

	public int insertBoard(HierBoardVo vo) {
		return sqlSession.insert("hierboard.insertBoard", vo);
	}

	public HierBoardVo selectBoard(int no) {
		return sqlSession.selectOne("hierboard.selectBoard", no);
	}

	public int updateHit(int no) {
		return sqlSession.update("hierboard.updateHit", no);
	}

	public int updateOrderNo(int groupNo, int order) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group", groupNo);
		map.put("order", order);
		
		return sqlSession.update("hierboard.updateOrderNo", map);
	}

	public int insertReply(HierBoardVo vo) {
		return sqlSession.insert("hierboard.insertReply", vo);
	}

	public int updateBoard(HierBoardVo vo) {
		return sqlSession.update("hierboard.updateBoard", vo);
	}

	public int updateState(int no) {
		return sqlSession.update("hierboard.updateState", no);
	}

}
