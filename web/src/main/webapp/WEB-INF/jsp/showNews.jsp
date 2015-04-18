<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <sec:authorize ifAnyGranted="admin">
    <a href="addNews.form"><h3>Добавить новость</h3></a>
    </sec:authorize>
    <sec:authorize ifNotGranted="admin">
    <a href="logInPage.form"><h4>Войти в систему</h4></a>
    </sec:authorize>
<table border="1" cellspacing="0px">
    <tr>
        <td>
        </td>
        <td>
        </td>
        <td>

            <!--Pagination elements-->
            <table border="0" cellspacing="0px">
                <tr>
                    <td>
                        <a href="showNews.form?selectedPage=1&newsOnPage=${newsOnPage}&sortBy=${sortBy}" onclick="this.form.submit()"> [ 1 ] </a>
                    </td>
                    <td>
                        <a href="showNews.form?selectedPage=${selectedPage - 1}&newsOnPage=${newsOnPage}&sortBy=${sortBy}" onclick="this.form.submit()">[ Назад ]</a>
                    </td>
                    <td>
                        [ Текущая: <c:out value="${selectedPage}"/> ]
                    </td>
                    <td>
                        <a href="showNews.form?selectedPage=${selectedPage + 1}&newsOnPage=${newsOnPage}&sortBy=${sortBy}" onclick="this.form.submit()">[ Вперед ]</a>
                    </td>
                    <td>
                        <a href="showNews.form?selectedPage=${numberOfPages}&newsOnPage=${newsOnPage}&sortBy=${sortBy}" onclick="this.form.submit()">[ <c:out value="${numberOfPages}"/> ]</a>
                    </td>
                    <td>
                        <form action="showNews.form">
                            Новостей на странице:
                            <select size="1" name="newsOnPage" onchange="this.form.submit()">
                                <option selected value=${newsOnPage}><b><c:out value="${newsOnPage}"/></b></option>
                                <option value="5">5</option>
                                <option value="10">10</option>
                            </select>
                            Сортировать по:
                            <select size="1" name="sortBy" onchange="this.form.submit()">
                                <option selected value=${sortBy}><b><c:out value="${sortByFull}"/></b></option>
                                <option value="postDay">По дате</option>
                                <option  value="category">По категории</option>
                            </select>
                        </form>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
            </table>

        </td>
        <sec:authorize ifAnyGranted="admin">
        <td></td>
        <td></td>
        </sec:authorize>
    </tr>
    <tr>
        <td><spring:message code = "postDay"/></td>
        <td><spring:message code = "category"/></td>
        <td><spring:message code = "newsFeed"/></td>
        <sec:authorize ifAnyGranted="admin">
        <td></td>
        <td></td>
        </sec:authorize>
    </tr>
    <!--Showing list of news-->

        <c:forEach var="i" items="${newsList}">
            <tr>
                <td>
                    <b><c:out value="${i.postDay}"/>&nbsp</b>
                </td>
                <td>
                    <b><c:out value="${i.category.categoryName}"/>&nbsp</b>
                </td>
                <td>
                    <br><a href="showSingleNews.form?id_for_showing=${i.newsId}">
                    <c:out value="${i.title4ann}"/>
                </a><br><br>
                </td>
                <sec:authorize ifAnyGranted="admin">
                <td>
                        <br><a href="editNews.form?id_for_editing=${i.newsId}">Редактировать
                        новость</a><br><br>
                    </td>
                    <td>
                        <br><a href="delNews.form?id_for_deleting=${i.newsId}">Удалить
                        новость</a><br><br>
                    </td>
                </sec:authorize>
            </tr>
        </c:forEach>
</table>
</body>
</html>