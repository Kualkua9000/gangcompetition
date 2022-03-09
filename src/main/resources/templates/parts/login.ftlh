<#macro login path>

    <div>
        <form action="${path}" method="post">
            <div class="form-control"><label> User Name : <input type="text" name="username"/> </label></div>
            <div class="form-control"><label> Password: <input type="password" name="password"/> </label></div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <br>
            <div class="btn"><input type="submit" value="Sign In"/></div>
        </form>
    </div>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="submit" value="Sign Out"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</#macro>
