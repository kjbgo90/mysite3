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
					
					<form action="${pageContext.request.contextPath }/hierboard" method="get">
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
						<c:forEach items="${listMap.boardList }" var="vo" >				
						<tr>
							<td>${vo.no }</td>
							<c:choose>
								<c:when test="${vo.state eq 0 }">
									<td style="text-align:left;">
										<a href="${pageContext.request.contextPath }/hierboard/read/${vo.no }?page=${param.page}&kwd=${param.kwd}"><c:forEach var ="i" begin="0" end="${vo.depth }">&nbsp;&nbsp;</c:forEach><c:if test="${vo.depth ne 0 }"><img src="/assets/images/reply.png"></c:if>${vo.title }</a>
									</td>
								</c:when>
								<c:otherwise>
									<td style="text-align:right; color:red;" >
										<a><strong>삭제된 글입니다.</strong></a>
									</td>
								</c:otherwise>
							</c:choose>
							
							<td>${vo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.reg_date }</td>
							<c:choose>
								<c:when test="${(vo.user_no eq authUser.no) and (vo.state eq 0)}">
									<td>
										<a href="${pageContext.request.contextPath }/hierboard/delete/${vo.no}?user_no=${vo.user_no}&page=${param.page}&kwd=${param.kwd}" class="del">삭제</a>
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
							<c:if test="${listMap.prev eq true }">
								<li><a href="${pageContext.request.contextPath }/hierboard?page=${listMap.startPageBtnNo - 1 }&kwd=${param.kwd}">◀</a></li>
							</c:if>
							
							<c:forEach var ="i" begin="${listMap.startPageBtnNo }" end="${listMap.endPageBtnNo }" step="1">
								<c:choose>
									<c:when test="${param.page eq i }">
										<li class="selected">${i}</li>
									</c:when>
									<c:otherwise>
										<li><a href="${pageContext.request.contextPath }/hierboard?page=${i }&kwd=${param.kwd}">${i}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>

							<c:if test="${listMap.next eq true }">
								<li><a href="${pageContext.request.contextPath }/hierboard?page=${listMap.endPageBtnNo + 1 }&kwd=${param.kwd}">▶</a></li>
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
								<a href="${pageContext.request.contextPath }/hierboard/writeform" id="new-book">글쓰기</a>
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

