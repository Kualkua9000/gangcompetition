<#import "parts/common.ftlh" as c>
<@c.page>
    <div>
        <#list members as member>
            <div>Hello ${member.name}!</div>
            <ul>
                <li>${member}</li>
            </ul>
        </#list>
        <div>
            <form action="" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit">Main page</button>
            </form>
        </div>
    </div>
</@c.page>