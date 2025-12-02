<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Login</title></head>
<body>
  <h2>Login</h2>
  <form method="post" action="${pageContext.request.contextPath}/login">
    <label>Username: <input name="username"/></label><br/>
    <label>Password: <input name="password" type="password"/></label><br/>
    <button type="submit">Login</button>
  </form>
  <div style="color:red;">
    ${requestScope.error}
  </div>
</body>
</html>
