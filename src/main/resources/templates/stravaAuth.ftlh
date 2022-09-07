<#import "parts/common.ftlh" as c>
<@c.page>
    <div class="container">
        <div><#if Session.SPRING_SECURITY_CONTEXT.authentication.principal??>
                <h1>Hello ${Session.SPRING_SECURITY_CONTEXT.authentication.principal.getUsername()}!</h1>
            </#if>
        </div>
        <form method="get" action="/main">
            <button type="submit" class="btn btn-primary">Main page</button>
        </form>
    </div>
</@c.page>