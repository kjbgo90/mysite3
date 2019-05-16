<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link href="${pageContext.request.contextPath }/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath }/assets/css/mysite.css" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath }/assets/css/gallery.css" rel="stylesheet" type="text/css">
		
	<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/assets/bootstrap/js/bootstrap.min.js"></script>
	<title>Mysite</title>
</head>
<body>
	<div id="container">
		<!-- header -->
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
	
		<!-- navigation -->
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		
		<div id="content">
			<div id="c_box">
				<div id="gallery">
					<h2>갤러리</h2>
					<c:if test="${not empty authUser}">
						<input id="btnImgPop" class="btn btn-primary" type="button" value="이미지등록">
					</c:if>
					<c:if test="${empty authUser}">
						<input id="loginPlz" class="btn btn-primary" type="button" value="이미지등록">
					</c:if>
					<ul id="imgOut">
						<c:forEach items="${list}" var="vo">
							<li class="view" data-no="${vo.no }">
								<div>
									<img src ="${vo.filePath }">
								</div>
							</li>
						</c:forEach>
					</ul>
				</div><!-- /gallery -->
			</div><!-- /c_box -->
		</div><!-- /content -->
			
		<!-- footer -->	
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
	</div><!-- /container -->
	
	
	
	
	<!-- 이미지등록 팝업(모달)창 -->
	<div class="modal fade" id="insertPop">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">이미지등록</h4>
				</div>
				<!--<form method="post" action="${pageContext.request.contextPath }/gallery/upload" enctype="multipart/form-data">-->
					<div class="modal-body">
						<div class="formgroup">
							<label>코멘트작성</label><br>
							<input type="text" name="comments" id="comments"><br>	
						</div>
						<div class="formgroup">
							<label>이미지선택</label><br>	
							<input type="file" name="file" value="" id="file"> <br>	
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
						<button type="submit" class="btn btn-primary" id="btnImgAdd">등록</button>
					</div>
				<!--</form>-->
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	
	
	<!-- 이미지보기 팝업(모달)창 -->
	<div class="modal fade" id="viewPop">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">이미지보기</h4>
				</div>
				<div class="modal-body">
					
					<div class="formgroup" id="imgDraw">
						
					</div>
					
					<div class="formgroup" id="commWrite">
						<label>코멘트</label><br>
						
					</div>
					<input type="hidden" name="del_no" value="">
					<input type="hidden" name="del_user_no" value="">
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
					<c:if test="${not empty authUser}">
						<button type="button" class="btn btn-danger" id="btnDel" data-dismiss="modal">삭제</button>
					</c:if>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
</body>

<script type="text/javascript">
	var $this;
	/* 이미지등록 팝업(모달)창 띄우기*/ 
	$("#btnImgPop").on("click", function() {
	    $("#insertPop").modal();
	});
	$("#loginPlz").on("click", function() {
		alert("로그인을 해주세요");
		window.location.href = "${pageContext.request.contextPath }/user/loginform";
	});
	
	/* 이미지보기 팝업(모달)창 띄우기*/ 
	$("#imgOut").on("click", ".view", function() {
		$this = $(this);
		var no = $this.data("no");
		$("#viewPop").modal();
		
		$.ajax({
			
			url : "${pageContext.request.contextPath }/gallery/view",		
			type : "post",
			/* contentType : "application/json", */
			data : {no: no},

			dataType : "json",
			success : function(fileVo){
				/*성공시 처리해야될 코드 작성*/
				$("#imgDraw").empty();
				$("#commWrite").empty();
				$("[name=del_no]").val("");
				$("[name=del_user_no]").val("");
				
				$("#imgDraw").html("<img src =" + fileVo.filePath + ">");
				$("#commWrite").html("<label>코멘트</label><br>" + fileVo.comments);
				$("[name=del_no]").val(fileVo.no);
				$("[name=del_user_no]").val(fileVo.user_no);
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
		
	});
	
	//이미지 보기창에서 삭제버튼을 클릭한 경우
	$("#btnDel").on("click", function(){
		var no = $("[name=del_no]").val();
		var user_no = $("[name=del_user_no]").val();
		
		$.ajax({
			url : "${pageContext.request.contextPath }/gallery/delete",		
			type : "post",
			/*contentType : "application/json", */
			data : {no: no, user_no: user_no},

			dataType : "json",
			success : function(result){
				/*성공시 처리해야될 코드 작성*/
				if(result == true){
					$this.remove();
				}
				else{
					alert("등록한 사람만 삭제 가능..");
				}
				
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});
	/* 등록버튼 클릭 시 새로운 갤러리 등록 */
	$("#btnImgAdd").on("click", function(){
		var formData = new FormData();
		formData.append("comments", $("#comments").val());
		formData.append("file", $("#file")[0].files[0]);
		
		$.ajax({
			url : "${pageContext.request.contextPath }/gallery/insert",		
			type : "post",
			data : formData,
			processData: false,
			contentType: false,
			
			success : function(result){
				/*성공시 처리해야될 코드 작성*/
				render(result);
				
				$("#insertPop").modal("hide");
				$("#comments").val("");
				$("#file").val("");
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	});
	
	function render(vo){
		var str = "";
		
		str += "<li class='view' data-no=" + vo.no + ">"
		str += "	<div>";
		str += "		<img src =" + vo.filePath + ">";
		str += "	</div>";
		str += "</li>";
		
		$("#imgOut").append(str);
		
	};

</script>

</html>