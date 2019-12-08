package models;

import main.math.Matrix4;
import main.math.Matrix4Factories;
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

    public Circle(Vector3 center, Vector3 rV, Vector3 cV,int res) {
        this.center = center;
        this.rV = rV;
        lines = new LinkedList<>();
        LinkedList<Vector3> points = new LinkedList<>();

        float dA = 360/res;
        float currentA = dA;
        points.add(center.add(center.add(rV)));
        Matrix4 turnMatrix;
        for (int i = 1;i<res;i++) {
            currentA+=dA;
            turnMatrix = Matrix4Factories.rorationAroundVector(cV,dA);
            points.add(center.add(turnMatrix.mul(new Vector4(rV)).asVector3()));
            //поворот на угол dA
        }
        points.add(points.getFirst());

        lines.add(new PolyLine3D(points,true));
    }

    @Override
    public List<PolyLine3D> getLines() {
        return lines;
    }
}
