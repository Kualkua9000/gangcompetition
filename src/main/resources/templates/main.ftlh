<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>

    <#if Session.SPRING_SECURITY_CONTEXT.authentication.principal??>
        <h1>Hello ${Session.SPRING_SECURITY_CONTEXT.authentication.principal.getUsername()}</#if>!</h1>

    <div>
        <form method="post" action="/main">
            <p>"Please select the activity: "</p>
            <select name="activityType">
                <option value="running" selected>RUNNING</option>
                <option value="walking">WALKING</option>
                <option value="bicycle">BICYCLE</option>
            </select>
            <input type="text" name="activityDistance" placeholder="Please enter the activity distance">
            <input type="text" name="activityTime" placeholder="Please enter the activity time">
            <input type="text" name="activityPace" placeholder="Please enter the activity pace">
            <input type="date" name="activityDate" placeholder="Please enter the date">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Save</button>
        </form>
    </div>

    <#if activityList??>
        <div>
            <#list activityList as activity>
                <ul>
                    <li>${activity}</li>
                </ul>
            </#list>
        </div>
    </#if>
    <div>
        <br>
        <form action="/" method="get">
            <button type="submit">Hello page</button>
        </form>
    </div>
    <br><br>
    <div>
        <@l.logout />
    </div>
</@c.page>