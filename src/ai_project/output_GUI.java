/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_project;

import java.awt.Color;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author Abdulwahab
 */
public class output_GUI extends javax.swing.JFrame {

    public static output_GUI mainFrame;
    
    public int allTasksNum;
    public int numOfPop;
    
    boolean firstTime_run = true;
    
    /**
     * Creates new form output_GUI
     */
    public output_GUI() {
        initComponents();
   
        comboBox.removeAllItems();
        comboBox.addItem("instance 1");
        comboBox.addItem("instance 2");
        comboBox.addItem("instance 3");
        
        this.titlePanel.setBackground(Color.LIGHT_GRAY);
        
      runOutput_GUI();

    }
    
    public void runOutput_GUI()
    {
        
        
       // -------------------------------------------------- top panel info
        this.NumOfTasks.setText(allTasksNum+"");
        this.GeneratedPop.setText(numOfPop+"");
        this.Tasks_.setText("Tasks");
        this.populations_.setText("population");
        
        
        this.talkbeforeTasks.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        this.talkBeforePop.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        this.NumOfTasks.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        this.GeneratedPop.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        this.Tasks_.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        this.populations_.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        // --------------------------------------------------
        
        
        // ------------------------------------------------------------------------------------------------------------------ main panel info

        // create panel as number of populations
        int size = Integer.parseInt(this.GeneratedPop.getText()); 
        JPanel [] panels = new JPanel[size];
        
        this.mainPanel.setLayout(new GridLayout(3,1, 0, 10));
        
        
        JLabel [] firstTalkInPanel = new JLabel[size];
        JLabel [] lastTalkInPanel = new JLabel[size];
        
        // for each processor and it's tasks there is a panel
        JPanel [] subPanels = new JPanel[2]; 
        
        for(int i=0; i<panels.length; i++)
        {
          // ........ the two processors in each population
           JLabel p1 = new JLabel("1st processor");
           JLabel p2 = new JLabel("2nd processor");

           p1.setFont(new Font("Serif", Font.PLAIN, 14));
           p1.setBorder(new EmptyBorder(10,10,10,10));  // top, left, bottom, right  (for padding)
           p1.setForeground(Color.black); // text color
           p1.setBackground(Color.orange);
           p1.setOpaque(true);

           p2.setFont(new Font("Serif", Font.PLAIN, 14));
           p2.setBorder(new EmptyBorder(10,10,10,10));  // top, left, bottom, right  (for padding)
           p2.setForeground(Color.black); // text color
           p2.setBackground(Color.orange);
           p2.setOpaque(true);
          // ........   
          
          
          // create sub panels
         
                subPanels[0] = new JPanel();
                subPanels[1] = new JPanel();
                
                // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~ add the first processor with it's tasks in subPanel[0]
                subPanels[0].add(p1);  

                JLabel [] labels_1 = new JLabel[Main.populations.get(i).getBestSchedule().getP1_size()]; // create the tasks for the first processor
                for(int j=0; j<labels_1.length; j++)
                {
                  labels_1[j] = new JLabel("T" + Main.populations.get(i).getBestSchedule().getP1_tasks()[j].getId());
                  labels_1[j].setFont(new Font("Serif", Font.PLAIN, 14));
                  
                  labels_1[j].setBorder(new EmptyBorder(10,10,10,10));  // top, left, bottom, right  (for padding)

                  labels_1[j].setForeground(Color.black); // text color
                  labels_1[j].setBackground(Color.CYAN);
                  labels_1[j].setOpaque(true);

                  subPanels[0].add(labels_1[j]);
                }
                // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~

                // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~ add the second processor with it's tasks in subPanel[1]
                subPanels[1].add(p2);  

                JLabel [] labels_2 = new JLabel[Main.populations.get(i).getBestSchedule().getP2_size()]; // create the tasks for the second processor
                for(int j=0; j<labels_2.length; j++)
                {
                  labels_2[j] = new JLabel("T" + Main.populations.get(i).getBestSchedule().getP2_tasks()[j].getId());
                  labels_2[j].setFont(new Font("Serif", Font.PLAIN, 14));
                  
                  labels_2[j].setBorder(new EmptyBorder(10,10,10,10));  // top, left, bottom, right  (for padding)

                  labels_2[j].setForeground(Color.black); // text color
                  labels_2[j].setBackground(Color.CYAN);
                  labels_2[j].setOpaque(true);

                  subPanels[1].add(labels_2[j]);
                }
                // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
          
          
          // create the panel
          panels[i] = new JPanel();

          // panels layout
          panels[i].setLayout(new GridLayout(4,1, 0, 10));
          
          
          firstTalkInPanel[i] = new JLabel("Best Schedule in Population " + (i+1));
          firstTalkInPanel[i].setBorder(new EmptyBorder(8,8,8,8));  // top, left, bottom, right  (for padding)
          firstTalkInPanel[i].setForeground(Color.white); // text color
          firstTalkInPanel[i].setBackground(Color.GRAY);
          firstTalkInPanel[i].setOpaque(true);
          firstTalkInPanel[i].setFont(new Font("Serif", Font.PLAIN, 20));
          
          lastTalkInPanel[i] = new JLabel("Schedule Finish Time : " + Main.populations.get(i).getBestSchedule().getFinishTime());
          lastTalkInPanel[i].setBorder(new EmptyBorder(8,8,8,8));  // top, left, bottom, right  (for padding)
          lastTalkInPanel[i].setForeground(Color.WHITE); // text color
          lastTalkInPanel[i].setBackground(Color.BLACK);
          lastTalkInPanel[i].setOpaque(true);
          lastTalkInPanel[i].setFont(new Font("Serif", Font.BOLD, 20));
          
          panels[i].add(firstTalkInPanel[i]);
          panels[i].add(subPanels[0]);
          panels[i].add(subPanels[1]);
          panels[i].add(lastTalkInPanel[i]);
          
          if(Main.populations.size() > 1)
          {
            this.populations_.setText("populations");
          }
          
          this.mainPanel.add(panels[i]);
        }
        // ----------------------------------------------------------------------------------------------------------------    
                 
          this.revalidate();
          this.repaint();
    }

