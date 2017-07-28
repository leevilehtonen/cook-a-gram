function previewImage(event) {

    var img = document.createElement("img");
    var file = event.target.files[0];

    if (file == null || !file.type.match(/image.*/)) {
        clearInput();
        return;
    }
    img.src = window.URL.createObjectURL(event.target.files[0]);
    img.onload = function () {
        resizeAndDraw(img);
    }
}

function resizeAndDraw(img) {
    var MAX_WIDTH = 1000;
    var MAX_HEIGHT = 1000;
    var width = img.width;
    var height = img.height;

    if (width > height) {
        if (width > MAX_WIDTH) {
            height *= MAX_WIDTH / width;
            width = MAX_WIDTH;
        }
    } else {
        if (height > MAX_HEIGHT) {
            width *= MAX_HEIGHT / height;
            height = MAX_HEIGHT;
        }
    }
    var canvas = document.getElementById("preview-canvas");
    canvas.width = width;
    canvas.height = height;
    var ctx = canvas.getContext("2d");
    ctx.drawImage(img, 0, 0, width, height);
}

function clearInput() {
    var canvas = document.getElementById("preview-canvas");
    var ctx = canvas.getContext("2d");
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    canvas.width = 0;
    canvas.height = 0;
    $('.form-post')[0].reset();
}

function showError() {
    $('#error-dialog').show();
}

$(document).ready(function () {

    $(".form-post").on("submit", function (event) {
        event.preventDefault();
        var formData = new FormData();

        var csrfName = $(this).find("input[type=hidden]").attr("name");
        var csrfValue = $(this).find("input[type=hidden]").val();
        var tags = $(this).find("input[name=tags]").val();
        var canvas = document.getElementById("preview-canvas");

        formData.append(csrfName, csrfValue);
        formData.append("tags", tags);
        formData.append("file", canvas.toDataURL('image/jpeg', 0.8));
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState == XMLHttpRequest.DONE) {
                if (xhr.status == 200) {
                    window.location.href = xhr.responseURL;
                } else {
                    showError();
                    clearInput();
                }
            }
        };
        xhr.open("POST", "/posts");
        xhr.send(formData);


    });


    $("#inputFile").on("change", previewImage);

});

$('#inputTags').tagsinput({
    tagClass: 'h6 badge badge-pill badge-info',
    trimValue: true,
    maxChars: 24,
    maxTags: 32,
    cancelConfirmKeysOnEmpty: false
});