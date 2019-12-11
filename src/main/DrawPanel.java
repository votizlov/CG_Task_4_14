/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import main.draw.IDrawer;
import main.draw.SimpleEdgePolygonDrawer;
import main.math.Vector3;
import main.screen.ScreenConverter;
import main.third.*;
import models.Circle;
import models.CylinderFromPolyLine3D;
import models.Parallelepiped;
import models.Tor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

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
        cam = new Camera();
        camController = new CameraController(cam, sc);
        scene = new Scene(Color.WHITE.getRGB());
        scene.showAxes();
/*
        scene.getModelsList().add(new CylinderFromPolyLine3D(
                new PolyLine3D(Arrays.asList(
                        new Vector3(5, 0, 0),
                        new Vector3(4, 1, 0),
                        new Vector3(3, 2, 0),
                        new Vector3(2, 3, 0),
                        new Vector3(1, 4, 0),
                        new Vector3(0, 5, 0),
                        new Vector3(-1, 4, 0),
                        new Vector3(-2, 3, 0),
                        new Vector3(-3, 2, 0),
                        new Vector3(-4, 1, 0),
                        new Vector3(-5, 0, 0),
                        new Vector3(-4, -1, 0),
                        new Vector3(-3, -2, 0),
                        new Vector3(-2, -3, 0),
                        new Vector3(-1, -4, 0),
                        new Vector3(0, -5, 0),
                        new Vector3(1, -4, 0),
                        new Vector3(2, -3, 0),
                        new Vector3(3, -2, 0),
                        new Vector3(4, -1, 0),
                        new Vector3(5,0,0)
                ), true), 1, 10
        ));*/
/*
        scene.getModelsList().add(new CylinderFromPolyLine3D(
                new PolyLine3D(Arrays.asList(
                        new Vector3(0, 0, 0),
                        new Vector3(1, 1, 1),
                        new Vector3(2, 2, 2),
                        new Vector3(3, 3, 3),
                        new Vector3(4, 4, 4)

                ), true), 1, 500));
*/
scene.getModelsList().add(new Tor(new Vector3(0,0,0),2,0,200));
        //scene.getModelsList().add(new Circle(new Vector3(-1,-1,-1),new Vector3(1,1,1),new Vector3(11,1,11),100));

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
    }

    @Override
    public void shouldRepaint() {
        repaint();
    }
}
