package gei.id.tutelado.model;

import javax.persistence.*;
import java.time.LocalDate;


@NamedQueries ({
        @NamedQuery (name="Habitacion.recuperaPorNumero",
                query="SELECT h FROM Habitacion h where h.numero=:numero"),
        @NamedQuery (name="Habitacion.recuperaTodos",
                query="SELECT h FROM Habitacion h ORDER BY h.numero")
})

@Entity
@Table(name="Habitacion_tcc")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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

    @Column(nullable = false, unique=false)
    private set<Empleados> empleado ;

    @Column(nullable = false, unique=true)
    private set<Residente> residente ;

    @Column(nullable = false, unique=false)
    private String estado;


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

    public set<Empleados> getEmpleado() {return empleado;}

    public void setEmpleado(set<empleados> empleado) {
        this.empleado = empleado;
    }

    public set<Residente> getResidente() {return residente;}

    public void setResidente(set<Residente> residente) {
        this.residente = residente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    @Override
    public String toString() {
        return "Habitacion [numero=" + numero + ", planta=" + planta + ", capacidad=" + capacidad + ", tipo=" + tipo + ", empleado=" + empleado + ", residente=" + residente + ", estado=" + estado "]";
    }






}
