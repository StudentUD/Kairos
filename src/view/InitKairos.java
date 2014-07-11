package view;

import controller.*;
import java.io.File;

/**
 *
 * @author Cesar A. Villamizar C.
 */
public class InitKairos extends javax.swing.JFrame {

    public InitKairos() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnsPane = new javax.swing.JPanel();
        amzBtn = new javax.swing.JButton();
        bogBtn = new javax.swing.JButton();
        carBtn = new javax.swing.JButton();
        manBtn = new javax.swing.JButton();
        medBtn = new javax.swing.JButton();
        orqBtn = new javax.swing.JButton();
        palBtn = new javax.swing.JButton();
        defaultBtn = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Seleccione su sede");
        setResizable(false);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/logo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel10))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Seleccione su sede:");

        amzBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/sed/AMZ.png"))); // NOI18N
        amzBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        amzBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amzBtnActionPerformed(evt);
            }
        });

        bogBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/sed/BOG.png"))); // NOI18N
        bogBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bogBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bogBtnActionPerformed(evt);
            }
        });

        carBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/sed/CAR.png"))); // NOI18N
        carBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        carBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carBtnActionPerformed(evt);
            }
        });

        manBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/sed/MAN.png"))); // NOI18N
        manBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        manBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manBtnActionPerformed(evt);
            }
        });

        medBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/sed/MED.png"))); // NOI18N
        medBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        medBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medBtnActionPerformed(evt);
            }
        });

        orqBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/sed/ORQ.png"))); // NOI18N
        orqBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        orqBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orqBtnActionPerformed(evt);
            }
        });

        palBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/sed/PAL.png"))); // NOI18N
        palBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        palBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                palBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btnsPaneLayout = new javax.swing.GroupLayout(btnsPane);
        btnsPane.setLayout(btnsPaneLayout);
        btnsPaneLayout.setHorizontalGroup(
            btnsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnsPaneLayout.createSequentialGroup()
                .addComponent(amzBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bogBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(carBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(manBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(medBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orqBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(palBtn))
        );
        btnsPaneLayout.setVerticalGroup(
            btnsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bogBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btnsPaneLayout.createSequentialGroup()
                .addComponent(amzBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(carBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(manBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(medBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(orqBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(palBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        defaultBtn.setSelected(true);
        defaultBtn.setText("No volverme a preguntar.");
        defaultBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defaultBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(defaultBtn)
                    .addComponent(btnsPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnsPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(defaultBtn)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void amzBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amzBtnActionPerformed
        selection(0);
    }//GEN-LAST:event_amzBtnActionPerformed

    private void defaultBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_defaultBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_defaultBtnActionPerformed

    private void bogBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bogBtnActionPerformed
        selection(1);
    }//GEN-LAST:event_bogBtnActionPerformed

    private void carBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carBtnActionPerformed
        selection(2);
    }//GEN-LAST:event_carBtnActionPerformed

    private void manBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manBtnActionPerformed
        selection(3);
    }//GEN-LAST:event_manBtnActionPerformed

    private void medBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medBtnActionPerformed
        selection(4);
    }//GEN-LAST:event_medBtnActionPerformed

    private void orqBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orqBtnActionPerformed
        selection(5);
    }//GEN-LAST:event_orqBtnActionPerformed

    private void palBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_palBtnActionPerformed
        selection(6);
    }//GEN-LAST:event_palBtnActionPerformed

    private void selection(int i) {
        Kairos.setSede(i);
        if (defaultBtn.isSelected()) {
            Kairos.writeSEDEfile(i);
        }
        callNewSession();
        this.dispose();
    }

    private static boolean readSede() {
        try {
            int i = 0;
            for (String s : Kairos.getData(new File("./SEDE.cfg"))) {
                if (s.startsWith("*")) {
                    Kairos.setSede(i);
                    return true;
                }
                if (++i >= 7) {
                    break;
                }
            }
        } catch (Exception ex) {
        }
        return false;
    }

    private static void callNewSession() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewSesion().setVisible(true);
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    javax.swing.UIManager.put("control", new java.awt.Color(240, 240, 240));
                    javax.swing.UIManager.put("nimbusOrange", new java.awt.Color(51, 98, 140));
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */

        if (!readSede()) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new InitKairos().setVisible(true);
                }
            });
        } else {
            callNewSession();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton amzBtn;
    private javax.swing.JButton bogBtn;
    private javax.swing.JPanel btnsPane;
    private javax.swing.JButton carBtn;
    private javax.swing.JCheckBox defaultBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JButton manBtn;
    private javax.swing.JButton medBtn;
    private javax.swing.JButton orqBtn;
    private javax.swing.JButton palBtn;
    // End of variables declaration//GEN-END:variables
}
