package app;

import java.util.List;

public class Contacto {

	private String nombres,apellidos,fotografia,direccion,email;
	private List<String> telefonos;
	
	public Contacto(String nombres, String apellidos, String fotografia, String direccion, String email,
			List<String> telefonos) {
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.fotografia = fotografia;
		this.direccion = direccion;
		this.email = email;
		this.telefonos = telefonos;
	}

	public String getNombres() {
		return this.nombres;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public String getFotografia() {
		return this.fotografia;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public String getEmail() {
		return this.email;
	}

	public List<String> getTelefonos() {
		return this.telefonos;
	}

	@Override
	public String toString() {
		return this.nombres + " "+ this.apellidos;
	}	
}
