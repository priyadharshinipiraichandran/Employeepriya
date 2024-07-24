<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Rawwire- Login</title>

<!-- Font Icon -->
<link rel="stylesheet" href="fonts/material-icon/css/material-design-iconic-font.min.css">

<!-- Main css -->
<link rel="stylesheet" href="css/style.css">

<style>
/* Ensure Flexbox alignment */
.container {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.signin-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
}

.signin-image, .signin-form {
    flex: 1;
}

.signin-form {
    padding: 20px;
}

.form-group {
    display: flex;
    align-items: center;
    margin-bottom: 20px;
}

.form-group label {
    margin-right: 10px;
}

.form-group input[type="checkbox"] {
    margin-right: 5px;
}

.social-login {
    text-align: center;
}

.socials {
    display: flex;
    justify-content: center;
    list-style: none;
    padding: 0;
}

.socials li {
    margin: 0 10px;
}
</style>

</head>
<body>

<div class="main">
    <!-- Sing in  Form -->
    <section class="sign-in">
        <div class="container">
            <div class="signin-content">
                <div class="signin-image">
                    <figure>
                        <img src="images/emplap1.png" alt="sing up image">
                    </figure>
                    <a href="register.jsp" class="signup-image-link">Create an account</a>
                    <a href="empadminlogin.jsp" class="signup-image-link">Admin login</a>
                </div>

                <div class="signin-form">
                    <h2 class="form-title">Sign in</h2>
                    <form method="post" action="login" class="register-form" id="login-form">
                        <div class="form-group">
                            <label for="phno"><i class="zmdi zmdi-account material-icons-name"></i></label>
                            <input type="text" name="phno" id="phno" placeholder="Enter your Phone number" required>
                        </div>
                        <div class="form-group">
                            <label for="password"><i class="zmdi zmdi-lock"></i></label>
                            <input type="password" name="password" id="password" placeholder="Password" required>
                        </div>
                 
                        <div class="form-group form-button">
                            <input type="submit" name="signin" id="signin" class="form-submit" value="Log in">
                        </div>
                    </form>
                    
                </div>
            </div>
        </div>
    </section>
</div>

<!-- JS -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="js/main.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<script type="text/javascript">
    var status = document.getElementById("status").value;
    if (status == "failed") {
        swal("Oops!", "Wrong Username or Password", "error");
    }
</script>
</body>
<!-- This templates was made by Colorlib (https://colorlib.com) -->
</html>
