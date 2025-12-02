<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html><head><title>Timesheet Form</title></head><body>
  <h2>Create Timesheet</h2>
  <form method="post" action="${pageContext.request.contextPath}/timesheets">
    <input type="hidden" name="action" value="save"/>
    Project:
    <select name="projectId">
      <c:forEach var="p" items="${projects}">
        <option value="${p.projectId}">${p.jobName}</option>
      </c:forEach>
    </select><br/>
    <c:forEach var="i" begin="1" end="16">
      Day ${i}: <input name="day${i}" value="0"/><br/>
    </c:forEach>
    <button type="submit">Save</button>
  </form>
</body></html>
