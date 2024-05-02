/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import java.awt.Toolkit;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;

/**
 *
 * @author debf
 */
public class CompilerJForm extends javax.swing.JFrame {

    /**
     * Creates new form CompilerJForm
     */
    public CompilerJForm() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        containerJPanel = new javax.swing.JPanel();
        textEditorJPanel = new javax.swing.JPanel();
        textEditorTittleJpanel = new javax.swing.JPanel();
        titleJLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textEditorJTextArea = new javax.swing.JTextArea();
        logsJPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultJTextArea = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        runJMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        loadExampleJMenuItem = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        containerJPanel.setLayout(new javax.swing.BoxLayout(containerJPanel, javax.swing.BoxLayout.X_AXIS));

        titleJLabel.setText("EDITOR DE TEXTO");
        textEditorTittleJpanel.add(titleJLabel);

        jPanel2.setLayout(new java.awt.CardLayout());

        textEditorJTextArea.setColumns(20);
        textEditorJTextArea.setRows(5);
        jScrollPane1.setViewportView(textEditorJTextArea);

        jPanel2.add(jScrollPane1, "card2");

        javax.swing.GroupLayout textEditorJPanelLayout = new javax.swing.GroupLayout(textEditorJPanel);
        textEditorJPanel.setLayout(textEditorJPanelLayout);
        textEditorJPanelLayout.setHorizontalGroup(
            textEditorJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, textEditorJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(textEditorJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textEditorTittleJpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        textEditorJPanelLayout.setVerticalGroup(
            textEditorJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textEditorJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textEditorTittleJpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE))
        );

        containerJPanel.add(textEditorJPanel);

        jLabel1.setText("RESULTADOS");
        jPanel1.add(jLabel1);

        jPanel3.setLayout(new java.awt.CardLayout());

        resultJTextArea.setEditable(false);
        resultJTextArea.setColumns(20);
        resultJTextArea.setRows(5);
        jScrollPane2.setViewportView(resultJTextArea);

        jPanel3.add(jScrollPane2, "card2");

        javax.swing.GroupLayout logsJPanelLayout = new javax.swing.GroupLayout(logsJPanel);
        logsJPanel.setLayout(logsJPanelLayout);
        logsJPanelLayout.setHorizontalGroup(
            logsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logsJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(logsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        logsJPanelLayout.setVerticalGroup(
            logsJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logsJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        containerJPanel.add(logsJPanel);

        jMenu1.setText("Archivo");

        jMenuItem1.setText("Abrir");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Guardar");
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu4.setText("Compilador");

        runJMenuItem.setText("Ejecutar");
        jMenu4.add(runJMenuItem);

        jMenuBar1.add(jMenu4);

        jMenu2.setText("Acerca de");

        loadExampleJMenuItem.setText("Ejemplo");
        loadExampleJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadExampleJMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(loadExampleJMenuItem);

        jMenuItem3.setText("Info");
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(containerJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1112, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(containerJPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Updates current text area and automatically fill the data of a file to
     * display data
     *
     * @param evt
     */
    private void loadExampleJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadExampleJMenuItemActionPerformed
        // SEARCH EXMPLE TEXT
    }//GEN-LAST:event_loadExampleJMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel containerJPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    public javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JMenuItem loadExampleJMenuItem;
    private javax.swing.JPanel logsJPanel;
    public javax.swing.JTextArea resultJTextArea;
    public javax.swing.JMenuItem runJMenuItem;
    private javax.swing.JPanel textEditorJPanel;
    public javax.swing.JTextArea textEditorJTextArea;
    private javax.swing.JPanel textEditorTittleJpanel;
    private javax.swing.JLabel titleJLabel;
    // End of variables declaration//GEN-END:variables

    public void setup() {
        // configure sizes
        this.setTitle("PROYECTO ALGEBRA LINEAL : 2121323");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        // configure line counter
        JTextPane textPane = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(textPane);
        TextLineNumber tln = new TextLineNumber(textPane);
        scrollPane.setRowHeaderView(tln);
    }
}