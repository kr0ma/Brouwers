<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='v' uri='http://vdab.be/tags'%>
<%@taglib prefix='spring' uri='http://www.springframework.org/tags'%>
<!doctype html>
<html lang='nl'>
<head>
<v:head title='Brouwers op alfabet' />
</head>
<body>
	<v:menu />
	<h1>Brouwers op alfabet</h1>
	<ul class="zonderbolletjes">
		<c:forEach var="letter" items="${alfabet}">
			<spring:url value='/brouwers/alfabet/{letter}' var='letterURL'>
				<spring:param name='letter' value='${letter}' />
			</spring:url>
			<li><a href='${letterURL}'>${letter}</a></li>
		</c:forEach>
	</ul>
	<c:if test="${not empty brouwers}">
		<ul>
			<c:forEach var="brouwer" items="${brouwers}">
				<li>${brouwer.naam} (${brouwer.adres.gemeente})</li>
			</c:forEach>
		</ul>
	</c:if>
</body>
</html>