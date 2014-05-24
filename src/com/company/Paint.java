package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by kruczjak on 24.05.14.
 */
public class Paint {
    private JPanel panel;
    private JButton color;
    private DrawPanel drawPanel;
    private JButton clear;
    private JMenuBar menuBar;
    private JMenu menu;

    public Paint() {
        init();
    }

    private void init() {
        color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawPanel.setColor(JColorChooser.showDialog(null,"Wybierz kolor",drawPanel.getColor()));
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawPanel.clear();
            }
        });
    }

    private JMenuBar initMenu() {
        menuBar = new JMenuBar();
        menu = new JMenu("Plik");
        menu.setMnemonic(KeyEvent.VK_P);
        menuBar.add(menu);

        JMenuItem menuItem = new JMenuItem("Nowy",KeyEvent.VK_N);
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawPanel.clear();
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Otwórz", KeyEvent.VK_O);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        drawPanel.open(file);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(),"Błąd", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                    }
                }
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Zapisz", KeyEvent.VK_Z);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (drawPanel.getLastFile()==null)  {
                    saveDialog();
                }   else    {
                    try {
                        drawPanel.save(drawPanel.getLastFile());
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Błąd", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                    }
                }
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Zapisz jako...", KeyEvent.VK_A);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveDialog();
            }
        });
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem("Exit",KeyEvent.VK_X);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        menu.add(menuItem);
        return menuBar;
    }

    public void saveDialog()    {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                drawPanel.save(file);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),"Błąd", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Paint");
        Paint paint = new Paint();
        frame.setMinimumSize(new Dimension(640,480));
        frame.setContentPane(paint.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(paint.initMenu());
        frame.pack();
        frame.setVisible(true);
    }
}