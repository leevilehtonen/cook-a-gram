$(document).ready(function () {
    $(".form-signup").validate({
        rules: {
            email: {
                required: true,
                email: true
            },
            firstname: "required",
            lastname: "required",
            username: {
                required: true,
                minlength: 2
            },
            password: {
                required: true,
                minlength: 5
            }
        },
        messages: {
            email: "Please enter a valid email address",
            firstname: "Please enter your firstname",
            lastname: "Please enter your lastname",
            username: {
                required: "Please enter a username",
                minlength: "Your username must be at least 2 characters"
            },
            password: {
                required: "Please provide a password",
                minlength: "Your password must be at least 5 characters long"
            }

        }
    });
});