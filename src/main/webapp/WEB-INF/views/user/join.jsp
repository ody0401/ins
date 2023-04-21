<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
    <script src="/js/jquery-3.6.3.min.js"></script>
    <link rel="stylesheet" href="/css/member.css">
</head>
<body>
    <form:form modelAttribute="member" method="post" action="/user/join" class="signup_form">
        <div class="title">회원가입</div>
        <div class="input_container">
            <form:label path="memberName">이름</form:label>
            <form:input path="memberName" name="memberName" />
            <form:errors path="memberName" />
        </div>
        <div class="input_container">
            <form:label path="memberEmail">이메일</form:label>
            <form:input path="memberEmail" name="memberEmail" />
            <form:errors path="memberEmail" />
        </div>
        <div class="input_container">
            <form:label path="memberPw">비밀번호</form:label>
            <form:password path="memberPw" name="memberPw" />
            <form:errors path="memberPw" />
        </div>
        <div class="error"><c:out value="${err}" /></div>
        <div class="switch_btn"><a href="/user/login">로그인</a></div>
        <div class="btn_div"><form:button type="submit" class="submit_btn">확인</form:button></div>
        <div>
            <form:input path="memberAvatar" hidden="hidden" />
        </div>
    </form:form>

    <script>
        $("#member").submit(() => {
            $("#memberAvatar").val("https://loremflickr.com/320/240/cat?random="+getRandomInt(1,10000));
        });

        function getRandomInt(min, max) {
            min = Math.ceil(min);
            max = Math.floor(max);
            return Math.floor(Math.random() * (max - min)) + min; //최댓값은 제외, 최솟값은 포함
        }
    </script>
</body>
</html>