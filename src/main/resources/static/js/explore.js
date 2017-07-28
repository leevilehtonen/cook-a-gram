jQuery(document).ready(function ($) {

    $(".like-form").on("submit", function (event) {
        event.preventDefault();
        var target = $(this).find("input[name=target]").val();
        var data = $(this).serialize();
        likePost(target, data);
    });
});

function likePost(target, data) {

    var like_btn = $("#like_btn_" + target);
    var like_count = $("#like_count_" + target);
    if (!like_btn.hasClass("disabled")) {
        if (like_btn.hasClass("active-btn")) {
            var count = parseInt(like_count.text());
            count--;
            like_count.text(count);
        } else {
            var count = parseInt(like_count.text());
            count++;
            like_count.text(count);
        }
        like_btn.toggleClass("active-btn");
        like_btn.addClass("disabled");
        sendLike(target, data);
    }
}

function sendLike(target, data) {
    $.ajax({
        type: "POST",
        url: "/like",
        data: data,
        success: function (e) {
            $("#like_btn_" + target).removeClass("disabled");
        },
        error: function (e) {
            $("#like_btn_" + target).removeClass("disabled");
        },
    });
}
