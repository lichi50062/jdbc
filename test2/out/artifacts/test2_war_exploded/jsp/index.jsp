<%@ page import="entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>查詢頁面</title>
</head>
<body>
<div align="center">
  <h2/>輸入使用者名稱，查詢資訊
  <form action="/findByName" method="get">
    <input type="text" name="name" id="name">
    <input type="submit" value="查詢">
  </form>
  <%
    User userInfo = (User) request.getAttribute("userInfo");
  %>
  <%
    if (userInfo != null) {
  %>
  <table border="3">
    <tr>
      <th>使用者名稱</th>
      <th>密碼</th>
      <th>年齡</th>
    </tr>
    <tr>
      <td> &nbsp; &nbsp; <%=userInfo.getName()%> &nbsp; &nbsp;</td>
      <td> &nbsp; &nbsp; <%=userInfo.getPassword()%> &nbsp; &nbsp;</td>
      <td> &nbsp; &nbsp; <%=userInfo.getAge()%> &nbsp; &nbsp;</td>
    </tr>

  </table>
  <%
    }
  %>
</div>
</body>
</html>