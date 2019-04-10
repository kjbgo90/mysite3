package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;

	public List<BoardVo> selectList() {
		return sqlSession.selectList("board.selectlist");
	}

	public int insertBoard(BoardVo vo) {
		return sqlSession.insert("board.insert", vo);
	}

	public void updateHit(int no) {
		sqlSession.update("board.updatehit", no);
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
}
