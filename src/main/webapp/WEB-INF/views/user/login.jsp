<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그인</title>
    <script src="/js/jquery-3.6.3.min.js" ></script>
    <link rel="stylesheet" href="/css/member.css" >
</head>
<body>
<form actions="/user/login" method="post" class="signup_form">
    <div class="title">로그인</div>
    <div class="input_container">
        <label for="memberEmail">이메일</label>
        <input id="memberEmail" name="memberEmail" />
    </div>
    <div class="input_container">
        <label for="memberPw">비밀번호</label>
        <input type="password" id="memberPw" name="memberPw" />
    </div>
    <div class="error">${err}</div>
    <div class="switch_btn"><a href="/user/join">회원가입</a></div>
    <div class="btn_div"><button type="submit" class="submit_btn">확인</button></div>
</form>
</body>
</html>