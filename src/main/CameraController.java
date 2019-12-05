/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import main.math.Matrix4Factories;
import main.math.Vector3;
import main.math.Vector4;
import main.screen.ScreenConverter;
import main.screen.ScreenPoint;
import main.third.Camera;
import main.third.ICamera;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;


public class CameraController implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
    @Override
    public void keyTyped(KeyEvent keyEvent) {
        System.out.println("a");
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        System.out.println("a");
        switch (keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                camera.modifyCameraPos(new Vector3(1,0,0));
            break;
            case KeyEvent.VK_S:
                camera.modifyCameraPos(new Vector3(0,1,0));
            break;
            case KeyEvent.VK_D:
                camera.modifyCameraPos(new Vector3(0,0,1));
            break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    /*=============== Начало паттерна "слушатель" ==================*/
    /* Реализация паттерна слушатель для оповещения всех желающих о каком-либо событии */
    
    /* Для начала требуется объявить интерфейс, в котором будут указаны используемы для оповещения методы.
     * В данном случае будет объявлен один метод, который будет вызываться тогда,
     * когда изменится состояние камеры и надо перерисовать экран
     */
    
    /**
     * Интерфейс, объявляющий набор метод, которые обязан реализовать слушатель
     */
    public static interface RepaintListener {
        /**
         * Метод, вызываемый при изменении
         */
        void shouldRepaint();
    }
    
    
    /* Далее описывается приватная коллекция, в данном случае - Set, 
     * где будет хрнаиться список всех слушателей, подписанных на данное событие.
     */
    private Set<RepaintListener> listeners = new HashSet<>();
    
    /**
     * Метод добавления слушателя
     * @param listener слушатель
     */
    public void addRepaintListener(RepaintListener listener) {
        listeners.add(listener);
    }
    
    /**
     * Метод удаления слушателя
     * @param listener слушатель
     */
    public void removeRepaintListener(RepaintListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * Вспомогательный метод, который оповещает всех слушателей о произошедшем событии.
     */
    protected void onRepaint() {
        for (RepaintListener cl : listeners)
            cl.shouldRepaint();
    }
    
    /*=============== Конец паттерна "слушатель" ==================*/
    
    private ICamera camera;
    private ScreenConverter sc;

    public CameraController(ICamera camera, ScreenConverter sc) {
        this.camera = camera;
        this.sc = sc;
    }

    public ICamera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public ScreenConverter getSc() {
        return sc;
    }

    public void setSc(ScreenConverter sc) {
        this.sc = sc;
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    /*Здесь запоминаем последнее положение мыши, для которого обрабатывали событие*/
    private Point last;
    /*Флаг, фиксирующий, зажата ли сейчас левая кнопка мыши*/
    private boolean leftFlag = false;
    /*Флаг, фиксирующий, зажата ли сейчас правая кнопка мыши*/
    private boolean rightFlag = false;
    /*Флаг, фиксирующий, зажата ли сейчас средняя кнопка мыши*/
    private boolean middleFlag = false;
    
    @Override
    public void mousePressed(MouseEvent e) {
        /*Устанавливаем флаги кнопок мыши*/
        if (SwingUtilities.isLeftMouseButton(e))
            leftFlag = true;
        if (SwingUtilities.isRightMouseButton(e))
            rightFlag = true;
        if (SwingUtilities.isMiddleMouseButton(e))
            middleFlag = true;
        last = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        /*Снимаем флаги кнопок мыши*/
        if (SwingUtilities.isLeftMouseButton(e))
            leftFlag = false;
        if (SwingUtilities.isRightMouseButton(e))
            rightFlag = false;
        if (SwingUtilities.isMiddleMouseButton(e))
            middleFlag = false;
        
        /*Если оба сняты, то забываем точку*/
        if (!leftFlag && !rightFlag && !middleFlag)
            last = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point current = e.getPoint();
        if (last != null) {
            /*Вычисляем разницу в пикселях*/
            int dx = current.x - last.x;
            int dy = current.y - last.y;
            /*Если двигаем с зажатой левой кнопкой мыши, то вращаем камеру*/
            if (leftFlag) {
                double da = dx * Math.PI / 180;
                double db = dy * Math.PI / 280;
                camera.modifyRotate(
                        Matrix4Factories.rotationXYZ(da, Matrix4Factories.Axis.Y)
                    .mul(
                        Matrix4Factories.rotationXYZ(db, Matrix4Factories.Axis.X)
                    )
                );

                camera.modifyCameraPos(new Vector3(1,1,1));
            }
            /*Если двигаем с зажатой правой кнопкой мыши, то перемещаем камеру вдоль осей X и Y*/
            if (rightFlag) {
                Vector4 zero = new Vector4(sc.s2r(new ScreenPoint(0, 0)), 0);
                Vector4 cur = new Vector4(sc.s2r(new ScreenPoint(dx, dy)), 0);
                
                /*Вектор смещения в реальных координатах с точки зрения камеры*/
                Vector3 delta = cur.add(zero.mul(-1)).asVector3();
                camera.modifyTranslate(Matrix4Factories.translation(delta));
            }
            /* Если двигаем с зажатой средней кнопкой мыши, то перемещаем камеру 
             * вдоль оси Z на расстояние равное изменению положения мыши в реальных координатах.
             * Направление выбирается положительное при движении вверх.
             */
            if (middleFlag && dy != 0) {
                Vector4 zero = new Vector4(sc.s2r(new ScreenPoint(0, 0)), 0);
                Vector4 cur = new Vector4(sc.s2r(new ScreenPoint(dx, dy)), 0);
                /*Длина вектор смещения в реальных координатах с точки зрения камеры*/
                float length = cur.add(zero.mul(-1)).asVector3().length();
                if (dy < 0)
                    length = -length;
                //System.out.println(length); todo чтоб не потерять важный дебаг
                camera.modifyTranslate(Matrix4Factories.translation(0, 0, length));
            }
        }
        last = current;
        onRepaint(); /*Оповещаем всех, что мы изменили камеру и её надо перерисовать*/
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int delta = e.getWheelRotation();
        /*Если зажат Control, то будем менять параметры перспективы, иначе - масштаба*/
        if (e.isControlDown()) {
            /*delta*5f - экспериментально подобранное число. Чем меньше, тем быстрее будет изменяться точка схода*/
            camera.modifyProjection(Matrix4Factories.centralProjection(delta*5f, Matrix4Factories.Axis.Z));
        } else {
            /*Вычислим коэффициент масштаба*/
            float factor = 1;
            float scale = delta < 0 ? 0.9f : 1.1f;
            int counter = delta < 0 ? -delta : delta;
            while (counter-- > 0)
                factor *= scale;
            camera.modifyScale(Matrix4Factories.scale(factor));
        }
        onRepaint();
    }
    
}
