<#import "parts/page.ftlh" as c>
<@c.page>
<div>
    <h1>Hello everyone!</h1>
    <form method="post" action="/main">
        <button type="submit">Main page</button>
    </form>
    <#--<form method="post" action="/login">
        <button type="submit"></button>
    </form>-->
</div>
</@c.page>