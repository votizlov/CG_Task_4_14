package models;

import com.sun.javafx.geom.Quat4f;
import main.math.*;
import main.third.IModel;
import main.third.PolyLine3D;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.cos;
import static java.lang.Math.toRadians;

public class Circle implements IModel {
    Vector3 center, rV;
    LinkedList<PolyLine3D> lines;

    public Vector3 getCenter() {
        return center;
    }

    public Vector3 getrV() {
        return rV;
    }

    public Circle(Vector3 center, Vector3 rV, Vector3 cV, int res) {
        this.center = center;
        this.rV = rV;
        lines = new LinkedList<>();
        LinkedList<Vector3> points = new LinkedList<>();
        Vector3 translateV = new Vector3(center, new Vector3(0, 0, 0));
        Vector3 backTranslateV = new Vector3(new Vector3(0, 0, 0), center);

        double dA = toRadians(360 / (double)res);
        Matrix4 turnM;

        Quat4f qR = QuatMath.createQuat(cV, dA);
        //points.add(center.add(center.add(rV)));
        for (int i = 0; i < res; i++) {
            turnM = Matrix4Factories.translation(backTranslateV)
                    .mul(Matrix4Factories.rorationAroundVector(cV, (float) cos(dA))
                            .mul(Matrix4Factories.translation(translateV)));
            //rV = QuatMath.transformVector(qR,rV);

            rV = turnM.mul(new Vector4(rV,1)).asVector3();
            //points.add(center.add(turnMatrix.mul(new Vector4(rV)).asVector3()));
            points.add(rV);
            //поворот на угол dA
        }

        lines.add(new PolyLine3D(points, true));
    }

    @Override
    public List<PolyLine3D> getLines() {
        return lines;
    }
}
