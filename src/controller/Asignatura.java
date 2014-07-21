package controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import javax.swing.JLabel;

/**
 *
 * @author Cesar A. Villamizar C.
 */
public class Asignatura {

    private Color[] c={Color.BLUE,Color.WHITE};
    private int creditos;
    private String codigo;
    private ArrayList<Button> buttons;
    private String name;
    private ArrayList<Group> grupos;
    private Button selected;   
    private JLabel label;
    private Plan plan;
       
    public static final Color[][] colors={{new Color(71,172,177),new Color(255,255,255)},
                                        {new Color(242,101,34),new Color(255,255,255)},
                                        {new Color(255,205,51),new Color(255,255,255)},
                                        {new Color(103,103,102),new Color(255,255,255)},
                                        {new Color(119,196,212),new Color(255,255,255)},
                                        {new Color(68,154,167),new Color(255,255,255)},
                                        {new Color(121,194,105),new Color(255,255,255)},
                                        {new Color(241,140,50),new Color(255,255,255)},
                                        {new Color(191,99,166),new Color(255,255,255)},
                                        {new Color(234,132,109),new Color(255,255,255)},
                                        {new Color(22,82,142),new Color(255,255,255)},
                                        {new Color(230,74,75),new Color(255,255,255)},
                                        {new Color(163,198,192),new Color(255,255,255)},
                                        {new Color(195,214,155),new Color(79,98,40)},
                                        {new Color(250,192,144),new Color(152,72,7)},
                                        {new Color(147,205,221),new Color(33,89,104)},
                                        {new Color(179,162,199),new Color(64,50,106)},
                                        {new Color(217,150,148),new Color(99,37,35)},
                                        {new Color(255,232,175),new Color(255,150,46)},
   
    };
    private static int currentColor= new Random().nextInt(colors.length);
    
    private static Color[] getNextColorPair(){        
        int i=(++currentColor)%(colors.length);        
        return colors[i];
    }    

    public Color[] getColor() {
        c= getNextColorPair();
        return c;
    }
    
    public void setSelected(Button selected) {
        this.selected = selected;
        if(selected!=null){            
            this.label.setBackground(c[0]);
            this.label.setForeground(c[1]);
        }
        else{
            this.label.setBackground(null);
            this.label.setForeground(null);        
        }
    }

    public Button getSelected() {
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
    
    public Plan getPlan(){
        return this.plan;
    }
    
    public void setPlan(Plan p){
        this.plan=p;
        this.label.setText("  -->" + name+" (Plan:"+this.plan.getCode()+")");
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }   
    
    public ArrayList<Group> getGrupos() {
        return grupos;
    }

    public void setGrupos(ArrayList<Group> grupos) {
        this.grupos = grupos;
    }  
    
    public Asignatura(String name,String code) {
        this.name = name;        
        this.grupos = new ArrayList<>();
        this.buttons = new ArrayList<>();
        this.label= new JLabel("  -->" + name);
        this.label.setOpaque(true);
        this.codigo= code;
        this.selected=null;
        this.plan=Plan.NULL_PLAN;
    }
    
    public Asignatura(String name) {
        this(name,"0000000");        
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Asignatura){
            Asignatura a= (Asignatura)o;
            if(a.getCodigo().equals(this.codigo)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;        
        hash = 79 * hash + Objects.hashCode(this.name);
        return hash;
    }    
    
    @Override
    public String toString() {
        return this.creditos+":"+this.name+" (Plan:"+this.plan.getCode()+")";
    }    
}
   