package gei.id.tutelado.model;

import javax.persistence.*;
import java.time.LocalDate;

@NamedQueries ({
	@NamedQuery (name="Empleado.recuperaPorNif",
				 query="SELECT e FROM Empleado e where e.nif=:nif"),
	@NamedQuery (name="Empleado.recuperaTodos",
				 query="SELECT e FROM Empleado e ORDER BY e.nif")
})

@Entity
@Table(name="Empleado_tcc")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Empleado extends Persona {
    
    @Column(nullable = false, unique = true)
    private String nss;

    @Column(nullable = false, unique=false)
    private Double salario;

    @Column(nullable = false, unique=false)
    private String puesto;
    
    @Column(nullable = false, unique=false)
    private LocalDate fechaContratacion;

    @Column(nullable = true, unique=false)
    private String horario;

    @Column(nullable = true, unique=false)
    private int experiencia;
     

	public String getNss() {
        return nss;
    }


    public void setNss(String nss) {
        this.nss = nss;
    }


    public Double getSalario() {
        return salario;
    }


    public void setSalario(Double salario) {
        this.salario = salario;
    }


    public String getPuesto() {
        return puesto;
    }


    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }


    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }


    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    
    public String getHorario() {
        return horario;
    }


    public void setHorario(String horario) {
        this.horario = horario;
    }


    public int getExperiencia() {
        return experiencia;
    }


    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }


    @Override
	public String toString() {
		return "Empleado [nss=" + nss + ", salario=" + salario + ", puesto=" + puesto + ", fechaContratacion=" + fechaContratacion + ", experiencia=" + experiencia + "]";
	}
    
}
