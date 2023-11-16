package gei.id.tutelado.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NamedQueries ({
        @NamedQuery (name="Residente.recuperaPorNif",
                query="SELECT r FROM Residente r where r.nif=:nif"),
        @NamedQuery (name="Residente.recuperaTodos",
                query="SELECT r FROM Residente r ORDER BY r.nif")
})

@Entity
@Table(name="Residente_tcc")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Residente extends Persona {

    @Column(nullable = false, unique = false)
    private LocalDate fecha_ingreso;

    @Column(nullable = false, unique=false)
    private String estadoSalud;


    @ElementCollection
    @CollectionTable(name = "TelefonosContacto", joinColumns = @JoinColumn(name = "residente_id"))
    @Column(name = "telefono")
    private List<String> contactosEmergencia;


    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn (nullable=false, unique=false)
    private Habitacion habitacion;
    //private int habitacion;


    public LocalDate getFechaIngreso() {
        return fecha_ingreso;
    }


    public void setFechaIngreso(LocalDate fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }


    public String getEstadosalud() {
        return estadoSalud;
    }


    public void setEstadosalud(String estadoSalud) {
        this.estadoSalud = estadoSalud;
    }


    public List<String> getContactosEmergencia() {
        return contactosEmergencia;
    }


    public void setContactosEmergencia(List<String> contactosEmergencia) {
        this.contactosEmergencia = contactosEmergencia;
    }


    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    /*public int getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(int habitacion) {
        this.habitacion = habitacion;
    }*/

    public void addContacto(String contacto){
        this.contactosEmergencia.add(contacto);
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getNif() == null) ? 0 : this.getNif().hashCode());
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
		Residente other = (Residente) obj;
		if (this.getNif() == null) {
			if (other.getNif() != null)
				return false;
		} else if (!this.getNif().equals(other.getNif()))
			return false;
		return true;
	}


    @Override
    public String toString() {
        return "Residente [fecha_ingreso=" + fecha_ingreso + ", estadoSalud=" + estadoSalud + ", contactosEmergencia=" + contactosEmergencia + ", habitacion=" + habitacion.getNumero() + "]";
    }






}
