<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix='v' uri='http://vdab.be/tags'%>
<%@taglib prefix='spring' uri='http://www.springframework.org/tags'%>
<!doctype html>
<html lang='nl'>
<head>
<v:head title='Alle Brouwers' />
</head>
<body>
	<v:menu />
	<h1>Alle Brouwers</h1>
	<c:if test="${not empty brouwers}">
		<table>
			<thead>
				<tr>
					<th>Nummer</th>
					<th>Naam</th>
					<th>Straat</th>
					<th>HuisNr</th>
					<th>Postcode</th>
					<th>Gemeente</th>
					<th>Omzet</th>
				</tr>
			</thead>
			<c:forEach var="brouwer" items="${brouwers}">
				<tr>
					<td class="rechts">${brouwer.id}</td>
					<td>${brouwer.naam}</td>
					<td>${brouwer.adres.straat}</td>
					<td>${brouwer.adres.huisNr}</td>
					<td>${brouwer.adres.postcode}</td>
					<spring:url value='/temperatuur/{plaats}' var="temperatuurURL">
						<spring:param name='plaats' value='${brouwer.adres.gemeente}' />
					</spring:url>
					<td>${brouwer.adres.gemeente}<a href='${temperatuurURL}'> temperatuur</a></td>
					<td class="rechts"><fmt:formatNumber value="${brouwer.omzet}" /></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>