<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>

    <#if Session.SPRING_SECURITY_CONTEXT.authentication.principal??>
        <h1>Hello ${Session.SPRING_SECURITY_CONTEXT.authentication.principal.getUsername()}</#if>!</h1>

    <br><br>
    <div>
        <form method="post" action="/main">
            <input type="text" name="name" placeholder="Please enter the name">
            <input type="text" name="email" placeholder="Please enter the e-mail">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Save</button>
        </form>
    </div>

    <#if members??>
        <div>
            <#list members as member>
                <ul>
                    <li>${member}</li>
                </ul>
            </#list>
        </div>
    </#if>
    <div>
        <br>
        <form action="/" method="get">
            <button type="submit">Main page</button>
        </form>
    </div>
    <br><br>
    <div>
        <@l.logout />
    </div>
</@c.page>