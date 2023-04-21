const body = $("body");

$(".logout_btn").click(() => {
    $("#logout").submit();
})

const uploadModal = $("#upload_modal");
const openUploadPopup = $(".create_btn");

openUploadPopup.click(() => {
    uploadModal.toggleClass('show');

    if (uploadModal.hasClass('show')) {
        body.css("overflow", "hidden");
    }
})

uploadModal.click((event) => {
    if (event.target === uploadModal.get(0)) {
        uploadModal.toggleClass('show');
    }

    if (!uploadModal.hasClass('show')) {
        body.css("overflow", "auto");
        $('#upload_img').attr('src', '#');
        $('#upload_img').hide();
        $('.select_img').show();
        $('#selected_img').val('');
        $('#post_content').val('');
    }
});

$(".select_btn").click(() => {
    $('#selected_img').click();
})

function readURL(input) {
    if (input.files && input.files[0]) {

        const imageType = input.files[0].type;

        if (!(imageType === 'image/jpeg' || imageType === 'image/jpg' ||
            imageType === 'image/png' || imageType === 'image/gif')) {
            alert('이미지 파일만 선택할 수 있습니다.');
            return;
        }

        const reader = new FileReader();

        reader.onload = function (e) {
            $('#upload_img').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);

        $('#upload_img').show();
        $('.select_img').hide();
    }
}

$("#selected_img").change(function(){
    readURL(this);
});

$(".upload_btn").click(() => {
    const img = $('#selected_img')[0];
    const content = $('#post_content').val();

    if(img.files.length < 1) {
        alert('파일을 선택해주세요');
        return;
    }

    const data = new FormData();
    data.append('file', img.files[0]);
    data.append('postContent', content);

    $.ajax({
        type: "POST",
        url: "/post/upload",
        processData: false,
        contentType: false,
        data: data,
        success(res) {
            alert(res);
            location.reload();
        },
        error(jqXHR, textStatus, errorThrown) {
            alert(textStatus);
        }
    })
})

const commentModal = $("#comment_modal");
const openCommentPopup = $('.comment_icon');

openCommentPopup.click(() => {
    commentModal.toggleClass('show');

    if (commentModal.hasClass('show')) {
        body.css("overflow", "hidden");
    }
})

commentModal.click((event) => {
    if (event.target === commentModal.get(0)) {
        commentModal.toggleClass('show');
    }

    if (!commentModal.hasClass('show')) {
        body.css("overflow", "auto");
    }
})

const modifyModal = $("#modify_modal");
function modifyModalOpenBtn(postNo) {
    $('#modify_btn').off();
    modifyModal.toggleClass('show');

    if (modifyModal.hasClass('show')) {
        body.css("overflow", "hidden");
    }

    $.ajax({
        type: "GET",
        url: `/post/${postNo}`,
        success(res) {
            $('#modify_modal_img').attr("src", res.imageSrc);
            $('#modify_writer_img').attr("src", res.memberAvatar);
            $('#modify_writer_name').text(res.memberName);
            $('#modify_content').val(res.postContent);
        },
        error(jqXHR, textStatus, errorThrown) {
            alert(textStatus);
        }
    })

    $('#modify_btn').click(() => {
        onClickModifyBtn(postNo);
    });
}

function onClickModifyBtn(postNo) {
    const postContent = $('#modify_content').val();
    $.ajax({
        type: "POST",
        url: "/post/modify",
        data: { postNo, postContent },
        success(res) {
            alert("수정 완료");
            modifyModal.click();
        },
        error(jqXHR, textStatus, errorThrown) {
            alert(textStatus);
        }
    })
}

modifyModal.click((event) => {
    if (event.target === modifyModal.get(0)) {
        modifyModal.toggleClass('show');
    }

    if (!modifyModal.hasClass('show')) {
        body.css("overflow", "auto");
    }
})

function getPostAndComments(postNo) {
    getPost(postNo);
    getComments(postNo);

    const commentBtn = $('.comment_btn');
    const commentContent = $('.comment_content');
    const commentHeart = $('.comment_heart');

    commentBtn.off();
    commentContent.off();
    commentHeart.off();

    commentBtn.click(() => { saveComment(postNo) });

    commentContent.keypress((e) => {
        if(e.keyCode === 13) {
            saveComment(postNo);
        }
    })

    commentHeart.click(function () {
        toggleLiked(postNo, $(this), false);
    });
}

