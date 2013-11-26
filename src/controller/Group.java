package controller;

import java.util.ArrayList;

/**
 *
 * @author Cesar A. Villamizar C.
 */
public class Group {

    private ArrayList<Block> horario;
    private String docente;
    private String numero;
    private int cupos;
    private int cuposTotales;
    private String text;
    
    
    public String getAsText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }   
    
    
    public int getCuposTotales() {
        return cuposTotales;
    }

    public String getNumero() {
        return numero;
    }

    public ArrayList<Block> getHorario() {
        return horario;
    }

    public String getDocente() {
        return docente;
    }

    public int getCupos() {
        return cupos;
    }
    
    public Group(String numero, String docente, int cupos, int cuposTotales) {
        this.docente = docente;
        this.numero = numero;
        this.horario = new ArrayList<>();
        this.cupos = cupos;
        this.cuposTotales = cuposTotales;
    }    
        
    @Override
    public String toString() {
        return this.numero + "- " + this.docente;
    }   
    
    public boolean isOverlappedWith(Group grup) {
        for (Block blo1 : this.horario) {
            for (Block blo2 : grup.horario) {
                if (blo1.isOverlappedWith(blo2)) {
                    return true;
                }
            }
        }
        return false;
    }
}
