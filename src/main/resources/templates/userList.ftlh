<#import "parts/page.ftlh" as c>
<@c.page>
<body>
    <#list members as member>
        <div>Hello ${member.name}!</div>
        <ul>
            <li>${member}</li>
        </ul>
    </#list>
<div>
    <form action="" method="post">
        <button type="submit">Main page</button>
    </form>
</div>
</body>
</@c.page>