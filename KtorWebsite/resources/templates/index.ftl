<#-- @ftlvariable name="data" type="com.firstwebsite.IndexData" -->
<head>
    <link rel="stylesheet" href="/static/styles.css"/>
</head>
<html>
    <body>
        <h1>This is a list of numbers</h1>
        <ul>
        <#list data.items as item>
            <li>${item}</li>
        </#list>
        </ul>
    </body>
</html>
