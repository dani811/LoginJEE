/**
 * 
 */

//window.onload = iniciaestilo;


function iniciaestilo ()
{
	console.log ("estilo iniciado");
	let anchoinput = document.getElementById("nombrebuscar").getBoundingClientRect().width;
	console.log ("ancho = " + anchoinput);
	document.getElementById("listasugerencias").width = anchoinput;
}

const URL_CONSULTAR_USUARIOS_PATRON_NOMBRE = "/loginjee/BusquedaPorPatron";
const URL_CONSULTAR_USUARIOS_POR_NOMBRE = "/loginjee/BusquedaPorNombre";

	//TODO PETICION A SERVIDOR PARA QUE NOS DEVUELVA LA LISTA DE NOMBRES COINCIDENTES
var xhr = new XMLHttpRequest();
function buscarXnombre() 
{
	console.log ("INTRO NOMBRE");
	let nombre = document.getElementById("nombrebuscar").value;
	console.log ("nombre = " + nombre);
	if (nombre.length > 0)
		{
		let url = URL_CONSULTAR_USUARIOS_PATRON_NOMBRE + "?patron="+nombre;
		console.log ("url = " + url);
		xhr.open("GET", url);
		xhr.onreadystatechange = pintarListaNombres;//programar el callback
		xhr.send(null);
		}
	else {
		borrarListaNombres();
	}
	
}
function borrarListaNombres()
{
	let elemento_ul = document.getElementById ("listasugerencias");//cojo el padre
	elemento_ul.innerHTML="";
	
	let tabla_u = document.getElementById ("tusuario");
	tabla_u.hidden = true;
}

function mostrarNombre (nombre)
{
	let elemento_li_nuevo = document.createElement("li");//creamos el elemento nuevo
	elemento_li_nuevo.innerHTML = nombre;//le metemos el nombre
	elemento_li_nuevo.setAttribute ("onclick", "mostrarDetalle ('"+nombre+"');");
	let elemento_ul = document.getElementById ("listasugerencias");//cojo el padre
	elemento_ul.appendChild (elemento_li_nuevo);//y le agregamos un nuevo hijo
}

function mostrarDetalle (nombre)
{
	console.log("Nombre clicado = " + nombre);
	//borrar lista
	borrarListaNombres();
	document.getElementById("nombrebuscar").value = nombre;
	
	let url = URL_CONSULTAR_USUARIOS_POR_NOMBRE + "?nombre="+nombre;
	console.log ("url = " + url);
	xhr.open("GET", url);
	xhr.onreadystatechange = pintarNombre;//programar el callback
	xhr.send(null);
	
}
function mostrarUsuario (usuario)//id nombre pwd
{
	
	//pasart de json a objeto js
	let usuario_js = JSON.parse (usuario);
	
	let tabla_u = document.getElementById ("tusuario");
	let tid = document.getElementById ("tid");
	let tnombre = document.getElementById ("tnombre");
	let tpwd = document.getElementById ("tpwd");
	
	tid.innerHTML = usuario_js.id;
	tnombre.innerHTML = usuario_js.nombre;
	tpwd.innerHTML = usuario_js.pwd;
	
	tabla_u.hidden = false;
	
	
	
}

function mostrarListaNombres (lista_nombres)
{
	let lista_nombres_js = JSON.parse (lista_nombres);
	console.log (lista_nombres.length);
	console.log (lista_nombres_js.length);
	for (let i=0; i<lista_nombres_js.length; i++)
		{
		mostrarNombre (lista_nombres_js[i]);
		}
	
}

function pintarNombre ()
{
	if (xhr.readyState == 4) {
		//borrarListaNombres();
		console.log("La respuesta del servidor ha llegado");
		if (xhr.status == 200) {
			console.log("200 - Usuario recibido");
			//TODO mostrar la lista en la paǵina
			console.log (xhr.responseText);
			mostrarUsuario (xhr.responseText);
		} else if (xhr.status == 204) {
			console.log("204 -  Usuario vacío");
			
			}
		else if (xhr.status == 500) {
			console.log("500 -Ha habido un error. Intentelo más tarde");
		}
	}
}
function pintarListaNombres ()
{
	if (xhr.readyState == 4) {
		borrarListaNombres();
		console.log("La respuesta del servidor ha llegado");
		if (xhr.status == 200) {
			console.log("200 - Lista usuarios recibida");
			//TODO mostrar la lista en la paǵina
			console.log (xhr.responseText);
			mostrarListaNombres (xhr.responseText);
		} else if (xhr.status == 204) {
			console.log("204 - Lista usuarios vacia");
			
			}
		else if (xhr.status == 500) {
			console.log("500 -Ha habido un error. Intentelo más tarde");
		}
	}
}
