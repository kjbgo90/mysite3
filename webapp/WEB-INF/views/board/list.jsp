<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
	<title>Mysite</title>
</head>
<body>
	<div id="container">
		<!-- header -->
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
	
		<!-- /navigation -->
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		
		<div id="content">
			<div id="c_box">
				<div id="board">
					<h2>게시판-리스트</h2>
					
					<form action="${pageContext.request.contextPath }/board" method="get">
						<input type="hidden" name="page" value="1" >
						<input type="text" id="kwd" name="kwd" value="${param.kwd }" >
						<input type="submit" value="찾기">
					</form>
					
					<table class="tbl-ex">
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>글쓴이</th>
							<th>조회수</th>
							<th>작성일</th>
							<th>&nbsp;</th>
						</tr>
						<c:forEach items="${pageMap.boardList }" var="vo" >				
						<tr>
							<td>${vo.no }</td>
							<td><a href="${pageContext.request.contextPath }/board/read/${vo.no }?page=${param.page}&kwd=${param.kwd}">${vo.title }</a></td>
							<td>${vo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.reg_date }</td>
							<c:choose>
								<c:when test="${vo.user_no eq authUser.no}">
									<td>
										<a href="${pageContext.request.contextPath }/board/deletes/${vo.no}?user_no=${vo.user_no}" class="del">삭제</a>
									</td>
								</c:when>
								<c:otherwise>
									<td>
										&nbsp;
									</td>
								</c:otherwise>
							</c:choose>
						</tr>
						</c:forEach>
					</table>
					<div class="pager">
						<ul>
							<c:if test="${pageMap.prev eq true }">
								<li><a href="${pageContext.request.contextPath }/board?page=${pageMap.startPageBtnNo - 1 }&kwd=${param.kwd}">◀</a></li>
							</c:if>
							
							<c:forEach var ="i" begin="${pageMap.startPageBtnNo }" end="${pageMap.endPageBtnNo }" step="1">
								<c:choose>
									<c:when test="${param.page eq i }">
										<li class="selected">${i}</li>
									</c:when>
									<c:otherwise>
										<li><a href="${pageContext.request.contextPath }/board?page=${i }&kwd=${param.kwd}">${i}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>

							<c:if test="${pageMap.next eq true }">
								<li><a href="${pageContext.request.contextPath }/board?page=${pageMap.endPageBtnNo + 1 }&kwd=${param.kwd}">▶</a></li>
							</c:if>
						</ul>
					</div>				
					<c:choose>
						<c:when test="${empty authUser}">
							<!-- 로그인 전 -->
						</c:when>
						<c:otherwise>
							<!-- 로그인 후 -->
							<div class="bottom">
								<a href="${pageContext.request.contextPath }/board/writeform" id="new-book">글쓰기</a>
							</div>
						</c:otherwise>
					</c:choose>
					
				</div><!-- /board -->
			</div><!-- /c_box -->
		</div><!-- /content -->
			
		<!-- /footer -->
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
	</div><!-- /container -->
</body>
</html>

