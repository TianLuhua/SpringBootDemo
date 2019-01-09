<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>FreeMarker模版</title>
</head>
<body>
<br><br>需要显示所有list的记录:<br><br>

<#--这里blooean值需要用（）包裹起来，不然会报错-->
<#if (users?size > 0) >
    <#list  users as user>
        用户id：${user.id} --- 用户名:${user.name} --- 用户密码:${user.password}<br><br>
    </#list>
</#if>
<br><br>

<#--变量定义:<#assign Mouse="Mouse"/>,只是在当前模版中有效-->
<#assign Mouse="Mouse"/>

<#--FreeMarker对字符串的简单操作-->
<#assign testFreeMarker=" <h1> test FreeMarker</h1>" />
${testFreeMarker?lower_case}
${testFreeMarker?upper_case}

<#--判空(??):Mouse为空返回false，否则返回ture-->
<#if Mouse??>
    Mouse Not Null !!<br><br>
<#else>
    Mouse is Null !!<br><br>
</#if>

<#--list循环：可以嵌套if语句，breake-->
<#assign siji=["Spring","Summer","Autumn","Winter"]>
<#list siji as jijie>
    ${jijie}
    <br>
</#list>
<#--内置变量(格式：变量名_index)：index-->
<#list siji as jijie>
    ${jijie},index:${jijie_index}
    <br>
</#list>

<#--内置变量(格式：变量名_has_index)：has_next-->
<#list siji as jijie>
    ${jijie},
    <#if (jijie_has_next)>
        还有下一个元素！
    <#else >
        没有下一个元素了！
    </#if>
    <br>
</#list>
<br><br>

<#--宏定义-->
<#--单标签调用宏：-->
<@testMacro "111","222",333/>
<@testMacro "111","222",333/>
<#--macro-->
<#macro testMacro parm1 parm2="defaultParm2" parm3=3>
    Test Macro ,${parm1},${parm2},${parm3} <br><br>
</#macro>
<#macro repeat count>
    <#list 1..count as x>
        <#nested x,x/2,x==count>
    <#--return:遇到return语句，宏会直接返回，不在执行下面的语句-->
        <#if x==20>
            <#return >
        </#if>
    </#list>
</#macro>
<#--nested:能将宏中的值传出至宏调用处,调用宏处可以自定义变量名字进行接收-->
<#--双标签调用宏（FreeMarker中是不能输出blooean值的，会报错）：-->
<@repeat 50;content,content_half,isLast>
    ${content},
    ${content_half},
    <#if (isLast)>
        到最后一个数了！
    <#else >
        还有没有到最后一个数！
    </#if>
    <br>
</@repeat>
<br><br>
</body>
</html>