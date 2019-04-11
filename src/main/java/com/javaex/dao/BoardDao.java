package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;

	public List<BoardVo> selectList(int startRnum, int endRnum, String kwd) {
		
		Map<String, Object> selMap = new HashMap<String, Object>();
		selMap.put("start", startRnum);
		selMap.put("end", endRnum);
		selMap.put("kwd", kwd);
		
		return sqlSession.selectList("board.selectlist", selMap);
	}

	public int insertBoard(BoardVo vo) {
		return sqlSession.insert("board.insert", vo);
	}

	public int updateHit(int no) {
		return sqlSession.update("board.updatehit", no);
	}

	public BoardVo select(int no) {
		return sqlSession.selectOne("board.selectboard", no);
	}

	public int update(BoardVo vo) {
		return sqlSession.update("board.update", vo);
	}

	public int delete(int no) {
		return sqlSession.delete("board.delete", no);
	}
	
	public int totalCount(String kwd) {
		return sqlSession.selectOne("board.totalCount", kwd);
	}
	
	public void insert1000() {
		BoardVo vo = new BoardVo();
		vo.setUser_no(1);
		
		for(int i = 1; i <= 70; i++) {
			vo.setTitle(i + "검색 글" );
			vo.setContent(i + "검색 글 내용");
			
			sqlSession.insert("board.insert", vo);
		}
	}
}
