package models;

import com.sun.javafx.geom.Quat4f;
import main.math.*;
import main.third.IModel;
import main.third.PolyLine3D;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.toRadians;

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

        float dA = (float) toRadians(360/res);
        Matrix4 turnM;

        Quat4f qR = QuatMath.createQuat(cV,dA);
        points.add(center.add(center.add(rV)));
        for (int i = 0;i<res;i++) {
            turnM = Matrix4Factories.rorationAroundVector(cV, (float) cos(dA));
            //rV = QuatMath.transformVector(qR,rV);
            rV = turnM.mul(new Vector4(rV)).asVector3();
            //points.add(center.add(turnMatrix.mul(new Vector4(rV)).asVector3()));
            points.add(rV);
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
