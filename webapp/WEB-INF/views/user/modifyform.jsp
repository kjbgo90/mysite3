<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
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
				<div id="user">
					<h2>유저수정</h2>
					
					<form class="form-box" method="post" action="${pageContext.request.contextPath }/user/modify">
						<input id="no" type="hidden" name="no" value="${modiUser.no}">
						<div class="form-group">
							<label class="block-label" for="name">이름</label>
							<input id="name" type="text" name="name" value="${modiUser.name }" >
						</div>
						
						<div class="form-group">
							<label class="block-label" for="email">이메일</label>
							<p><strong>${modiUser.email }</strong></p>
						</div>
						<div class="form-group">
							<label class="block-label" for="password">패스워드</label>
							<input id="password" type="password" name="password"  value="" >
						</div>
						<fieldset>
							<legend>성별</legend>
							<c:choose>
								<c:when test="${modiUser.gender eq 'female'}">
									<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
									<label>남</label> <input type="radio" name="gender" value="male" >
								</c:when>
		
								<c:otherwise> 	
									<label>여</label> <input type="radio" name="gender" value="female" >
									<label>남</label> <input type="radio" name="gender" value="male" checked="checked">
								</c:otherwise>
							</c:choose>
						</fieldset>
						
						<input type="submit" value="수정완료">
						
					</form>
					
				</div><!-- /user -->
			</div><!-- /c_box -->
		</div><!-- /content -->
			
		<!-- /footer -->
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
	</div><!-- /container -->
</body>
</html>

