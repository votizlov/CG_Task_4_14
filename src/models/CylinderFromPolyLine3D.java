package models;

import main.math.Vector3;
import main.third.IModel;
import main.third.Material;
import main.third.PolyLine3D;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CylinderFromPolyLine3D implements IModel {
    private LinkedList<PolyLine3D> lines;

    public CylinderFromPolyLine3D(PolyLine3D line,double r,int nPolygons) {
        LinkedList<Vector3> polygonPoints;

        Vector3 rV = new Vector3();
        lines = new LinkedList<>();
        Circle t = new Circle(line.getPoints().get(0),rV,nPolygons);
        Circle t1;

        for (int i = 1;i<line.getPoints().size();i++){//за один шаг этого цикла вычисляется одна секция
            //вычисление изменения наклона rV измерением угла между двумя секциями
            t1 = new Circle(line.getPoints().get(i),rV,nPolygons);
            lines.addAll(connectCircles(t,t1));
            t = t1;
        }
    }

    private LinkedList<PolyLine3D> connectCircles(Circle t, Circle t1) {
        LinkedList<PolyLine3D> line = new LinkedList<>();

        return line;
    }

    @Override
    public List<PolyLine3D> getLines() {
        return lines;
    }
}
