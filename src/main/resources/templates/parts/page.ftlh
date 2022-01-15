<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8"/>
        <title>Gang competition</title>
    </head>
    <body>
    <#nested>
    <div>
        <form action="/logout" method="post">
            <input type="submit" value="Sign Out"/>
        </form>
    </div>
    </body>
    </html>
</#macro>