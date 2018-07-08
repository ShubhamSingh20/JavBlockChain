/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jbcinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

/**
 *
 * @author Shubham
 */
public class JBCInterface extends JFrame{

    /**
     * @param args the command line arguments
     */
    public Document document;
    
    public ArrayList<String> commandHistory = new ArrayList<String>();
    public int commandHistoryLen = 0;
    public JBCInterface(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exception) {}
        
        Frame frame = new Frame();
        frame.setTitle("JBC Command Line");
        
        JTextPane jTextPane = new JTextPane();
        jTextPane.setEditable(false);
        jTextPane.setOpaque(true);
        jTextPane.setFont(new Font("Courier New", 12, 24));
        jTextPane.setBackground(Color.DARK_GRAY);
        jTextPane.setForeground(Color.GREEN);
        
        document = jTextPane.getStyledDocument();
        
        JTextField inputJTextField = new JTextField();
        inputJTextField.setFont(new Font("Courier New", 12, 24));
        inputJTextField.setEditable(true);
        inputJTextField.setCaretColor(Color.WHITE);
        inputJTextField.setForeground(Color.WHITE);
        inputJTextField.setBackground(Color.DARK_GRAY);
        inputJTextField.setOpaque(true);
        
        /*
          System.out.println(e.getActionCommand());
                {
                    
                }*/
        inputJTextField.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent event){
                if(event.getKeyCode() == KeyEvent.VK_ENTER){
                    String text = inputJTextField.getText();
                    commandHistory.add(text+"\n");
                    commandHistoryLen = commandHistory.size()-1;
                    inputJTextField.setText("");
                    printOnTextPane(text,jTextPane);
                    scrollBottom(jTextPane);
                }
                if (event.getKeyCode() == KeyEvent.VK_UP) {
                    if (commandHistoryLen >=0) {
                        inputJTextField.setText(commandHistory.get(commandHistoryLen--));
                    }
                }
                if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (commandHistoryLen <= commandHistory.size()) {
                        inputJTextField.setText(commandHistory.get(commandHistoryLen++));
                    }
                }
            }
        });
        JScrollPane frameJScrollPane = new JScrollPane(jTextPane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        frameJScrollPane.setBorder(null);
        frameJScrollPane.setOpaque(false);
        frameJScrollPane.getViewport().setOpaque(false);
        
        frame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
                // Close all activities and Close the frame on Click
                System.exit(0);
            }   
        });
        
        frame.add(inputJTextField,BorderLayout.SOUTH);
        frame.add(frameJScrollPane,BorderLayout.CENTER);
        frame.setSize(900,600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        
    }
    
    public void scrollBottom(JTextPane jTextPane){
        jTextPane.setCaretPosition(jTextPane.getDocument().getLength());
        
    }
    public void printOnTextPane(String displayString,JTextPane jTextPane){
        Style style = jTextPane.addStyle(displayString, null);
        displayString = displayString.replaceAll("\\s+","");
        StyleConstants.setForeground(style, Color.GREEN);
        
        try {
            document.insertString(document.getLength(), displayString+"\n", style);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        
    }
    public static void main(String[] args) {
        // TODO code application logic here
        new JBCInterface();
       
    }
    
}
