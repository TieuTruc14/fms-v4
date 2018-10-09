<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
[
    <s:iterator value="devices" status="item">
        [
        "<s:property value="id" />",
        <s:property value="dateStart.time" />,
        <s:property value="dateActive.time" />,
        <s:property value="dateEnd.time" />,
        "<s:property value="batch.model.productType.name" />"
        ]<s:if test="!#item.last">,</s:if>
    </s:iterator>
]
