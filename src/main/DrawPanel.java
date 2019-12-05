/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import main.draw.IDrawer;
import main.draw.SimpleEdgePolygonDrawer;
import main.math.Vector3;
import main.screen.ScreenConverter;
import main.third.ICamera;
import main.third.LookAtCamera;
import main.third.Material;
import main.third.Scene;
import models.Parallelepiped;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Alexey
 */
public class DrawPanel extends JPanel
        implements CameraController.RepaintListener {
    private Scene scene;
    private ScreenConverter sc;
    private ICamera cam;
    private CameraController camController;

    public DrawPanel() {
        super();
        sc = new ScreenConverter(-1, 1, 2, 2, 1, 1);
        cam = new LookAtCamera();
        camController = new CameraController(cam, sc);
        scene = new Scene(Color.WHITE.getRGB());
        scene.showAxes();

        scene.getModelsList().add(new Parallelepiped(
                new Vector3(-5, 5, -5),
                new Vector3(5, 5, 5),
                new Material(Color.DARK_GRAY,0.5f)
        ));

        camController.addRepaintListener(this);
        addMouseListener(camController);
        addMouseMotionListener(camController);
        addMouseWheelListener(camController);
        addKeyListener(camController);
    }

    @Override
    public void paint(Graphics g) {
        sc.setScreenSize(getWidth(), getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) bi.getGraphics();
        IDrawer dr = new SimpleEdgePolygonDrawer(sc, graphics);
        scene.drawScene(dr, cam);
        g.drawImage(bi, 0, 0, null);
        graphics.dispose();
        /*for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(cam.getTranslate().getAt(i, j) + " ");
            }
            System.out.println();
        }*/
    }

    @Override
    public void shouldRepaint() {
        repaint();
    }
}
