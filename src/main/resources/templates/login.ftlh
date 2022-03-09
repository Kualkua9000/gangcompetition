<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <div class="container">
        <br>
        <div class="h2">Login page</div>
        <br>
        <@l.login "/login" />
        <form class="btn" method="get" action="/registration">
            <button type="submit" class="btn btn-primary">Add new user</button>
        </form>
    </div>
</@c.page>
