/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javax.swing.*;

/**
 *
 * @author Alexey
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600, 600);
        frame.setUndecorated(true);
        frame.requestFocus();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new DrawPanel());
        frame.setVisible(true);

        JFrame UIFrame = new JFrame();
        UIFrame.setSize(200, 600);
        UIFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        UIFrame.add(new JPanel());
        UIFrame.setVisible(true);
    }
}
