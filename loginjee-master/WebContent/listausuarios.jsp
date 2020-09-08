<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="loginjee.bean.Usuario"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
AQUÍ IRÁ LA LISTA DE USUARIOS
<table>
<%
List<Usuario> lu = (List<Usuario>)request.getAttribute("lu");
for(int i = 0; i < lu.size(); i+=1) { %>
        <tr>      
            <td><%=lu.get(i).getId()%></td>
           	<td><%=lu.get(i).getNombre()%></td>
            <td><%=lu.get(i).getPwd()%></td>
        </tr>
    <% } %>
</table>
</body>
</html>