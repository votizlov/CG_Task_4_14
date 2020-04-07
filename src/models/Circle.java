package models;

import com.sun.javafx.geom.Quat4f;
import main.math.*;
import main.third.IModel;
import main.third.PolyLine3D;

import java.util.Iterator;
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
        cV.normalize();
        this.center = center;
        this.rV = rV;
        lines = new LinkedList<>();
        LinkedList<Vector3> points = new LinkedList<>();
        Vector3 translateV = new Vector3(center, new Vector3(0, 0, 0));



        double dA = toRadians(360 / (double) res);
        Matrix4 turnM;
        Matrix4 transM = Matrix4Factories.translation(translateV);

        Quat4f qR = QuatMath.createQuat(cV, dA);
        //points.add(center.add(center.add(rV)));

        //поворачиваем на 45
        turnM = Matrix4Factories.rorationAroundVector(cV, (float) cos(toRadians(45)));

        rV = turnM.mul(new Vector4(rV, 1)).asVector3();

        for (int i = 0; i < res; i++) {
            turnM = Matrix4Factories.rorationAroundVector(cV, (float) cos(dA));

            rV = turnM.mul(new Vector4(rV, 1)).asVector3();
            points.add(rV);
            //поворот на угол dA
        }
        LinkedList<Vector3> t = new LinkedList<>();
        for (int i = 0; i < points.size(); i++) {
            t.add(transM.mul(new Vector4(points.get(i), 1)).asVector3());
        }

        lines.add(new PolyLine3D(t, true));
    }

    @Override
    public List<PolyLine3D> getLines() {
        return lines;
    }
}
