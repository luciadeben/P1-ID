package gei.id.tutelado.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NamedQueries ({
        @NamedQuery (name="Residente.recuperaPorNif",
                query="SELECT r FROM Residente r where r.nif=:nif"),
        @NamedQuery (name="Residente.recuperaTodos",
                query="SELECT r FROM Residente e ORDER BY r.nif")
})

@Entity
@Table(name="Residente_tcc")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Residente extends Persona {

    @Column(nullable = false, unique = false)
    private LocalDate fecha_ingreso;

    @Column(nullable = false, unique=false)
    private String estadoSalud;

    @Column(nullable = false, unique=false)
    private List<String> contactosEmergencia;

    @Column(nullable = false, unique=false)
    private Habitacion habitacion;


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



    @Override
    public String toString() {
        return "Residente [fecha_ingreso=" + fecha_ingreso + ", estadoSalud=" + estadoSalud + ", contactosEmergencia=" + contactosEmergencia + ", habitacion=" + habitacion + "]";
    }






}
