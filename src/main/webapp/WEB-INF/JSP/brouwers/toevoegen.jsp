<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='v' uri='http://vdab.be/tags'%>
<%@taglib prefix='form' uri='http://www.springframework.org/tags/form'%>

<!doctype html>
<html lang='nl'>
<head>
<v:head title='Brouwers toevoegen' />
</head>
<body>
	<v:menu />
	<h1>Brouwers toevoegen</h1>
	<c:url value='/brouwers' var='url' />
	<form:form action='${url}' commandName='brouwer' id='toevoegform'>
		<jsp:include page="brouwerformfields.jsp"/>
		<input type='submit' value='Toevoegen' id='toevoegknop'>
	</form:form>
	<script>
		document.getElementById('toevoegform').onsubmit = function() {
			document.getElementById('toevoegknop').disabled = true;
		};
	</script>
</body>
</html>