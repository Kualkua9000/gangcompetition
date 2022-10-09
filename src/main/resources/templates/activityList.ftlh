<#import "parts/common.ftlh" as c>
<@c.page>
    <div class="container">
        <#if Session.SPRING_SECURITY_CONTEXT.authentication.principal??>
            <h1>Hello ${Session.SPRING_SECURITY_CONTEXT.authentication.principal.getUsername()}!</h1>
        </#if>
        <h3 class="h3">Activity list:</h3>
        ${activityList}
        <#--<#list activityList as activities>
            <ul>
                <li class="form-control">
                    ${activities}
                </li>
            </ul>
        </#list>-->
        <br>
        <br>
        <br>
        <div>
            <form action="/main" method="get">
                <button class="btn-primary" type="submit">Main page</button>
            </form>
        </div>
    </div>
</@c.page>