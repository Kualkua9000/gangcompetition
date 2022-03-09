<#import "parts/common.ftlh" as c>
<@c.page>
    <div class="container">
        <#if Session.SPRING_SECURITY_CONTEXT.authentication.principal??>
            <h1>Hello ${Session.SPRING_SECURITY_CONTEXT.authentication.principal.getUsername()}!</h1>
        </#if>
        <h3 class="h3">Userlist:</h3>
        <#list userList as users>
            <ul>
                <li class="form-control">
                    ${users.username}
                </li>
            </ul>
        </#list>
        <div>
            <form action="/main" method="get">
                <button class="btn-primary" type="submit">Main page</button>
            </form>
        </div>
    </div>
</@c.page>