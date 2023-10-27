package gei.id.tutelado.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance (strategy = InheritanceType.TABLE_PER_CLASS)

@TableGenerator(name="xeradorIdsPersonas", table="taboa_ids",
pkColumnName="nombre_id", pkColumnValue="idPersona",
valueColumnName="ultimo_valor_id",
initialValue=0, allocationSize=1)

@NamedQueries ({
	@NamedQuery (name="Persona.recuperaPorNif",
				 query="SELECT p FROM Persona p where p.nif=:nif"),
	@NamedQuery (name="Persona.recuperaTodos",
				 query="SELECT p FROM Persona p ORDER BY p.nif")
})

public abstract class Persona {
    @Id
    @GeneratedValue (generator="xeradorIdsPersonas")
    private Long id;

	@Column(nullable = false, unique = true)
    private String nif;

	@Column(nullable = false, unique=false)
    private String nombre;

	@Column(nullable = false, unique=false)
    private String apellidos;

	@Column(nullable = true, unique=false)
    private String telefono;

    @Column(nullable = false, unique=false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false, unique=false)
    private String genero;

    @Column(nullable = false, unique=false)
    private String direccion;

    public Long getId() {
		return id;
	}

    public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}
     
    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
    public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	// Metodo de conveniencia para asegurarnos de que actualizamos os dous extremos da asociación ao mesmo tempo
/* 	public void engadirEntradaLog(EntradaLog entrada) {
		if (entrada.getUsuario() != null) throw new RuntimeException ("");
		entrada.setUsuario(this);
		// É un sorted set, engadimos sempre por orde de data (ascendente)
		this.entradasLog.add(entrada);
	}*/

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nif == null) ? 0 : nif.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		if (nif == null) {
			if (other.nif != null)
				return false;
		} else if (!nif.equals(other.nif))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return null;
		//return "Persona [id=" + id + ", nif=" + nif + ", nombre=" + nombre + ", dataAlta=" + dataAlta + "]";
	}

    



    
}
