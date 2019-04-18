<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="navigation">
	<ul>
		<li><a href="${pageContext.request.contextPath }/main">MAIN</a></li>
		<li><a href="${pageContext.request.contextPath }/guestbook">방명록</a></li>
		<li><a href="${pageContext.request.contextPath }/board?page=1&kwd=">게시판</a></li>
		<li><a href="${pageContext.request.contextPath }/hierboard?page=1&kwd=">계층 게시판</a></li>
		<li><a href="${pageContext.request.contextPath }/guestbook/ajaxlist">ajax방명록</a></li>
		<li><a href="${pageContext.request.contextPath }/gallery">갤러리</a></li>
	</ul>
</div>