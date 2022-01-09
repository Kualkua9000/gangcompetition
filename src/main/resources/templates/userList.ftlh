<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>There's start page of the Gang Competition application.</title>
</head>
<body>
<#list members as member>
<div>Hello ${member.name}!</div>
<ul>
    <li>${member}</li>
    </#list>
</ul>
</body>
</html>