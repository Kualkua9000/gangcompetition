<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>

    <div class="container">
        <#if Session.SPRING_SECURITY_CONTEXT.authentication.principal??>
            <h1>Hello ${Session.SPRING_SECURITY_CONTEXT.authentication.principal.getUsername()}!</h1>
        </#if>

        <div>
            <form method="post" action="/main">
                <h2 class="h2">Please select the activity:</h2>
                <select class="form-select" name="activityType">
                    <option value="running" selected>RUNNING</option>
                    <option value="walking">WALKING</option>
                    <option value="bicycle">BICYCLE</option>
                </select>
                <input class="form-control" type="text" name="activityDistance" placeholder="Please enter the activity distance">
                <input class="form-control" type="text" name="activityTime" placeholder="Please enter the activity time">
                <input class="form-control" type="text" name="activityPace" placeholder="Please enter the activity pace">
                <input class="form-control" type="date" name="activityDate" placeholder="Please enter the date">
                <input class="form-control" type="hidden" name="_csrf" value="${_csrf.token}"/>
                <br>
                <button class="btn-primary" type="submit">Save</button>
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
                <button class="btn-secondary" type="submit">Hello page</button>
            </form>
        </div>
        <div>
            <br>
            <form action="/user" method="get">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button class="btn-secondary" type="submit">User list</button>
            </form>
        </div>
        <br><br>
        <div>
            <form action="/user/stravaAuth" method="get">
            <input type="submit" value="Strava Authentification"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </form>
        </div>
        <br><br>
        <div>
            <form action="/user/activities" method="get">
                <input type="submit" value="Strava get User activities"/>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </form>
        </div>
        <br><br>
        <div>
            <@l.logout />
        </div>
    </div>
</@c.page>