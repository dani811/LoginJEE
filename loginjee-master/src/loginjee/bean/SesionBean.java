package loginjee.bean;

import java.sql.Date;;

public class SesionBean {
	
	private int idsesion;//pk
	private int idusuario;
	private String sesionhttp;//[32]
	private Date tinicio; //
	private Date tfin;//
	private String nombre_usuario;//
	
	
	
	
	public SesionBean() {
		//super();
	}
	
	
	
	public String getNombre_usuario() {
		return nombre_usuario;
	}



	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario;
	}



	public SesionBean(int idsesion, int idusuario, String sesionhttp, Date tinicio, Date tfin) {
		super();
		this.idsesion = idsesion;
		this.idusuario = idusuario;
		this.sesionhttp = sesionhttp;
		this.tinicio = tinicio;
		this.tfin = tfin;
	}
	public int getIdsesion() {
		return idsesion;
	}
	public void setIdsesion(int idsesion) {
		this.idsesion = idsesion;
	}
	public int getIdusuario() {
		return idusuario;
	}
	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}
	public String getSesionhttp() {
		return sesionhttp;
	}
	public void setSesionhttp(String sesionhttp) {
		this.sesionhttp = sesionhttp;
	}
	public Date getTinicio() {
		return tinicio;
	}
	public void setTinicio(Date tinicio) {
		this.tinicio = tinicio;
	}
	public Date getTfin() {
		return tfin;
	}
	public void setTfin(Date tfin) {
		this.tfin = tfin;
	}
	
	

}
