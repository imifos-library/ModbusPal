/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * HISTORY:
 * - implementation of event "generatorHasBeenAdded"
 *
 * Created on 16 déc. 2008, 15:17:40
 */

package modbuspal.automation;

import modbuspal.main.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import modbuspal.generator.Generator;

/**
 *
 * @author nnovic
 */
public class AutomationPanel
extends javax.swing.JPanel
implements WindowListener, AutomationStateListener
{

    private Automation automation = null;
    private AutomationEditor automationEditor = null;
    private ModbusPalGui mainGui;

    /** Creates new form listOfSlavesCellRenderer */
    public AutomationPanel(ModbusPalGui gui, Automation parent)
    {
        mainGui = gui;
        automation = parent;
        automation.addAutomationStateListener(this);
        automationEditor = new AutomationEditor(mainGui,automation);
        automationEditor.addWindowListener(this);
        initComponents();
        setBackground();
    }


    public void disconnect()
    {
        automationEditor.removeWindowListener(this);
        automationEditor.setVisible(false);
        automationEditor.disconnect();
        assert(automation.removeAutomationStateListener(this)==false);
    }


    public Automation getAutomation()
    {
        return automation;
    }


    private void setBackground()
    {
        setBackground( automation.isRunning() );
    }

    private void setBackground(boolean running)
    {
        if( running )
        {
            setBackground( javax.swing.UIManager.getDefaults().getColor("Panel.background") );
        }
        else
        {
            setBackground( javax.swing.UIManager.getDefaults().getColor("List.background") );
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameTextField = new javax.swing.JTextField();
        playToggleButton = new javax.swing.JToggleButton();
        showToggleButton = new javax.swing.JToggleButton();
        deleteButton = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 3));

        nameTextField.setText(automation.getName());
        nameTextField.setPreferredSize(new java.awt.Dimension(120, 20));
        nameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nameTextFieldFocusLost(evt);
            }
        });
        add(nameTextField);

        playToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/modbuspal/automation/img/play.png"))); // NOI18N
        playToggleButton.setToolTipText("Start or stop this automation");
        playToggleButton.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/modbuspal/automation/img/stop.png"))); // NOI18N
        playToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playToggleButtonActionPerformed(evt);
            }
        });
        add(playToggleButton);

        showToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/modbuspal/automation/img/show.png"))); // NOI18N
        showToggleButton.setToolTipText("Show or hide the editor of this automation");
        showToggleButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        showToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showToggleButtonActionPerformed(evt);
            }
        });
        add(showToggleButton);

        deleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/modbuspal/automation/img/delete.png"))); // NOI18N
        deleteButton.setToolTipText("Delete this automation");
        deleteButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        add(deleteButton);
    }// </editor-fold>//GEN-END:initComponents

    private void showToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showToggleButtonActionPerformed
        if( showToggleButton.isSelected() == true )
        {
            automationEditor.setVisible(true);
        }
        else
        {
            automationEditor.setVisible(false);
        }
    }//GEN-LAST:event_showToggleButtonActionPerformed

    private void playToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playToggleButtonActionPerformed

        if( playToggleButton.isSelected()==true )
        {
            /* "Play" button has been pushed, it starts the automation
             * and the button becomes a "Stop" button. */
            automation.start();
        }
        else
        {
            automation.stop();
        }

    }//GEN-LAST:event_playToggleButtonActionPerformed

    private void nameTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTextFieldFocusLost
        String newName = nameTextField.getText().trim();
        newName = ModbusPal.checkAutomationNewName(automation, newName);
        nameTextField.setText(newName);
        automation.setName(newName);
    }//GEN-LAST:event_nameTextFieldFocusLost

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        ModbusPal.removeAutomation(automation);
    }//GEN-LAST:event_deleteButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JToggleButton playToggleButton;
    private javax.swing.JToggleButton showToggleButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void windowOpened(WindowEvent e)
    {
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
    }

    @Override
    public void windowClosed(WindowEvent e)
    {
        if( e.getSource() == automationEditor )
        {
            showToggleButton.setSelected(false);
        }
    }

    @Override
    public void windowIconified(WindowEvent e)
    {
    }

    @Override
    public void windowDeiconified(WindowEvent e)
    {
    }

    @Override
    public void windowActivated(WindowEvent e)
    {
    }

    @Override
    public void windowDeactivated(WindowEvent e)
    {
    }

    @Override
    public void automationHasEnded(Automation source)
    {
        //playToggleButton.setText("Start");
        playToggleButton.setSelected(false);
        setBackground(false);
    }

    @Override
    public void automationHasStarted(Automation aThis)
    {
        //playToggleButton.setText("Stop");
        playToggleButton.setSelected(true);
        setBackground(true);
    }

    @Override
    public void automationNameHasChanged(Automation aThis, String newName)
    {
    }

    @Override
    public void automationLoopEnabled(Automation aThis, boolean enabled)
    {
    }

    @Override
    public void automationStepHasChanged(Automation aThis, double step)
    {
    }

    @Override
    public void generatorHasBeenAdded(Automation source, Generator generator, int index)
    {
    }

    @Override
    public void generatorHasBeenRemoved(Automation source, Generator generator)
    {
    }

    @Override
    public void generatorsHaveBeenSwapped(Automation source, Generator g1, Generator g2)
    {
    }

    @Override
    public void automationInitialValueChanged(Automation aThis, double init)
    {
    }
}