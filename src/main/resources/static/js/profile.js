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

function followAjax() {
    var data = {};
    data["target"] = targetId;

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
