package pruenainsersion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/** @author VICTOR */

@Entity
@Table(name = "CIUDADES")
public class Ciudad {
    
    private long idciudad;
    private String nombre;

    public Ciudad() {
    }

    public Ciudad(String nombre) {
        this.nombre = nombre;
    }
    
    @Id
    @GeneratedValue
    @Column(name = "CIUDAD_ID")
    public long getCiudadID() {
        return this.idciudad;
    }

    public void setCiudadID(long idciudad) {
        this.idciudad = idciudad;
    }

    @Column(name = "NOMBRE", nullable = false, length = 100)
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
