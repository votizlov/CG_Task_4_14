/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.draw;

import main.third.PolyLine3D;

import java.util.Collection;


public interface IDrawer {
    /**
     * Очищает область заданным цветом
     * @param color цвет
     */
    public void clear(int color);
    
    /**
     * Рисует все полилинии
     * @param polyline набор рисуемых полилиний.
     */
    public void draw(Collection<PolyLine3D> polyline);

    void drawPolygons();

    void drawCounters();
}
