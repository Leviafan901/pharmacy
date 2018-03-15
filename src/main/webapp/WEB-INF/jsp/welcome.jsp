<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <c:url var="login_url" value="/login"/>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>

<div id="wrapper">
    <div class="user-icon"></div>
    <div class="pass-icon"></div>
	
<form name="login-form" class="login-form" action="${login_url}" method="POST">
    <div class="header">
		<h1>Авторизация</h1>
		<span>Введите ваши регистрационные данные для входа в ваш личный кабинет.</span>
    </div>
    <div class="content">
    <input name="username" type="text" class="input username" value="login" onfocus="this.value=''" />
		<input name="password" type="password" class="input password" value="password" onfocus="this.value=''" />
    </div>
    <div class="footer">
		<input type="submit" name="submit" value="ВОЙТИ" class="button"/>
    </div>
</form>
</div>
<div class="gradient"></div>
</body>
</html>