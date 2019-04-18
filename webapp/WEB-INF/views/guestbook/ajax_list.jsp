<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link href="${pageContext.request.contextPath }/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath }/assets/css/guestbook.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/assets/bootstrap/js/bootstrap.min.js"></script>
	
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
				<div id="guestbook">
					<h2>방명록</h2>
						<table border="1" width="510">
							<tr>
								<c:choose>
									<c:when test="${empty authUser}">
										<!-- 로그인 전 -->
										<td>이름</td>
										<td><input type="text" name="name" value=""></td>
									</c:when>
									<c:otherwise>
										<!-- 로그인 후 -->
										<td>이름</td>
										<td><input type="text" name="name" value="${authUser.name}"></td>
									</c:otherwise>
								</c:choose>
								<td>비밀번호</td>
								<td><input type="password" name="password" value=""></td>
							</tr>	
							<tr>
								<td colspan="4"><textarea rows="10" cols="60" name="content"></textarea></td>
							</tr>	
							<tr>
								<td colspan="4" style="text-align:right;"><input id="btn-insert" type="submit" value="확인"></td>
							</tr>
						</table><br>
					
					<div id="gbList"></div>
					
				</div><!-- /guestbook -->
			</div><!-- /c_box -->
		</div><!-- /content -->
			
		<!-- /footer -->
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
	</div><!-- /container -->
	
	<!-- 삭제팝업(모달)창 -->
	<div class="modal fade" id="del-pop">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">방명록삭제</h4>
				</div>
				<div class="modal-body">
					<label>비밀번호</label>
					<input type="password" name="modalPassword" id="modalPassword"><br>	
					<input type="hidden" name="modalNo" id="modalNo"> <br>	
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
					<button type="button" class="btn btn-danger" id="btn_del" data-dismiss="modal">삭제</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
</body>

<script type="text/javascript">
	var page = 1;
	var delCnt = 0;
	var insertCnt = 0;
	
	$(document).ready(function(){
		$.ajax({
			url : "${pageContext.request.contextPath }/api/gb/list",		
			type : "post",
			/*contentType : "application/json", */
			data : {page: page, delCnt: delCnt, insertCnt: insertCnt},

			dataType : "json",
			success : function(guestbookList){
				/*성공시 처리해야될 코드 작성*/
				console.log(guestbookList);
				for(var i = 0; i < guestbookList.length; i++){
					render(guestbookList[i], "down");
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});
	
	/* 삭제버튼 클릭할 때 */
	$("#gbList").on("click", ".btnDel", function(){
		console.log("삭제버튼클릭!");
		var $this = $(this);
		var no = $this.data("no");

		$("#del-pop").modal();
		$("#modalNo").val(no);
		$("#modalPassword").val("");
	});
	
	/* 모달창에서 삭제버튼을 클릭한 경우 */
	$("#btn_del").on("click", function(){
		var no = $("#modalNo").val();
		var password = $("#modalPassword").val();
		var delDiv = "#div" + no;
		
		console.log("모달 삭제 클릭! " + no + " " + password);
		
		$.ajax({
			
			url : "${pageContext.request.contextPath }/api/gb/delete",		
			type : "post",
			/* contentType : "application/json", */
			data : {no: no, password: password},

			dataType : "json",
			success : function(result){
				/*성공시 처리해야될 코드 작성*/
				console.log(result);
				if(result == true){
					$(delDiv).remove();
					delCnt++;
				} else {
					alert("비밀번호가 틀려요");
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});
	
	/* 방명록을 작성하기 위해 확인버튼을 클릭한 경우 */
	$("#btn-insert").on("click", function(){
		console.log("확인 버튼 클릭!");
		var name = $("[name=name]").val();
		var password = $("[name=password]").val();
		var content = $("[name=content]").val();
		
		$.ajax({
		
			url : "${pageContext.request.contextPath }/api/gb/insert",		
			type : "post",
			/* contentType : "application/json", */
			data : {name: name, password: password, content: content},

			dataType : "json",
			success : function(GuestBookVo){
				/*성공시 처리해야될 코드 작성*/
				console.log(GuestBookVo);
				render(GuestBookVo, "up");
				insertCnt++;
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
		
		//등록 후 데이터 삭제해줌
		$("[name=name]").val("");
		$("[name=password]").val("");
		$("[name=content]").val("");
	});
	
	/* 맨 밑으로 내려 간 경우 */
	$(window).scroll(function() {
	    if ($(window).scrollTop() == $(document).height() - $(window).height()) {
	    	console.log("아래");
	    	page++;
	    	console.log(page);
	    	$.ajax({
				url : "${pageContext.request.contextPath }/api/gb/list",		
				type : "post",
				/* contentType : "application/json", */
				data : {page: page, delCnt: delCnt, insertCnt: insertCnt},

				dataType : "json",
				success : function(guestbookList){
					/*성공시 처리해야될 코드 작성*/
					console.log(guestbookList);
					for(var i = 0; i < guestbookList.length; i++){
						render(guestbookList[i], "down");
					}
				},
				error : function(XHR, status, error) {
					console.error(status + " : " + error);
				}
			});
	    }
	});

	
	function render(vo, updown){
		var str = "";
		str += "<div id='div" + vo.no + "'>"
		str += "	<table border='1' width='510'>";
		str += "		<tr>";
		str += "			<td>" + vo.no + "</td>";
		str += "			<td>" + vo.name + "</td>";
		str += "			<td>" + vo.reg_date + "</td>";
		str += "			<td><input class='btnDel' type='button' value='삭제' data-no=" + vo.no + "></td>";
		str += "		</tr>";
		str += "		<tr>";
		str += "			<td colspan='4'><pre>" + vo.content + "</pre></td>";
		str += "		</tr>";
		str += "	</table><br>";
		str += "</div>"	
		if(updown == "up"){
			$("#gbList").prepend(str);
		} else if(updown == "down"){
			$("#gbList").append(str);
		} else{
			console.log("updown 오류");
		}
	};
</script>

</html>

