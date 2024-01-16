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
<form method="post" action="/invoice/update">
<input  name="id" type="hidden" value="${id}"><br>
<%--suppress HtmlFormInputWithoutLabel --%>
    <input  name="title" type="text" placeholder="title" value="${title}"><br>
<%--suppress HtmlFormInputWithoutLabel --%>
    <input  name="date" type="date" placeholder="date" value="${date}"><br>
<%--suppress HtmlFormInputWithoutLabel --%>
    <textarea  name="description">${description}</textarea><br>
<%--suppress HtmlFormInputWithoutLabel --%>
    <select name="category">
    <option ${category == "Resor till och från jobbet" ? "selected":""}>Resor till och från jobbet</option>
    <option ${category == "Lunchmöten" ? "selected":""}>Lunchmöten</option>
    <option ${category == "Övertidsarbete" ? "selected":""}>Övertidsarbete</option>
    <option ${category == "Övrigt" ? "selected":""}>Övrigt</option>
</select><br>
<%--suppress HtmlFormInputWithoutLabel --%>
    <input name="price" type="number" placeholder="price" value="${price}"><br>
<button name="Update">Save</button>
<button formaction="/invoice/remove" name="Remove">Remove</button>
</form>
</body>
</html>
