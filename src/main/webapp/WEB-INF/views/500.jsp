<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF8" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>500服务器内部错误</title>
    <!-- Bootstrap core CSS -->
    <link href="/static_page/css/bootstrap.css" rel="stylesheet">
    <!-- FONT AWESOME CSS -->
    <link href="/static_page/css/font-awesome.min.css" rel="stylesheet"/>
    <!--GOOGLE FONT -->
    <link href='http://fonts.googleapis.com/css?family=Nova+Flat' rel='stylesheet' type='text/css'>
    <!-- custom CSS here -->
    <link href="/static_page/css/style.css" rel="stylesheet"/>
</head>
<body>


<div class="container">

    <div class="row pad-top text-center">
        <div class="col-md-6 col-md-offset-3 text-center">
            <h1> 服务器开小差了，请再等等吧... </h1>
            <h5 class="error-info"> 点击下面的按钮返回首页 </h5>
            <span id="error-link"></span>
            <h2>! ERROR DECETED !</h2>
        </div>
    </div>

    <div class="row text-center">
        <div class="col-md-8 col-md-offset-2">

            <h3><i class="fa fa-lightbulb-o fa-5x"></i></h3>
            <a href="/index" class="btn btn-primary">返回首页</a>
            <br/><br/>
            <p>网站信息</p>
        </div>
    </div>

</div>
<!-- /.container -->


<!--Core JavaScript file  -->
<script src="/static_page/js/jquery-1.10.2.js"></script>
<!--bootstrap JavaScript file  -->
<script src="/static_page/js/bootstrap.js"></script>
<!--Count Number JavaScript file  -->
<script src="/static_page/js/countUp.js"></script>
</body>
<script>
    (function ($) {
        "use strict";
        var mainApp = {
            main_fun: function () {

                var count = new countUp("error-link", 10, 500, 0, 4); //CHANGE 404 TO THE ERROR VALUE AS YOU WANT
                window.onload = function () {
                    count.start();
                };

            },
            initialization: function () {
                mainApp.main_fun();
            }
        };
        // Initializing ///
        $(document).ready(function () {
            mainApp.main_fun();
        });
    }(jQuery));
</script>
</html>
