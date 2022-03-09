<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <div class="h2">Login page</div>
    <@l.login "/login" />
    <form class="btn" method="get" action="/registration">
        <button type="submit" class="btn btn-primary">Add new user</button>
    </form>
</@c.page>
