package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.GalleryDao;
import com.javaex.vo.FileVo;

@Service
public class GalleryService {
	
	@Autowired
	private GalleryDao galleryDao;

	public List<FileVo> getList() {
		return galleryDao.selectList();
	}

	public FileVo insertImg(MultipartFile file, FileVo fileVo) {
		String saveDir = "/upload";
		
		//오리지널 파일명
		String orgName = file.getOriginalFilename();
		
		//확장자
		String exName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		
		//저장할 파일명
		String saveName = System.currentTimeMillis() + UUID.randomUUID().toString() + exName;

		//파일패스
		String filePath = saveDir + "/" + saveName;

		//파일사이즈
		long fileSize = file.getSize();
		
		fileVo.setOrgName(orgName);
		fileVo.setSaveName(saveName);
		fileVo.setFilePath(filePath);
		fileVo.setFileSize(fileSize);
		
		//서버에 파일 복사
		try {
			byte[] fileData = file.getBytes();
			OutputStream out = new FileOutputStream(saveDir + "/" + saveName);
			BufferedOutputStream bout = new BufferedOutputStream(out);
					
			bout.write(fileData);
					
			if(bout != null) {
				bout.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		//데이터베이스에 파일 저장
		galleryDao.insertImg(fileVo);
		
		
		return galleryDao.selectImg(fileVo.getNo());
	}

	public FileVo getImg(int no) {
		return galleryDao.selectImg(no);
	}

	public int delete(int no) {
		return galleryDao.delete(no);
	}

	

}
