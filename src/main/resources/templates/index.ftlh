<#import "parts/page.ftlh" as c>
<@c.page>
    <div>
        <form method="post" action="">
            <input type="text" name="name" placeholder="Please enter the name">
            <input type="text" name="email" placeholder="Please enter the e-mail">
            <button type="submit">Save</button>
        </form>
    </div>

    <div>
        <#list members as member>
            <ul>
                <li>${member}</li>
            </ul>
        </#list>
    </div>
</@c.page>