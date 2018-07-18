package pruenainsersion;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/** @author VICTOR  */

@Entity
@Table(name = "ALUMNO")
public class Alumno {
    
    private long idAlumno;
    private String nombre;
    private String sexo;
    private int edad;
    private Ciudad idCiudad;
    
    public Alumno() {
    }

    public Alumno( String nombre, String sexo, int edad, Ciudad idCiudad ) {
        this.nombre = nombre;
        this.sexo = sexo;
        this.edad = edad;
        this.idCiudad = idCiudad;
    }
    
    @Id
    @GeneratedValue
    @Column(name = "ALUMNO_ID")
    public long getAlumnoId() {
        return this.idAlumno;
    }
    
    public void setAlumnoId(long idAlumno) {
        this.idAlumno = idAlumno;
    }

    @Column(name = "NOMBRE", nullable = false, length = 100)
    public String getAlumnoName() {
        return this.nombre;
    }
    
    public void setAlumnoName(String nombre) {
        this.nombre = nombre;
    }
    
    @Column(name = "SEXO", nullable = false, length = 100)
    public String getAlumnoSexo() {
        return this.sexo;
    }
    
    public void setAlumnoSexo(String sexo) {
        this.sexo = sexo;
    }

    @Column(name = "EDAD", nullable = false, length = 100)
    public int getAlumnoEdad() {
        return this.edad;
    }
    
    public void setAlumnoEdad(int edad) {
        this.edad = edad;
    }
    
    @ManyToOne(cascade = CascadeType.ALL)
    public Ciudad getAlumnoCiudad() {
        return this.idCiudad;
    }

    public void setAlumnoCiudad( Ciudad idCiudad ) {
        this.idCiudad = idCiudad;
    }
}
