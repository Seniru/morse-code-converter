/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package japps;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import jApps.MorseCodeConverter;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import javax.swing.event.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Seniru
 */
public class MorseGUI extends JFrame implements ActionListener, CaretListener {
    JFrame gui = new JFrame("Morse Code Converter");
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    JMenuBar menu = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem newFile = new JMenuItem("Open File");
    JTextArea forTxt = new JTextArea((int)dim.getHeight()/30+15,(int)dim.getWidth()/35+25);
    JTextArea forMorse = new JTextArea((int)dim.getHeight()/30+15,(int)dim.getWidth()/35+25);
    FlowLayout flo = new FlowLayout(FlowLayout.CENTER,5,5);
    JButton submit = new JButton("Submit");
    JButton copy = new JButton("Copy the Morse Code");
    MorseCodeConverter con = new jApps.MorseCodeConverter();
    Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
    JPanel stats = new JPanel();
    JPanel txtAreas = new JPanel();
    JPanel btns = new JPanel();
    JLabel word = new JLabel("Word Count: 0    |    Line Count: 1  |  ");
    JLabel morse = new JLabel("Morse Code Count: 0  ");
    JScrollPane scroll = new JScrollPane(forMorse,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    FileDialog chooser = new FileDialog((Frame)null,"Select File to Open");
    File choosen;
    FileReader fis;
    
    public MorseGUI() {
      forMorse.setLineWrap(true);
      forTxt.setLineWrap(true); 
      forMorse.setWrapStyleWord​(true);	     
      forMorse.setFont(forMorse.getFont().deriveFont(35));
      forTxt.setFont(forTxt.getFont().deriveFont(35));
      gui.setSize((int)dim.getWidth(),(int)dim.getHeight()-30);
      gui.setVisible(true);
      fileMenu.add(newFile);
      //fileMenu.add(chooser);
      menu.add(fileMenu);
      gui.setJMenuBar(menu);
      gui.setLayout(new BorderLayout());
      txtAreas.add(forTxt);
      txtAreas.add(forMorse);
      gui.add(txtAreas,BorderLayout.NORTH);
      //gui.add(chooser);
      btns.add(submit);
      btns.add(copy);
      gui.add(btns);
      forMorse.setAutoscrolls(true);
      stats.setBackground(new Color(200,200,200));
      stats.setLayout(new FlowLayout(FlowLayout.LEFT,20,5));
      stats.add(word);
      stats.add(morse);
      gui.add(stats,BorderLayout.SOUTH);
      gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      submit.addActionListener(this);
      copy.addActionListener(this);
      forTxt.addCaretListener(this);
      forMorse.addCaretListener(this);
      newFile.addActionListener(this);
      

      try {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

      } catch(Exception e) {};
      
    }
    public static void main(String[] args) {
      new MorseGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {        
       if(e.getActionCommand()=="Submit") {
       forMorse.setText(MorseCodeConverter.convert(forTxt.getText()));
       } else if(e.getActionCommand()=="Copy the Morse Code") {
        clip.setContents(new StringSelection(forMorse.getText()),new StringSelection(forMorse.getText()));  
       } else if(e.getActionCommand()=="Open File") {
           chooser.setMode(FileDialog.LOAD);
           chooser.setVisible(true);
           choosen = new File(chooser.getDirectory()+""+chooser.getFile());
           
           try {
               fis = new FileReader(choosen);
               var i = 0;
               word.setText("");
                while ((i=fis.read()) != -1) 
                    forTxt.setText(forTxt.getText()+(char) i); 
                forMorse.setText(MorseCodeConverter.convert(forTxt.getText()));
           } catch (FileNotFoundException ex) {
              JOptionPane.showMessageDialog(null,"File Not Found","Error",JOptionPane.ERROR_MESSAGE);
           } catch (IOException ioe) {
              JOptionPane.showMessageDialog(null,"Error Occured while Reading The File","Error",JOptionPane.ERROR_MESSAGE);

           }
       }
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        word.setText("Word Count: "+forTxt.getText().length()+"    |    Line Count: "+forTxt.getLineCount());
       morse.setText("Morse Code Count: "+forMorse.getText().length());

    }
    
    
}
