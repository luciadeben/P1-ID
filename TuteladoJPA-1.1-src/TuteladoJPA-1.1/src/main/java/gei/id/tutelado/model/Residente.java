package gei.id.tutelado.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NamedQueries ({
        @NamedQuery (name="Residente.recuperaPorNif",
                query="SELECT r FROM Residente r where r.nif=:nif"),
        @NamedQuery (name="Residente.recuperaTodos",
                query="SELECT r FROM Residente r ORDER BY r.nif"),
        @NamedQuery(name = "Residente.recuperaHabitacion",
                query = "SELECT r FROM Residente r INNER JOIN r.habitacion h WHERE h.id = :habitacionId")
})

@Entity
@Table(name="Residente_tcc")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Residente extends Persona {

    @Column(nullable = false, unique = false)
    private LocalDate fecha_ingreso;

    @Column(nullable = false, unique=false)
    private String estadoSalud;


    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name = "TelefonosContacto", joinColumns = @JoinColumn(name = "residente_id"))
    @Column(name = "telefono")
    private List<String> contactosEmergencia;


    @ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE})
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
        if(habitacion!=null){
            if(this.habitacion!=null){
                this.habitacion.removeResidente(this);
            }
            this.habitacion = habitacion;
            habitacion.addResidente(this);
        }
        else{
            this.habitacion = habitacion;
        }
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
    public String toString() {
        return "Residente [fecha_ingreso=" + fecha_ingreso + ", estadoSalud=" + estadoSalud + ", contactosEmergencia=" + contactosEmergencia + ", habitacion=" + habitacion.getNumero() + "]";
    }






}
