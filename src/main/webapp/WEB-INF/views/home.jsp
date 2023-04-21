<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head lang="ko">
    <title>Home</title>
    <script src="https://kit.fontawesome.com/2e1441b09f.js" crossorigin="anonymous"></script>
    <script src="/js/jquery-3.6.3.min.js" ></script>
    <link rel="stylesheet" href="/css/home.css">
</head>
<body>
    <sec:authentication property="principal.member" var="loginUser" />

    <div>
        <div class="guide">
            <div class="guide_btn home_btn">
                <i class="fa-solid fa-house"></i>
                <span>홈</span>
            </div>
            <div class="guide_btn create_btn">
                <i class="fa-regular fa-square-plus"></i>
                <span>만들기</span>
            </div>
            <div class="guide_btn logout_btn">
                <i class="fa-solid fa-right-from-bracket"></i>
                <span>로그아웃</span>
                <form id="logout" action="/user/logout" method="post" hidden="hidden"></form>
            </div>
        </div>

        <div class="posts">
            <c:forEach items="${posts}" var="post">
                <div class="post">
                    <div class="post_top">
                        <div class="writer_profile">
                            <img class="profile_img" src="${post.memberAvatar}" />
                            <div class="profile_name">${post.memberName}</div>
                        </div>
                        <c:if test="${loginUser.memberEmail eq post.memberEmail}">
                        <div>
                            <button class="post_btn modify_btn" onclick="modifyModalOpenBtn(${post.postNo})">수정</button>
                            <button class="post_btn delete_btn" onclick="onClickDelBtn(${post.postNo})">삭제</button>
                        </div>
                        </c:if>
                    </div>
                    <div class="post_img_container">
                        <img class="post_img" src="${post.imageSrc}" />
                    </div>
                    <div class="post_icons">
                        <c:choose>
                            <c:when test="${post.liked}">
                                <i class="fa-solid fa-heart post_heart${post.postNo}" onclick="toggleLiked(${post.postNo}, this, true)"></i>
                            </c:when>
                            <c:otherwise>
                                <i class="fa-regular fa-heart post_heart${post.postNo}" onclick="toggleLiked(${post.postNo}, this, true)"></i>
                            </c:otherwise>
                        </c:choose>
                        <i class="fa-regular fa-comment-dots comment_icon" onclick="getPostAndComments(${post.postNo})"></i>
                    </div>
                    <div class="liked_container">
                        좋아요 <span class="likedCount${post.postNo}">${post.likedCount}개</span>
                    </div>
                    <div>
                        <span style="font-weight: bold">${post.memberName}</span>
                        <span>${post.postContent}</span>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div id="upload_modal" class="modal">
            <div class="modal_body">
                <div class="img_container">
                    <div class="select_img">
                        <button class="select_btn">이미지 선택</button>
                    </div>
                    <input type="file" id="selected_img" hidden accept="image/gif, image/jpeg, image/jpg, image/png" />
                    <img class="upload_img img" id="upload_img" src="#" alt="upload_img" hidden >
                </div>
                <div class="modal_content">
                    <div class="modal_content_top">
                        <img id="loginUser_img" class="writer_img" src="${loginUser.memberAvatar}" />
                        <div id="loginUser_name" class="writer_name">${loginUser.memberName}</div>
                    </div>
                    <div class="modal_content_body">
                        <textarea id="post_content" placeholder="문구입력..."></textarea>
                    </div>
                    <div class="modal_content_bottom">
                        <button class="upload_btn">공유</button>
                    </div>
                </div>
            </div>
        </div>

        <div id="comment_modal" class="modal">
            <div class="modal_body">
                <div class="img_container">
                    <img id="comment_modal_img" class="img" src="#" >
                </div>
                <div class="modal_content">
                    <div class="writer modal_content_top">
                        <img id="writer_img" class="writer_img" src="#" />
                        <div id="writer_name" class="writer_name"></div>
                        <div id="writer_content" class="writer_content"></div>
                    </div>
                    <div class="comments"></div>
                    <div class="comment_icons">
                        <i class="fa-regular fa-heart comment_heart"></i>
                        <i class="fa-regular fa-comment-dots comment_content_focus"></i>
                    </div>
                    <div class="liked">
                        좋아요 <span class="likedCount"></span>
                    </div>
                    <div class="comment_container">
                        <input class="comment_content" placeholder="댓글달기" />
                        <button class="comment_btn">게시</button>
                    </div>
                </div>
            </div>
        </div>

        <div id="modify_modal" class="modal">
            <div class="modal_body">
                <div class="img_container">
                    <img id="modify_modal_img" class="img" src="#" alt="post img" >
                </div>
                <div class="modal_content">
                    <div class="modal_content_top">
                        <img id="modify_writer_img" class="writer_img" src="#" />
                        <div id="modify_writer_name" class="writer_name"></div>
                    </div>
                    <div class="modal_content_body">
                        <textarea id="modify_content" placeholder="문구입력..."></textarea>
                    </div>
                    <div class="modal_content_bottom">
                        <button id="modify_btn">공유</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="/js/home.js"></script>
</body>
</html>
