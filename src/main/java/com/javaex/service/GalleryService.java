package com.javaex.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.GalleryDao;
import com.javaex.util.S3Util;
import com.javaex.vo.FileVo;

@Service
public class GalleryService {
	
	@Autowired
	private GalleryDao galleryDao;
	
	@Autowired
	private S3Util s3util;

	public List<FileVo> getList() {
		return galleryDao.selectList();
	}

	public FileVo insertImg(MultipartFile file, FileVo fileVo) {
		String path = "gallery";
		String bucketName = "com.javaex.kjbbb.upload";
		
		//오리지널 파일명
		String orgName = file.getOriginalFilename();
		
		//확장자
		String exName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		
		//저장할 파일명
		String saveName = System.currentTimeMillis() + UUID.randomUUID().toString() + exName;

		//파일패스
		String filePath = s3util.getFileURL(bucketName, path, saveName);

		//파일사이즈
		long fileSize = file.getSize();
		
		fileVo.setOrgName(orgName);
		fileVo.setSaveName(saveName);
		fileVo.setFilePath(filePath);
		fileVo.setFileSize(fileSize);
		
		//aws s3에 파일 저장 
		s3util.fileUpload(bucketName + "/" + path, file, saveName);

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
