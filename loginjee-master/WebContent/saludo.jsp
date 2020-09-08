<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Statement"%>
<%@page import="loginjee.persistencia.BaseDeDatos"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- vamos a HACER UN JSP en el que rx el id de un usuario
y devolvemos una página con su nombre 

http://localhost:8081/loginjee/saludo.jsp?usuario=1
-->
<!-- TODO haced un SERVLET QUE RX UN ID DE UN USUARIO POR PARÁMETROS (URL)
Y MOSTRARLO POR PANTALLA CON SYSOUT EN EL DOGET

HACED LAS CAPAS DE SERVICIO Y PERSISTENCIA POR SEPARADO - SI SE PUEDE- -->
	<h1>
		
		<%
		Connection connection = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String nom_usuario = null;
		try{
			String id = request.getParameter("usuario");
			int idn = Integer.parseInt(id);
			String consulta_SQL = "SELECT nombre from hedima.usuarios where idusuarios = ?";
			
			connection = BaseDeDatos.getConnection();
			st = connection.prepareStatement(consulta_SQL);
			st.setInt(1, idn);
			rs = st.executeQuery();
			
			if (rs.next())
			{
				nom_usuario = rs.getString("nombre");
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		} finally{
			BaseDeDatos.liberarRecursos(connection, st, rs);
			
		}
		
	%>
	Hola <% out.println(nom_usuario); %>
	</h1>
</body>
</html>