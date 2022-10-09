<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
Add new user:
    <#if message??>
        <br>
        ${message}
    </#if>
<@l.login "/registration" />
</@c.page>
