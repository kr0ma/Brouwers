<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='security' uri='http://www.springframework.org/security/tags'%>

<nav>
	<ul>
		<li><a href="<c:url value='/'/>">&#8962;</a></li>
		<li><a href="#">Brouwers</a>
			<ul>
				<security:authorize url="/brouwers">
					<li><a href="<c:url value='/brouwers'/>">Lijst</a></li>
				</security:authorize>
				<security:authorize url="/brouwers/alfabet">
					<li><a href="<c:url value='/brouwers/alfabet'/>">Alfabet</a></li>
				</security:authorize>
				<security:authorize url="/brouwers/beginnaam">
					<li><a href="<c:url value='/brouwers/beginnaam'/>">Beginnaam</a></li>
				</security:authorize>
				<security:authorize url="/brouwers/toevoegen">
					<li><a href="<c:url value='/brouwers/toevoegen'/>">Toevoegen</a></li>
				</security:authorize>				
			</ul>
		</li>
		<c:if test='${pageContext.response.locale.language != "nl"}'>
			<c:url value='' var='nederlandsURL'>
				<c:param name='locale' value='nl_be' />
			</c:url>
			<li><a href='${nederlandsURL}'>Nederlands</a></li>
		</c:if>
		<c:if test='${pageContext.response.locale.language != "en"}'>
			<c:url value='' var='engelsURL'>
				<c:param name='locale' value='en_us' />
			</c:url>
			<li><a href='${engelsURL}'>Engels</a></li>
		</c:if>
		<security:authorize access='isAnonymous()'>
			<li><a href="<c:url value='/login'/>">Aanmelden</a></li>
		</security:authorize>
		<security:authorize access="isAuthenticated ()">
		<li>
			<form method='post' action='<c:url value="/logout"/>' id='logoutform'>
				<input type='submit' value='<security:authentication property="name"/> afmelden' id='logoutbutton'>
				<security:csrfInput />
			</form>
		</li>
		</security:authorize>
	</ul>
</nav>
