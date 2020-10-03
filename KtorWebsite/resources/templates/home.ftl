<html>
<head>
 <link rel="stylesheet" href="/static/styles.css" />
</head>
<body>
    <#if username??>
    <h1>Welcome ${username}</h1>
    <#else>
    <h1>Please Login</h1>
    </#if>
</body>
</html>