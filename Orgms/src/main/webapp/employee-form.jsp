<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html><head><title>Employee Form</title></head><body>
  <h2><c:if test="${not empty employee}">Edit Employee</c:if><c:if test="${empty employee}">Create Employee</c:if></h2>
  <form method="post" action="${pageContext.request.contextPath}/employees">
    <input type="hidden" name="action" value="save"/>
    <input type="hidden" name="employeeId" value="${employee.employeeId}"/>
    Name: <input name="name" value="${employee.empName}"/><br/>
    Email: <input name="email" value="${employee.empCemail}"/><br/>
    DOB: <input name="dob" type="date" value="${employee.empDob}"/><br/>
    Designation: <input name="designation" value="${employee.empDesignation}"/><br/>
    Blood Group: <input name="bloodGroup" value="${employee.empBgp}"/><br/>
    Mobile: <input name="mobile" value="${employee.empCmob}"/><br/>
    Address: <textarea name="address">${employee.empAddress}</textarea><br/>
    <button type="submit">Save</button>
    <a href="${pageContext.request.contextPath}/employees">Cancel</a>
  </form>
</body></html>
