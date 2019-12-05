/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.third;

import main.math.Vector3;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.PI;
import static java.lang.Math.cos;


public class PolyLine3D {
    private List<Vector3> points;
    private boolean closed;

    /**
     * Создаёт новую полилинию на основе трёхмерных точек.
     *
     * @param points список точек-вершин ломанной
     * @param closed признак замкнутостит линии
     */
    public PolyLine3D(Collection<Vector3> points, boolean closed) {
        this.points = new LinkedList<Vector3>(points);
        this.closed = closed;
    }

    /**
     * Признак закрытости
     *
     * @return возвращает истину, если линия замкнута, иначе - ложь.
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * Список вершин линии
     *
     * @return возвращает список точек.
     */
    public List<Vector3> getPoints() {
        return points;
    }

    /**
     * Вычисляет среднее арифметическое по оси Z.
     *
     * @return среднее по Z для полилинии.
     */
    public float avgZ() {
        if (points == null || points.size() == 0)
            return 0;
        float sum = 0;
        for (Vector3 v : points)
            sum += v.getZ();
        return sum / points.size();
    }

    public boolean checkRayCollision(Vector3 point,float precision) {// https://www.tutorialspoint.com/computer_graphics/computer_graphics_surfaces.htm
        /*if(points.size()!=4) {
            double a = new Matrix3(new float[][]{{1, points.get(0).getY(), points.get(0).getZ()}, {1, points.get(1).getY(), points.get(1).getZ()}, {1, points.get(2).getY(), points.get(2).getZ()}}).det();
            double b = new Matrix3(new float[][]{{1, points.get(0).getY(), points.get(0).getZ()}, {1, points.get(1).getY(), points.get(1).getZ()}, {1, points.get(2).getY(), points.get(2).getZ()}}).det();
            double c = new Matrix3(new float[][]{{1, points.get(0).getY(), points.get(0).getZ()}, {1, points.get(1).getY(), points.get(1).getZ()}, {1, points.get(2).getY(), points.get(2).getZ()}}).det();
            double d = -new Matrix3(new float[][]{{1, points.get(0).getY(), points.get(0).getZ()}, {1, points.get(1).getY(), points.get(1).getZ()}, {1, points.get(2).getY(), points.get(2).getZ()}}).det();
            return a * point.getX() + b * point.getY() + c * point.getZ() + d < 0;
        } else { */

        LinkedList<Vector3> centerPointsV = new LinkedList<>();
        for (Vector3 v:points
             ) {
            centerPointsV.add(new Vector3(point,v));
        }
        double cos = 0;
        Vector3 line = centerPointsV.getFirst();
        Vector3 line2;
        for(int i = 1;i<centerPointsV.size();i++){
            line2 = centerPointsV.get(i);
            cos += cos(line.dot(line2)/(line.length()*line2.length()));
        }

        if(cos - 2*PI < precision){
            return true;
        } else
            return false;
    }
}
