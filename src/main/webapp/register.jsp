<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Rawwire - Sign up</title>
<link rel="stylesheet" href="fonts/material-icon/css/material-design-iconic-font.min.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<input type="hidden" id="status" value="<%= request.getAttribute("status") %>">

<div class="main">
    <section class="signup">
        <div class="container">
            <div class="signup-content">
                <div class="signup-form">
                    <h2 class="form-title">Hello new Employee!!</h2>
                    <form method="post" action="employee" class="register-form" id="register-form" onsubmit="return validateForm()">
                        <div class="form-group">
                            <label for="phno"><i class="zmdi zmdi-account material-icons-name"></i></label>
                            <input type="text" name="phno" id="phno" placeholder="Enter Phone number" required />
                        </div>
                        <div class="form-group">
                            <label for="role"><i class="zmdi zmdi-lock-outline"></i></label>
                            <input type="text" name="role" id="role" placeholder="ADMIN/ASSOCIATE" required />
                        </div>
                        <div class="form-group">
                            <label for="username"><i class="zmdi zmdi-pin"></i></label>
                            <input type="text" name="username" id="username" placeholder="Enter Username" required />
                        </div>
                        <div class="form-group">
                            <label for="pass"><i class="zmdi zmdi-lock"></i></label>
                            <input type="password" name="password" id="pass" placeholder="Password" required />
                        </div>
                        <div class="form-group">
                            <label for="re_pass"><i class="zmdi zmdi-lock"></i></label>
                            <input type="password" name="confirm_password" id="re_pass" placeholder="Repeat your password" required />
                        </div>
                        <div class="form-group form-button">
                            <input type="submit" name="signup" id="signup" class="form-submit" value="Register" required />
                        </div>
                    </form>
                </div>
                <div class="signup-image">
                    <figure>
                        <img src="images/waving.png" alt="sign up image">
                    </figure>
                    <a href="login.jsp" class="signup-image-link">Already a member?</a>
                </div>
            </div>
        </div>
    </section>
</div>
<script src="vendor/jquery/jquery.min.js"></script>
<script src="js/main.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<link rel="stylesheet" href="alert/dist/sweetalert.css">

<script type="text/javascript">
    var status = document.getElementById("status").value;
    if (status == "success"){
        swal("Congrats", "Account Created Successfully", "success");
    }

    function validateForm() {
        var password = document.getElementById("pass").value;
        var confirmPassword = document.getElementById("re_pass").value;
        var regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

        if (!regex.test(password)) {
            alert("Password must contain at least one uppercase letter, one lowercase letter, one number, and one symbol.");
            return false;
        }
        if (password !== confirmPassword) {
            alert("Passwords do not match.");
            return false;
        }
        return true;
    }
</script>
</body>
</html>
