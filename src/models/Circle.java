package models;

import main.math.Vector3;
import main.math.Vector4;
import main.third.IModel;
import main.third.PolyLine3D;
import java.util.LinkedList;
import java.util.List;

public class Circle implements IModel {
    Vector3 center,rV;
    LinkedList<PolyLine3D> lines;
    public Vector3 getCenter() {
        return center;
    }

    public Vector3 getrV() {
        return rV;
    }

    public Circle(Vector3 center, Vector3 rV,int res) {
        this.center = center;
        this.rV = rV;
        lines = new LinkedList<>();
        LinkedList<Vector3> points = new LinkedList<>();

        double dA = 360/res;
        for (int i = 0;i<res;i++) {
            points.add(center.add(rV));
            rV = new Vector4(rV).asVector3();//поворот на угол dA
        }
        points.add(points.getFirst());

        lines.add(new PolyLine3D(points,true));
    }

    @Override
    public List<PolyLine3D> getLines() {
        return lines;
    }
}
