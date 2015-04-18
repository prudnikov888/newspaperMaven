<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<form action="editWriteNews.form">
    <table>
        <tr>
            <td><input type="hidden" name="newsId" value="${newsToEdit.newsId}"></td>
        </tr>
        <tr>
            <td>Название категории</td>
            <td><input type="text" name="categoryName" value="${newsToEdit.category.categoryName}" size="40"></td>
        </tr>
        <tr>
        <tr>
            <td>Название новости:</td>
            <td><input type="text" name="title" value="${newsToEdit.title}" size="40"></td>
        </tr>
        <tr>
            <td>Аннотация к новости:</td>
            <td><input type="text" name="title4ann" value="${newsToEdit.title4ann}" size="40"></td>
        </tr>
        <tr>
            <td>Автор новости:</td>
            <td><input type="text" name="author" value="${newsToEdit.author}" size="40"></td>
        </tr>
        <tr>
            <td>Основной текст новости:</td>
            <td><input type="text" name="mainText" value="${newsToEdit.mainText}" size="40"></td>
        </tr>
        <tr>
            <td>Дата публикации:</td>
            <td><input type="text" name="postDay" value="${newsToEdit.postDay}" size="40"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Save"></td>
        </tr>
    </table>
</form>
</body>
</html>