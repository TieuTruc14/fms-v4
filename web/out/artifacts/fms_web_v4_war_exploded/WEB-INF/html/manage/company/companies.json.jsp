<%@page isELIgnored="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
[ <s:iterator var="item" value="companies" status="stat">
	{ "id":"<s:property value="id" />", "text":"<s:property escape="false" value="name" />" }<s:if test="!#stat.last">,</s:if>
</s:iterator> ]
