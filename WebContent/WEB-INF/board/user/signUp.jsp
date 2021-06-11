<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<c:set var="root" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function createFrom(obj) {
	if (obj.id.value == "") {
		alert("아이디를 반드시 입력하세요.");
		obj.id.focus();
		return false;
	}

		if (obj.password.value == "") {
			alert("비밀번호를 반드시 입력하세요.");
			obj.password.focus();
			return false;
		}

		if (obj.password.value.length < 7) {
			alert("비밀번호는 7글자 이상으로 만들어주세요.");
			obj.password.focus();
			return false;
		}

		if (obj.passwordCheck.value == "") {
			alert("비밀번호 확인란에 입력해주세요.");
			obj.passwordCheck.focus();
			return false;
		}

		if (obj.password.value != obj.passwordCheck.value) {
			alert("입력하신 비밀번호가 같지 않습니다.");
			obj.passwordCheck.focus();
			return false;
		}

		if (obj.name.value == "") {
			alert("이름을 반드시 입력하세요.");
			obj.name.focus();
			return false;
		}

		if (obj.email.value == "") {
			alert("이메일을 입력하세요.");
			obj.email.focus();
			return false;
		}

		var check = false;
		for (var i = 0; i < obj.mailing.length; i++) {
			if (obj.mailing[i].checked == true)
				check = true;
		}

		if (obj.mailing.value == "") {
			alert("메일수신 여부를 체크해주세요.");
			obj.mailing.focus();
			return false;
		}
		check = false;
		var str = "";
		for (var i = 0; i < obj.interest.length; i++) {
			if (obj.interest[i].checked == true) {
				str += obj.interest[i].value + ",";
			}
		}
	}
	function idCheck(obj, root) {
		alert(obj.id.value);

		if (obj.id.value == "") {
			alert("아이디를 반드시 입력하세요.");
			obj.id.focus();
			return false;
		} else {
			let url = root + "/user/idCheck?id=" + obj.id.value;
			window.open(url, "", "width=400, height=200");
		}
	}
</script>
</head>
<body>
	<section>

		<form id="joinform" name="joinform" action="" method="post"
			onsubmit="return createFrom(this)">

			<h4 style="text-align: center;">회원가입(*필수입력사항입니다.)</h4>
			<div class="menu" style="border-bottom-width: 0px;">


				<div id="id">아이디</div>
				<span>* <input type="text" class="checkInfo" name="id"
					size="12" />
					<button type="button" onclick="idCheck(joinform, '${root}')">아이디
						중복</button>
				</span>
			</div>

			<div class="menu " style="border-bottom-width: 0px;">
				<div id="id">비밀번호</div>
				<span>* <input type="password" class="checkInfo"
					name="password" size="12" />
				</span>
			</div>

			<div class="menu " style="border-bottom-width: 0px;">
				<div id="id">비밀번호확인</div>
				<span>* <input type="password" class="checkInfo"
					name="passwordCheck" size="12" />
				</span>
			</div>

			<div class="menu " style="border-bottom-width: 0px;">
				<div id="id">이름</div>
				<span>* <input type="text" class="checkInfo" name="name"
					size="12" />
				</span>
			</div>


			<div class="menu" style="border-bottom-width: 0px;">
				<div id="id" style="margin-left: 10px,">이메일</div>
				<span> <input type="email" name="email" size="25" />
				</span>
			</div>
			<div class="menu" style="text-align: center;">
				<span> <input type="submit" value="가입" /> <input
					type="reset" value="취소" />
				</span>
			</div>
		</form>
	</section>


	</form>

	</section>
</body>
</html>