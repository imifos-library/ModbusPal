/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AppConsole.java
 *
 * Created on 10 avr. 2009, 15:50:12
 */

package modbuspal.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 *
 * @author avincon
 */
public class AppConsole
extends javax.swing.JDialog
{
    private Redirector outRedirect;
    private Redirector errRedirect;
    private StyledDocument consoleDoc;

    class Redirector implements Runnable
    {
        private PipedInputStream pin;
        private PipedOutputStream pout;
        private Thread thread;

        Redirector() throws IOException
        {
            // create a piped input stream
            pin = new PipedInputStream();
            pout = new PipedOutputStream(pin);
            thread = new Thread(this);
            thread.start();
        }

        OutputStream getOutputStream()
        {
            return pout;
        }

        @Override
        public void run()
        {
            InputStreamReader isr = new InputStreamReader(pin);
            BufferedReader br = new BufferedReader(isr);
            while(true)
            {
                try
                {
                    String line = br.readLine();
                    consoleDoc.insertString( consoleDoc.getLength(), line+"\r\n", null);
                }
                catch (IOException ex)
                {
                    //Logger.getLogger(AppConsole.class.getName()).log(Level.SEVERE, null, ex);
                    //return;
                }
                catch (BadLocationException ex)
                {
                    //Logger.getLogger(AppConsole.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
            }

//            try
//            {
//                pin.close();
//                pout.close();
//            }
//            catch(IOException ex)
//            {
//
//            }
        }
    }


    /** Creates new form AppConsole */
    public AppConsole(java.awt.Frame parent) throws IOException
    {
        super(parent, false);
        initComponents();
        consoleDoc = consoleTextPane.getStyledDocument();
        redirect();
    }


    private void redirect() throws IOException
    {
        // create
        outRedirect = new Redirector();
        System.setOut( new PrintStream(outRedirect.getOutputStream(),true) );

        errRedirect = new Redirector();
        System.setErr( new PrintStream(errRedirect.getOutputStream(),true) );
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        consoleTextPane = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Console");

        consoleTextPane.setEditable(false);
        consoleTextPane.setPreferredSize(new java.awt.Dimension(350, 200));
        jScrollPane1.setViewportView(consoleTextPane);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane consoleTextPane;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}