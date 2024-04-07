<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Erreur</title>
</head>
<body>
    <h2>Une erreur est survenue</h2>
    <p style="color: red;">Message d'erreur : ${errorMessage}</p>
    <a href="<%=request.getContextPath()%>/users">Retour à la liste des clients</a>
</body>
</html>
