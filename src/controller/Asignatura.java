package controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.JLabel;

/**
 *
 * @author Cesar A. Villamizar C.
 */
public class Asignatura {

    private int creditos;
    private String codigo;
    private ArrayList<Button> buttons;
    private String name;
    private ArrayList<Group> grupos;
    private boolean selected;
    private JLabel label;
        
    public void setSelected(boolean selected) {
        this.selected = selected;
        if(selected){
        this.label.setForeground(Color.BLUE);}
        else{this.label.setForeground(null);}
    }

    public boolean isSelected() {
        return selected;
    }

    public JLabel getLabel() {
        return label;
    }    
    
    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public String getNombre() {
        return name;
    }
    
    public String getCodigo(){
        return codigo;
    }
   
    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }   
    
    public ArrayList<Group> getGrupos() {
        return grupos;
    }

    public Asignatura(String name,String code) {
        this.name = name;        
        this.grupos = new ArrayList<>();
        this.buttons = new ArrayList<>();
        this.label= new JLabel("  -->" + name);
        this.codigo= code;
    }
    
    public Asignatura(String name) {
        this(name,"0000000");        
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Asignatura){
            Asignatura a= (Asignatura)o;
            if(a.getNombre().equals(this.name)){
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int hash = 5;        
        hash = 79 * hash + Objects.hashCode(this.name);
        return hash;
    }    
    
    @Override
    public String toString() {
        return this.creditos+":"+this.name;
    }    
}
   