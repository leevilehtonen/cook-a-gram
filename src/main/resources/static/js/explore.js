jQuery(document).ready(function ($) {

    $("#follow-form").on("submit", function (event) {
        event.preventDefault();


        if (!$("#follow-btn").hasClass("disabled")) {
            $("#follow-btn").toggleClass("active");
            if ($("#follow-btn").hasClass("active")) {
                $("#follow-btn").text("Following");
            } else {
                $("#follow-btn").text("Follow");
            }
            followAjax();
            $("#follow-btn").addClass("disabled");
        }
    });
});

function likePost(target) {

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
        sendData(target);
        like_btn.addClass("disabled");
    }
}

function sendData(target) {

    var data = {};
    data["target"] = target;

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
