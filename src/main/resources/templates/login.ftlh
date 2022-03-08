<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <div class="w-auto p-3">
        <h2 class="h2">Login page</h2>
        <@l.login "/login" />
        <a class="link-dark" href="/registration">Add new user</a>
    </div>
</@c.page>
