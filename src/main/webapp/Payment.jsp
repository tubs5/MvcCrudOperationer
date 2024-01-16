<%--suppress ALL --%>
<%--
  User: tobia
  Date: 2024-01-15
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
</head>
<body>
<form method="post" action="/invoice/create">
    <input name="title" type="text" placeholder="title" required><br>
    <input name="date" type="date" placeholder="date" required><br>
    <textarea name="description"></textarea><br>
    <select name="category">
        <option>Resor till och från jobbet</option>
        <option>Lunchmöten</option>
        <option>Övertidsarbete</option>
        <option>Övrigt</option>
    </select><br>
    <input name="price" type="number" placeholder="price" required><br>
    <button name="save">Save</button>
</form>
</body>
</html>
