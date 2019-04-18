package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.FileVo;

@Repository
public class GalleryDao {
	
	@Autowired
	private SqlSession sqlSession;

	public List<FileVo> selectList() {
		return sqlSession.selectList("gallery.selectlist");
	}

	public int insertImg(FileVo fileVo) {
		return sqlSession.insert("gallery.insertImg", fileVo);
	}

	public FileVo selectImg(int no) {
		return sqlSession.selectOne("gallery.selectOne", no);
	}

	public int delete(int no) {
		return sqlSession.delete("gallery.delete", no);
	}

}