    public void clearOutput_GUI()
    {
      this.NumOfTasks.setText("");
      this.GeneratedPop.setText("");
      this.Tasks_.setText("");
      this.populations_.setText("");  
      
      this.mainPanel.removeAll();
      this.mainPanel.revalidate();
      this.mainPanel.repaint();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlePanel = new javax.swing.JPanel();
        talkbeforeTasks = new javax.swing.JLabel();
        talkBeforePop = new javax.swing.JLabel();
        NumOfTasks = new javax.swing.JLabel();
        GeneratedPop = new javax.swing.JLabel();
        Tasks_ = new javax.swing.JLabel();
        populations_ = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        runBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        comboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        titlePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        talkbeforeTasks.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        talkbeforeTasks.setText("Number of tasks to schedule are :");

        talkBeforePop.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        talkBeforePop.setText("Best generated populations :");

        NumOfTasks.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        NumOfTasks.setText("0");

        GeneratedPop.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        GeneratedPop.setText("0");

        Tasks_.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        Tasks_.setText("Tasks");

        populations_.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        populations_.setText("population");

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(titlePanelLayout.createSequentialGroup()
                        .addComponent(talkBeforePop)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GeneratedPop, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(populations_, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(titlePanelLayout.createSequentialGroup()
                        .addComponent(talkbeforeTasks)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NumOfTasks, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Tasks_, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(talkbeforeTasks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(NumOfTasks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Tasks_))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(talkBeforePop, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(GeneratedPop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(populations_, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        mainPanel.setForeground(new java.awt.Color(240, 240, 240));

        runBtn.setFont(new java.awt.Font("Serif", 0, 18)); // NOI18N
        runBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ai_project/runIcon.png"))); // NOI18N
        runBtn.setText(" Run");
        runBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runBtnActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Mongolian Baiti", 0, 18)); // NOI18N
        jLabel5.setText("Choose the input file :");

        comboBox.setFont(new java.awt.Font("Mongolian Baiti", 0, 18)); // NOI18N
        comboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboBox, 0, 1086, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(runBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(runBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(comboBox)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(titlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxActionPerformed
 
    }//GEN-LAST:event_comboBoxActionPerformed

   
    
    private void runBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runBtnActionPerformed
        
        int selectedFileIndex = comboBox.getSelectedIndex();
        String inputFilePath = "";
       
          switch(selectedFileIndex)
          {
                case 0:
                    inputFilePath = "instance1.txt"; 
                    break;
                case 1:
                    inputFilePath = "instance2.txt"; 
                    break;
                case 2:
                    inputFilePath = "instance3.txt"; 
                    break;    
          }
          
     
          if(!firstTime_run)  // press the run button the second time and above
          {
              
            clearOutput_GUI();  
  
            Main.allTasks = null;
            Main.populations.removeAll(Main.populations);
          }
 
           Main.runMainFunc(inputFilePath);
           allTasksNum = Main.allTasks.length;
           numOfPop = Main.populations.size();       
    
           runOutput_GUI();
                 
           firstTime_run = false;
           
    }//GEN-LAST:event_runBtnActionPerformed

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
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(output_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(output_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(output_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(output_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                          
                
            }
        });
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel GeneratedPop;
    private javax.swing.JLabel NumOfTasks;
    private javax.swing.JLabel Tasks_;
    private javax.swing.JComboBox<String> comboBox;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel populations_;
    private javax.swing.JButton runBtn;
    private javax.swing.JLabel talkBeforePop;
    private javax.swing.JLabel talkbeforeTasks;
    private javax.swing.JPanel titlePanel;
    // End of variables declaration//GEN-END:variables
}
