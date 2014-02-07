package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedHashSet;
import javax.swing.JRadioButton;
import view.JProgressBarColored;

/**
 *
 * @author Cesar A. Villamizar C.
 */
public class Button extends JRadioButton {

    private Group grupo;
    private Asignatura materia;    
    private int conflictive;
    private LinkedHashSet<Button> overlapped;
    private Color color;
    private JProgressBarColored progressBar;
    
    public LinkedHashSet<Button> getOverlapped(){
        return this.overlapped;
    }
    
    public void addConflictive(){
        conflictive++;
    }
    
    public void removeConflictive(){
        conflictive--;
    }
    
    public int getConflictiveAmount(){
        return conflictive;
    }    
    
    public Asignatura getMateria() {
        return materia;
    }

    public Group getGrupo() {
        return grupo;
    }

    public JProgressBarColored getProgressBar() {
        
        return progressBar;
    }

    public Button(Group grupo, Asignatura materia) {
        super(grupo.toString());        
        this.conflictive=0;
        this.grupo = grupo;
        this.materia = materia;        
        this.color = null;
        int maxValue = grupo.getCuposTotales();
        int value = grupo.getCupos();
        if (value > maxValue) {
            value = maxValue;
        }
        if (value > 0) {
            this.color = new Color(255 - 215 * value / maxValue, 30 + 185 * value / maxValue, 30 + 20 * value / maxValue);
        }
        progressBar = new JProgressBarColored(maxValue, value);
        progressBar.setBackground(Color.LIGHT_GRAY);
        progressBar.setForeground(this.color);
        progressBar.setPreferredSize(new Dimension(100, 12));
        progressBar.setMaximumSize(new Dimension(500, 15));
        progressBar.setVisible(false);
        this.setToolTipText("Cupos disp: "+grupo.getCupos()+"/"+grupo.getCuposTotales());
        materia.getButtons().add(this);
        initOverlapped();
    }

    private void initOverlapped(){
        this.overlapped= new LinkedHashSet<>();
        for (Asignatura mat : Kairos.getSubjects()) {            
            if (mat!=this.materia) {
                
                for (Button boton : mat.getButtons()) {
                    
                    if (grupo.isOverlappedWith(boton.getGrupo())) {
                        this.getOverlapped().add(boton);
                        boton.getOverlapped().add(this);
                        
                    }                    
                }
            }
        }        
    }
    
    public void colorButton(boolean bool) {
        if (bool) {
            this.setForeground(color);
        } else {
            this.setForeground(null);
        }
    }

    public void enableProgressBar(boolean bool) {
        this.progressBar.setVisible(bool);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Button) {
            if (this.hashCode() == ((JRadioButton) o).hashCode()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.grupo != null ? this.grupo.hashCode() : 0);
        return hash;
    }
}
