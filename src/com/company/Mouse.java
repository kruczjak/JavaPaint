package com.company;

import java.awt.*;
import java.io.*;

/**
 * Created by kruczjak on 24.05.14.
 */
public class Mouse implements Externalizable {
    private int x;
    private int y;
    private int lastX;
    private int lastY;
    private Color color;

    public Mouse() {}

    public Mouse(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void setLastX(int lastX) {
        this.lastX = lastX;
    }

    public void setLastY(int lastY) {
        this.lastY = lastY;
    }

    public int getLastX() {
        return lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeInt(x);
        objectOutput.writeInt(y);
        objectOutput.writeInt(lastX);
        objectOutput.writeInt(lastY);
        objectOutput.writeObject(color);
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        x = objectInput.readInt();
        y = objectInput.readInt();
        lastX = objectInput.readInt();
        lastY = objectInput.readInt();
        color = (Color) objectInput.readObject();
    }
}
