<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
    <meta charset="UTF-8">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script>
        $(function () {
            //页面加载完毕，清空页面
            $("#loginAct").val("");

            //页面加载时，文本框自动获取焦点
            $("#loginAct").focus();

            //为登录按钮绑定事件，执行操作
            $("#submitBtn").click(function () {
               login()
            })

            //绑定敲键盘事件
            $(window).keydown(function (event) {
                if(event.keyCode==13){
                    login()
                }
            })

        })

        //普通自定义方法一定要写在$function(){}外面
        function login() {
            //alert("登录操作")
            //验证账号密码不能为空
            //取得账号密码,将文本中的左右空格去掉，使用$.trim(文本)
            var loginAct = $("#loginAct").val();
            var loginPwd = $("#loginPwd").val();
            if (loginAct == "" || loginPwd == ""){
                $("#msg").html("账号密码不能为空！")
                //如果账号密码为空，则需要及时强制该方法
                return false;
            }

            //取得了用户填写的账号和密码，现在去数据库验证账号和密码
            $.ajax({
                url : "settings/user/login.do",
                data : {
                    "loginAct" : loginAct,
					"loginPwd" : loginPwd
                },
                type : "post",
                datatype : "json",
                success : function (data) {
                    /*

                    data
                    {"success":true/false,msg:"哪错了"}

                     */

                    //如果登录成功
					if (data.success){
						//跳转到工作台（欢迎页）
						window.location.href = "workbench/index.html";
					}else {
						$("#msg").html(data.msg);
					}
                }
            })
        }
    </script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2020&nbsp;武汉商贸职业学院</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.html" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" type="text" placeholder="用户名" id="loginAct">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" type="password" placeholder="密码" id="loginPwd">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						
							<span id="msg" style="color: red"></span>
						
					</div>
					<button type="submit" id="submitBtn" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>