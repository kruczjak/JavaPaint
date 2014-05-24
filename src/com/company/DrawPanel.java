package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kruczjak on 24.05.14.
 */
public class DrawPanel extends JPanel implements Externalizable{
    private List<Mouse> mouseList;
    private Mouse current;
    private Color color;
    private File lastFile;

    public DrawPanel()  {
        color = new Color(0,0,0);
        mouseList = new ArrayList<Mouse>();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                current = new Mouse(mouseEvent.getX(),mouseEvent.getY(), color);
                current.setLastX(mouseEvent.getX());
                current.setLastY(mouseEvent.getY());
                mouseList.add(current);
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                current.setLastX(mouseEvent.getX());
                current.setLastY(mouseEvent.getY());
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                current.setLastX(e.getX());
                current.setLastY(e.getY());
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Mouse one : mouseList) {
            g.setColor(one.getColor());
            g.drawLine(one.getX(),one.getY(),one.getLastX(),one.getLastY());
        }
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    public void clear() {
        mouseList.clear();
        repaint();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public File getLastFile() {
        return lastFile;
    }

    public void save(File file) throws IOException {
        lastFile = file;
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(this);
            oos.flush();
        } finally {
            if (oos != null) {
                oos.close();
            }
        }
    }
    public void open(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            DrawPanel drawPanel = (DrawPanel) ois.readObject();
            mouseList = drawPanel.mouseList;
            color = drawPanel.color;
            repaint();
        } finally {
            if (ois != null) {
                ois.close();
            }
        }
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeObject(mouseList);
        objectOutput.writeObject(color);
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        mouseList = (List<Mouse>) objectInput.readObject();
        color = (Color) objectInput.readObject();
    }
}
