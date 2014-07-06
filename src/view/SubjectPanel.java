package view;

import controller.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;

/**
 *
 * @author Cesar A. Villamizar C.
 */
public class SubjectPanel extends javax.swing.JPanel {
    Asignatura asig;
    
    
    public SubjectPanel(final MainFrame frame,Asignatura asig){
        super();
        this.asig=asig;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(asig.getLabel());
        for (Button boton : asig.getButtons()) {               
                this.add(boton);
                this.add(boton.getProgressBar());
                boton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        Button bot = (Button) ae.getSource();
                        if (bot.isSelected()) {
                            if (bot.getMateria().getSelected()!=null) {
                                bot.setSelected(false);
                                bot.getMateria().getSelected().doClick();
                                bot.setSelected(true);
                            }
                            bot.getMateria().setSelected(bot);
                        } else {
                            bot.getMateria().setSelected(null);
                        }
                        frame.selectButton(bot);
                        frame.paintBloq(bot);
                    }
                });
            }            
            this.add(Box.createVerticalGlue());
    }
    
    public Asignatura getSubject(){
        return this.asig;
    }

    @Override
    public String toString(){
        return asig.toString();
    }   
    
}
