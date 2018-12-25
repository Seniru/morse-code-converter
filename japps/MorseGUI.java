/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morsecoder;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import javax.swing.event.*;
import java.io.*;
import javax.swing.undo.*;

/**
 *
 * @author Seniru
 */
public class MorseCoder extends JFrame implements ActionListener, CaretListener, UndoableEditListener {

    JFrame gui = new JFrame("Morse Code Converter");
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    JMenuBar menu = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenu editMenu = new JMenu("Edit");
    JMenuItem newFile = new JMenuItem("Open File", new ImageIcon(new ImageIcon("images/open.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
    JMenuItem saveFile_txt = new JMenuItem("Save Text", new ImageIcon(new ImageIcon("images/save-txt.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
    JMenuItem saveFile_morse = new JMenuItem("Save Morse", new ImageIcon(new ImageIcon("images/save-morse.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
    JMenuItem undo = new JMenuItem("Undo", new ImageIcon(new ImageIcon("images/undo.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
    JMenuItem redo = new JMenuItem("Redo", new ImageIcon(new ImageIcon("images/redo.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
    JTextArea forTxt = new JTextArea((int) dim.getHeight() / 30 + 12, (int) dim.getWidth() / 35 + 25);
    JTextArea forMorse = new JTextArea((int) dim.getHeight() / 30 + 12, (int) dim.getWidth() / 35 + 25);
    FlowLayout flo = new FlowLayout(FlowLayout.CENTER, 5, 5);
    JButton submit = new JButton("Submit");
    JButton copy = new JButton("Copy the Morse Code");
    JButton newFileTool = new JButton(new ImageIcon(new ImageIcon("images/open.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
    JButton saveTxtTool = new JButton(new ImageIcon(new ImageIcon("images/save-txt.png").getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH)));
    JButton saveMorseTool = new JButton(new ImageIcon(new ImageIcon("images/save-morse.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
    JButton undoTool = new JButton(new ImageIcon(new ImageIcon("images/undo.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
    JButton redoTool = new JButton(new ImageIcon(new ImageIcon("images/redo.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
    MorseCodeConverter con = new morsecoder.MorseCodeConverter();
    Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
    JPanel stats = new JPanel();
    JPanel txtAreas = new JPanel();
    JPanel btns = new JPanel();
    JPanel toolBar = new JPanel();
    JPanel workArea = new JPanel();
    JLabel word = new JLabel("Word Count: 0    |    Line Count: 1   |  ");
    JLabel morse = new JLabel("Morse Code Count: 0  ");
    JScrollPane scroll = new JScrollPane(forTxt);
    FileDialog chooser = new FileDialog((Frame) null, "Select File to Open");
    File choosen;
    FileReader fis;
    BufferedWriter fop;
    JToolBar tools = new JToolBar();
    JToolBar editTools = new JToolBar();
    UndoManager undoMan = new UndoManager();
    KeyStroke undoKeyStroke = KeyStroke.getKeyStroke(
            KeyEvent.VK_Z, Event.CTRL_MASK);
    KeyStroke redoKeyStroke = KeyStroke.getKeyStroke(
            KeyEvent.VK_Y, Event.CTRL_MASK);
    public MorseCoder() {
        gui.setIconImage(new ImageIcon("images/logo.png").getImage().getScaledInstance(500, 500, 4));
        newFile.setAccelerator(KeyStroke.getKeyStroke('O', Event.CTRL_MASK));
        saveFile_txt.setAccelerator(KeyStroke.getKeyStroke('S', Event.ALT_MASK));
        saveFile_morse.setAccelerator(KeyStroke.getKeyStroke('S', Event.CTRL_MASK));
        morsecoder.DefaultContextMenu.addDefaultContextMenu(forTxt);
        undo.setAccelerator(undoKeyStroke);
        redo.setAccelerator(redoKeyStroke);
        gui.setLayout(new FlowLayout());
        forMorse.setLineWrap(true);
        forTxt.setLineWrap(true);
        forMorse.setWrapStyleWord(true);
        forMorse.setFont(forMorse.getFont().deriveFont(35));
        forTxt.setFont(forTxt.getFont().deriveFont(35));
        gui.setSize((int) dim.getWidth(), (int) dim.getHeight() - 30);
        gui.setVisible(true);
        fileMenu.add(newFile);
        saveFile_txt.setEnabled(false);
        saveFile_morse.setEnabled(false);
        fileMenu.add(saveFile_txt);
        fileMenu.add(saveFile_morse);
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem("Cancel"));
        editMenu.add(undo);
        editMenu.add(redo);
        editMenu.addSeparator();
        editMenu.add(new JMenuItem("Cancel"));
        undo.setEnabled(false);
        redo.setEnabled(false);
        menu.add(fileMenu);
        menu.add(editMenu);
        gui.setJMenuBar(menu);
        gui.setLayout(new BorderLayout());
        tools.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 3));
        tools.add(newFileTool);
        newFileTool.setToolTipText("Open File");
        tools.add(saveTxtTool);
        saveTxtTool.setToolTipText("Save Text");
        tools.add(saveMorseTool);
        saveMorseTool.setToolTipText("Save Morse");
        saveTxtTool.setEnabled(false);
        saveMorseTool.setEnabled(false);
        editTools.setLayout(new FlowLayout(FlowLayout.LEFT,6,3));
        editTools.add(undoTool);
        editTools.add(redoTool);
        undoTool.setEnabled(false);
        redoTool.setEnabled(false);
        undoTool.setToolTipText("Undo");
        redoTool.setToolTipText("Redo");
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 3));
        toolBar.add(editTools, FlowLayout.LEFT);
        toolBar.add(tools, FlowLayout.LEFT);
        gui.add(toolBar, BorderLayout.PAGE_START);
        txtAreas.add(forTxt);
        txtAreas.add(forMorse);
        workArea.add(txtAreas, BorderLayout.NORTH);
        btns.add(submit);
        btns.add(copy);
        workArea.add(btns);
        forMorse.setAutoscrolls(true);
        stats.setBackground(new Color(200, 200, 200));
        stats.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
        stats.add(word);
        stats.add(morse);
        gui.add(workArea);
        gui.add(stats, BorderLayout.SOUTH);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        submit.addActionListener(this);
        copy.addActionListener(this);
        forTxt.addCaretListener(this);
        forMorse.addCaretListener(this);
        newFile.addActionListener(this);
        undo.addActionListener(this);
        redo.addActionListener(this);
        saveFile_txt.addActionListener(this);
        saveFile_morse.addActionListener(this);
        forTxt.getDocument().addUndoableEditListener(this);
        newFileTool.addActionListener((ActionEvent e) -> {
            openFile();
        });
        saveTxtTool.addActionListener((ActionEvent e) -> {
            saveFile("text");
        });
        saveMorseTool.addActionListener((ActionEvent e) -> {
            saveFile("morse");
        });
        undoTool.addActionListener((ActionEvent e) -> {
            undoMan.undo();
        });
        redoTool.addActionListener((ActionEvent e) -> {
            undoMan.redo();
        });
        forTxt.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(undoKeyStroke, "undoKeyStroke");
        forTxt.getActionMap().put("undoKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    undoMan.undo();
                } catch (CannotUndoException cue) {
                }
            }
        });
        // Map redo action
        forTxt.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(redoKeyStroke, "redoKeyStroke");
        forTxt.getActionMap().put("redoKeyStroke", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    undoMan.redo();
                } catch (CannotRedoException cre) {
                }
            }
        });

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
        }

    }

    public static void main(String[] args) {
        new MorseCoder();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Submit":
                forMorse.setText(MorseCodeConverter.convert(forTxt.getText()));
                break;
            case "Copy the Morse Code":
                clip.setContents(new StringSelection(forMorse.getText()), new StringSelection(forMorse.getText()));
                break;
            case "Open File":
                openFile();
                break;
            case "Save Text":
                saveFile("text");
                break;
            case "Save Morse":
                saveFile("morse");
                break;
            case "Undo":
                undoMan.undo();
                break;
            case "Redo":
                undoMan.redo();
                break;
            default:
                break;
        }

    }

    @Override
    public void caretUpdate(CaretEvent e) {
        word.setText("Word Count: " + forTxt.getText().length() + "    |    Line Count: " + forTxt.getLineCount());
        morse.setText("Morse Code Count: " + forMorse.getText().length());
        saveFile_txt.setEnabled(forTxt.getText().length() > 0);
        saveFile_morse.setEnabled(forMorse.getText().length() > 0);
        saveTxtTool.setEnabled(forTxt.getText().length() > 0);
        saveMorseTool.setEnabled(forMorse.getText().length() > 0);
        undo.setEnabled(undoMan.canUndo());
        redo.setEnabled(undoMan.canRedo());
        undoTool.setEnabled(undoMan.canUndo());
        redoTool.setEnabled(undoMan.canRedo());
    }

    @Override
    public void undoableEditHappened(UndoableEditEvent e) {
        undoMan.addEdit(e.getEdit());
    }

    public void openFile() {
        chooser.setMode(FileDialog.LOAD);
        chooser.setVisible(true);
        choosen = new File(chooser.getDirectory() + "" + chooser.getFile());

        try {
            fis = new FileReader(choosen);
            //System.out.println(choosen);
            if (!choosen.toString().equals("nullnull")) {
                var i = 0;
                word.setText("");
                forTxt.setText("");
                while ((i = fis.read()) != -1) {
                    forTxt.setText(forTxt.getText() + (char) i);
                }
                forMorse.setText(MorseCodeConverter.convert(forTxt.getText()));
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "File Not Found", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "Error Occured while Opening the File", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void saveFile(String type) {
        chooser.setMode(FileDialog.SAVE);
        chooser.setVisible(true);
        try {
            try {
                fop = new BufferedWriter(new FileWriter(chooser.getDirectory() + "" + chooser.getFile()));
                fop.write("text".equals(type) ? forTxt.getText() : forMorse.getText());
                JOptionPane.showMessageDialog(null, "File Saved: " + chooser.getDirectory() + chooser.getFile(), "Info", JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "File Not Found", "Error", JOptionPane.ERROR_MESSAGE);

            } finally {
                fop.close();
            }
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "Error Occured while Saving the File", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
