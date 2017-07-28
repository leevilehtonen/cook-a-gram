var followUpdate = false;

jQuery(document).ready(function ($) {

    $("#follow-form").on("submit", function (event) {
        event.preventDefault();
        var data = $(this).serialize();
        followUser(data);
        followUpdate = true;
    });

    $(".follower-count").on("click", function (event) {
        if (followUpdate) {
            location.reload(true);
        }
        $('#modal-followers').modal({});
    });

    $(".following-count").on("click", function (event) {
        if (followUpdate) {
            location.reload(true);
        }
        $('#modal-followings').modal({});
    })
});

function followUser(data) {
    var followBtn = $("#follow-btn");
    var followerCount = $(".follower-count");
    if (!followBtn.hasClass("disabled")) {
        followBtn.toggleClass("active");
        if (followBtn.hasClass("active")) {
            followBtn.text("Following");
            var count = parseInt(followerCount.text().split(' ')[0]);
            var text = followerCount.text().split(' ')[1];
            count++;
            followerCount.text(count + ' ' + text);
        } else {
            followBtn.text("Follow");
            var count = parseInt(followerCount.text().split(' ')[0]);
            var text = followerCount.text().split(' ')[1];
            count--;
            followerCount.text(count + ' ' + text);
        }
        followBtn.addClass("disabled");
        sendData(data);
    }
}

function sendData(data) {

    $.ajax({
        type: "POST",
        url: "/relationship",
        data: data,
        success: function (e) {
            $("#follow-btn").removeClass("disabled");
        },
        error: function (e) {
            $("#follow-btn").removeClass("disabled");
        },

    });
}
