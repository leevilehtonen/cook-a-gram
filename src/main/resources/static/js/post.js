$(document).ready(function () {
    function previewImage(event) {

        $('#preview').attr('src', URL.createObjectURL(event.target.files[0]));
    }

    $("#inputFile").on("change", previewImage);

});

$('#inputTags').tagsinput({
    tagClass: 'h6 badge badge-pill badge-info',
    trimValue: true,
    maxChars: 24,
    maxTags: 32,
    cancelConfirmKeysOnEmpty: false
});





