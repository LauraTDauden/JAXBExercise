package domain;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Tienda {

    @XmlElementWrapper (name = "coleccionJuegos")
    @XmlElement(name = "videojuego")
    private List<Videojuego> coleccionJuegos;
    private String nombre;
    private String localizacion;


    public List<Videojuego> getColeccionJuegos() {
        return coleccionJuegos;
    }

    public void setColeccionJuegos(List<Videojuego> coleccionJuegos) {
        this.coleccionJuegos = coleccionJuegos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }
}
