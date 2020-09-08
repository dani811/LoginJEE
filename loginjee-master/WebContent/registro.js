//EL NOMBRE DEL USUARIO TIENE QUE SER MINIMO DE 2
//LA PWD DEL USUARIO TIENE QUE SER DE AL MENOS 2
//controlado que nombre no puede estar repe

//const ULR_COMPROBAR_NOMBRE_DISPONIBLE = "/loginjee/ExisteNombre"
const ULR_COMPROBAR_NOMBRE_DISPONIBLE = "/loginjee/ExisteNombreMap"
var xhr = new XMLHttpRequest();
var nombredisponible = false;

function comprobarNombreDisponible() {
	console.log ("Comprobando nombre ...");
	let nuevo_nombre = document.getElementById("nombre").value;
	if (nombreCorrecto (nuevo_nombre))
		{
		let url = ULR_COMPROBAR_NOMBRE_DISPONIBLE + "?nombre=" +nuevo_nombre;
		console.log ("get sobre " + url);
		//LLAMAR AL SERVIDOR
		xhr.open("GET", url);
		//deberíamos setear el content-type
		xhr.onreadystatechange = respuestaNombreDisponible;//programar el callback
		xhr.send(null);
		}
}

function limpiarFormularioYSetearFocus()
{
	document.getElementById("nombre").value = '';
	document.getElementById("pwd").value = '';
	document.getElementById("nombre").focus();
}
function respuestaNombreDisponible ()
{
	if (xhr.readyState == 4) {
		console.log("La respuesta del servidor ha llegado");
		if (xhr.status == 200) {
			//esta disponible
			console.log ("disponible");
			let elemento_error = document.getElementById("errornombre");
			elemento_error.innerHTML = "";
			nombredisponible = true;
		} else if (xhr.status == 409) {
			//NO esta disponible
			console.log (" NO disponible");
			let elemento_error = document.getElementById("errornombre");
			let nombre_no_valido = document.getElementById('nombre').value;
			elemento_error.innerHTML = nombre_no_valido +" Nombre no disponible";
			nombredisponible =false;
			limpiarFormularioYSetearFocus();
			
		} else if (xhr.status == 500) {
			console.log ("ERROR");
		}
	}
}

function nombreCorrecto (nombre)
{
	let correcto = false;
		
		correcto = (nombre.length >=2);
	
	return correcto;
}

function pwdCorrecto (pwd)
{
	let correcto = false;
		
		correcto = (pwd.length >=2);
	
	return correcto;
}

function activarBotonEnvio()
{
	let boton = document.getElementById("botonalta");
	boton.disabled = false;
}

function desactivarBotonEnvio()
{
	let boton = document.getElementById("botonalta");
	boton.disabled = true;
}

function modificadoFormulario() 
{
	let nuevo_nombre = document.getElementById("nombre").value;
	let nuevo_pwd = document.getElementById("pwd").value;
	
	if ((nombredisponible)&&nombreCorrecto(nuevo_nombre)&&(pwdCorrecto(nuevo_pwd)))
		{
			activarBotonEnvio();
		} else {
			desactivarBotonEnvio();
		}
	
	//mostrando errores personalizados
	/*let nombre_correcto =  false;
	let pwd_correcto =  false;
	
	if (nombreCorrecto(nuevo_nombre))
		{
			nombre_correcto = true;
		} else {
			mostrarErrorNombre();
		}
	if (pwdCorrecto (nuevo_pwd))
	{
		pwd_correcto = true;
	} else {
		mostrarErrorPwd();
	}
	 
	if (nombre_correcto&&ppwd_correcto)
		{
		activarBotonEnvio();
		} else {
			desactivarBotonEnvio();
		}
	*/
}

