<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
    <header>
        <div>
            <h3>Самые свежие новости. Обновление каждые 5 минут.</h3>
            <a align="right">&nbsp<a href="showNews.form?locale=RU">RU</a>/<a href="showNews.form?locale=EN">EN</a>&nbsp</a>
            <a align="right"><sec:authorize ifAnyGranted="admin">
                Добрый день, вы зашли как администратор!
                <a href="/j_spring_security_logout">Выйти из системы</a>
            </sec:authorize></a>
        </div>
    </header>
</html>



