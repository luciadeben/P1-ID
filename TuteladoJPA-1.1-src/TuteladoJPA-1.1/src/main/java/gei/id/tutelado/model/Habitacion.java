package gei.id.tutelado.model;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@TableGenerator(name="xeradorIdsHabitaciones", table="taboa_ids",
pkColumnName="nombre_id", pkColumnValue="idHabitacion",
valueColumnName="ultimo_valor_id",
initialValue=0, allocationSize=1)

@NamedQueries ({
        @NamedQuery (name="Habitacion.recuperaPorNumero",
                query="SELECT h FROM Habitacion h where h.numero=:numero"),
        @NamedQuery (name="Habitacion.recuperaConEmpleados",
                query="SELECT h FROM Habitacion h LEFT JOIN FETCH h.empleados WHERE h.numero = :numero"),        
        @NamedQuery (name="Habitacion.recuperaTodos",
                query="SELECT h FROM Habitacion h ORDER BY h.numero")
})

@Entity
@Table(name="Habitacion")
public class Habitacion {
    @Id
    @GeneratedValue (generator="xeradorIdsHabitacion")
    private Long id;

    @Column(nullable = false, unique=true)
    private int numero;

    @Column(nullable = false, unique=false)
    private int planta;

    @Column(nullable = false, unique=false)
    private int capacidad;

    @Column(nullable = false, unique=false)
    private String tipo;

    @ManyToMany(fetch=FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn (nullable=false, unique=false)
    private Set<Empleado> empleados;
    //rivate String empleados;

    @OneToMany(fetch=FetchType.EAGER, mappedBy = "habitacion", cascade={CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Residente> residentes;
    //private String residentes

    @Column(nullable = false, unique=false)
    private String estado;

    // Metodo de conveniencia para asegurarnos de que actualizamos los dos extremos de la asociación al mismo tiempo
	public void addResidente(Residente residente) {
        if(residentes==null){
            this.residentes = new HashSet<>();
        }
        if (this.capacidad<this.residentes.size()) throw new RuntimeException ("");
		this.residentes.add(residente);
        if(residente.getHabitacion() != this){
          residente.setHabitacion(this);
        }
	}
    
    public void removeResidente(Residente residente) {
        if (this.residentes.size() == 0) {
            throw new RuntimeException("La habitación no tiene residentes para eliminar");
        }
        residente.setHabitacion(null);
        this.residentes.remove(residente);
    }
    
    public void addEmpleado(Empleado empleado){
        if(empleados==null){
            this.empleados = new HashSet<>();
        }
        this.empleados.add(empleado);
    }
         
    public void removeEmpleado(Empleado empleado) {
	if (this.residentes.size() == 0) {
        throw new RuntimeException("La habitación no tiene empleados para eliminar");
       }
        this.empleados.remove(empleado);
        }

    public Long getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {this.numero = numero;}

    public int getPlanta() {
        return planta;
    }

    public void setPlanta(int planta) {
        this.planta = planta;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Set<Empleado> getEmpleado() {return empleados;}
    //public String getEmpleado() {return empleados;}

    public void setEmpleado(Set<Empleado> empleados) {
        this.empleados = empleados;
    }

    /*public void setEmpleado(String empleados) {
        this.empleados = empleados;
    }*/

    public Set<Residente> getResidente() {return residentes;}
    //public String getResidente() {return residentes;}

    public void setResidente(Set<Residente> residentes) {
        this.residentes = residentes;
    }

    /*public void setResidente(String residentes) {
        this.residentes = residentes;
    }*/

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numero;
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
		Habitacion other = (Habitacion) obj;
		if (numero!=other.numero)
			return false;
		return true;
	}

    @Override
    public String toString() {
        return "Habitacion [numero=" + numero + ", planta=" + planta + ", capacidad=" + capacidad + ", tipo=" + tipo + ", empleado=" + empleados + ", residente=" + residentes + ", estado=" + estado + "]";
    }
}
