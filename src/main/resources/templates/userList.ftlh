<#import "parts/common.ftlh" as c>
<@c.page>
    <div>
        <#if Session.SPRING_SECURITY_CONTEXT.authentication.principal??>
            <h1>Hello ${Session.SPRING_SECURITY_CONTEXT.authentication.principal.getUsername()!}</h1>
        </#if>
        <#list userList as users>
            <ul>
                <li>
                    <tr>

                    </tr>
                </li>
            </ul>
        </#list>
        <div>
            <form action="/main" method="get">
                <button type="submit">Main page</button>
            </form>
        </div>
    </div>
</@c.page>