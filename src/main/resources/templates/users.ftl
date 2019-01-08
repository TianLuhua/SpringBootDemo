<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>FreeMarker模版</title>
</head>
<body>
<br><br>需要显示所有list的记录:<br><br>
<#list  users as user>
    用户id：${user.id} --- 用户名:${user.name} --- 用户密码:${user.password}<br><br>
</#list>
</body>
</html>