/**
 * 
 */

// todo OBTENER LOS DATOS DEL FORMULARIO LOGIN Y PWD INTRODUCIDOS
// POR EL CLIENTE
// Y ENVIARLOS AL SERVIDOR, PARA COMPROBAR SI ESE USUARIO EXISTE
// O ESTÁ REGISTRADO
const URL_LOGIN = "http://localhost:8080/loginjee/Login";
const URL_LOGIN_RELATIVA = "/loginjee/Login";
const CLAVE_GUARDAR_CREDENCIALES = "guardar_credenciales";
const CLAVE_CREDENCIALES = "credenciales_usuario";

onload = cargarPagina;

function leerCookieActivado() {
	let activado_recordar = 'false';

	let activado = localStorage.getItem(CLAVE_GUARDAR_CREDENCIALES);
	if (activado != null) {
		activado_recordar = activado;
	}
	return activado_recordar;
}

function marcarDesmarcarCheckBox(activado) {
	let elemento_cb = document.getElementById("cajita");
	if (activado == 'true') {
		elemento_cb.checked = true; // marcando la caja
	} else {
		elemento_cb.checked = false; // desmarcando la caja
	}

}

function recuperarCredenciales() {
	let usuario;

	let u_json = localStorage.getItem(CLAVE_CREDENCIALES);
	usuario = JSON.parse(u_json);

	return usuario;
}

function mostrarCredenciales(usuario) {
	document.getElementById("nombre").value = usuario.nombre;
	document.getElementById("pwd").value = usuario.pwd;

}

function actualizarCookieRecordar() {
	console.log("Toca check");
	let elemento_cb = document.getElementById("cajita");
	if (elemento_cb.checked)// ==true
	{
		localStorage.setItem(CLAVE_GUARDAR_CREDENCIALES, 'true');
	} else {
		localStorage.setItem(CLAVE_GUARDAR_CREDENCIALES, 'false');
	}
}

function cargarPagina() {
	console.log("cargando página");
	let activado_recordar = leerCookieActivado();
	marcarDesmarcarCheckBox(activado_recordar);
	// TODO SI ACTIVADO_RECORDAR --> RECUERAR LAS CREDENCIALES Y MOSTRARLAS
	if (activado_recordar == 'true') {
		let usuario = recuperarCredenciales();
		if (usuario != null) {
			mostrarCredenciales(usuario);
		}

	}

}

function guardarCredenciales(jsonusuario) {
	localStorage.setItem(CLAVE_CREDENCIALES, jsonusuario);
}

// https://www.bing.com/search?q=realm+adrid&cvid=6da69e504f24467c997e6a1210719880&FORM=ANNTA1&PC=U531
// SERVICIO ES SINÓNIMO DE Servlet en JAVA
var xhr = new XMLHttpRequest();
function servidorlogin() {
	console.log("Entrando al servidor");
	var name = document.getElementById("nombre").value;
	var password = document.getElementById("pwd").value;

	var usuario = {
		nombre : name,
		pwd : password
	};

	var jsonusuario = JSON.stringify(usuario);

	if (leerCookieActivado() == 'true')// si quiere recordar
	{
		guardarCredenciales(jsonusuario);
	}

	console.log(jsonusuario);
	// si en vio info dsede el cliente al servidor debe usar post

	// TODO usar AJAX para enviar al servidor los datos
	xhr.open("POST", URL_LOGIN_RELATIVA);
	// deberíamos setear el content-type
	xhr.setRequestHeader("Content-type", "application/json;UTF-8");
	xhr.onreadystatechange = respuestaLogin;// programar el callback
	xhr.send(jsonusuario);
	// console.log ("la respuesta del soervidor ha sido ..");

}

function respuestaLogin() {
	// esta función será invocada cuando vuelva del servidor
	if (xhr.readyState == 4) {
		console.log("La respuesta del servidor ha llegado");
		if (xhr.status == 200) {
			alert("Logueado con éxito");
			location = "menu.html";
		} else if (xhr.status == 204) {
			alert("Nombre incorrecto. No existe el usuario");
		} else if (xhr.status == 403) {
			alert("Password incorrecta.");
		} else if (xhr.status == 302) {
			alert("VISITANTE NUMERO 5 ENHORABUENA");
			location = "https://euw.leagueoflegends.com/es-es/";
		} else if (xhr.status == 500) {

			alert("Ha habido un error. Intentelo más tarde");
		}
	}
}