function getPost(postNo) {
    $.ajax({
        type: "GET",
        url: `/post/${postNo}`,
        success(res) {
            const liked = res.liked;
            const likedCount = `${res.likedCount}개`;

            console.log(likedCount);

            $('#comment_modal_img').attr("src", res.imageSrc);
            $('#writer_img').attr("src", res.memberAvatar);
            $('#writer_name').text(res.memberName);
            $('#writer_content').text(res.postContent);
            $(`.likedCount${postNo}`).text(likedCount);
            toggleHeart($(`.post_heart${postNo}`), liked);

            $('.likedCount').text(likedCount);
            toggleHeart($('.comment_heart'), liked);
        },
        error(jqXHR, textStatus, errorThrown) {
            alert(textStatus);
        }
    })
}

function getComments(postNo) {
    $.ajax({
        type: "GET",
        url: `/comment/${postNo}`,
        success(res) {
            $(".comment").remove();

            res.forEach((v) => {
                const comment = "<div class='comment modal_content_top'>" +
                    `<img id='commenter_img' class='writer_img' src="${v.avatar}" />` +
                    `<div id='commenter_name' class='writer_name'>${v.name}</div>` +
                    `<div id='commenter_comment' class='writer_content'>${v.content}</div>` +
                    "</div>"
                $(".comments").append(comment);
            });

            const commentScroll = document.querySelector('.comments');
            commentScroll.scrollTo({ top: commentScroll.scrollHeight });
        },
        error(jqXHR, textStatus, errorThrown) {
            alert(textStatus);
        }
    })
}

$('.comment_content_focus').click(() => $('.comment_content').focus());

function saveComment(postNo) {

    const commentContent = $(".comment_content").val();

    if(commentContent === null || commentContent === "") {
        alert("댓글을 입력하세요");
        return;
    }

    $.ajax({
        type: "POST",
        url: `/comment/${postNo}`,
        data: { commentContent },
        success(res) {
            $(".comment_content").val('');

            getComments(postNo);
        },
        error(jqXHR, textStatus, errorThrown) {
            alert(textStatus);
        }
    })
}

function toggleLiked(postNo, info, inPost) {
    $.ajax({
        type: "POST",
        url: "/liked",
        data: { postNo },
        success(res) {
            const heart = $(info);
            const liked = res.liked;
            const likedCount = res.likedCount+"개";

            if(inPost === true) {
                if (liked === true) {
                    heart.removeClass('fa-regular');
                    heart.addClass('fa-solid');
                } else {
                    heart.addClass('fa-regular');
                    heart.removeClass('fa-solid');
                }
            } else {
                const postHeart = $(`.post_heart${postNo}`);

                if (liked === true) {
                    heart.removeClass('fa-regular');
                    heart.addClass('fa-solid');

                    postHeart.removeClass('fa-regular');
                    postHeart.addClass('fa-solid');
                } else {
                    heart.addClass('fa-regular');
                    heart.removeClass('fa-solid');

                    postHeart.addClass('fa-regular');
                    postHeart.removeClass('fa-solid');
                }
                $(`.likedCount`).text(likedCount);
            }
            $(`.likedCount${postNo}`).text(likedCount);
        },
        error(jqXHR, textStatus, errorThrown) {
            alert(textStatus);
        }
    })
}

function toggleHeart(info, result) {
    if (result == true) {
        info.removeClass('fa-regular');
        info.addClass('fa-solid');
    } else {
        info.addClass('fa-regular');
        info.removeClass('fa-solid');
    }
}

function onClickDelBtn(postNo) {
    const result = confirm("게시물을 삭제하시겠습니까?");
    if (result) {
        $.ajax({
            type: "DELETE",
            url: `/post/${postNo}`,
            success(res) {
                alert("게시물 삭제완료");
                location.reload();
            },
            error(jqXHR, textStatus, errorThrown) {
                alert(textStatus);
            }
        })
    }
}
