package models;

import main.math.Matrix4;
import main.math.Matrix4Factories;
import main.math.Vector3;
import main.math.Vector4;
import main.third.IModel;
import main.third.PolyLine3D;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CylinderFromPolyLine3D implements IModel {
    private LinkedList<PolyLine3D> lines;

    public CylinderFromPolyLine3D(PolyLine3D line, double r, int nPolygons) {
        Vector3 rV;
        Vector3 cV;
        Matrix4 turnMatrix = Matrix4Factories.rotationXYZ(90, Matrix4Factories.Axis.Y);

        if (line.isClosed()) {
            rV = turnMatrix.mul(new Vector4(new Vector3(
                    line.getPoints().get(line.getPoints().size() - 1), line.getPoints().get(0)).mul((float) r), 1)).asVector3();
            cV = new Vector3(
                    line.getPoints().get(line.getPoints().size() - 1), line.getPoints().get(0));
        } else {/*
            rV = Matrix4Factories.rotationXYZ(90, Matrix4Factories.Axis.Y)
                    .mul(Matrix4Factories.rotationXYZ(90, Matrix4Factories.Axis.X))
                    .mul(new Vector4(new Vector3(line.getPoints().get(0), line.getPoints().get(1))))
                    .asVector3();*/
            rV = turnMatrix.mul(new Vector4(new Vector3(
                    line.getPoints().get(0), line.getPoints().get(1)).mul((float) r), 1)).asVector3();
            cV = new Vector3(
                    line.getPoints().get(0), line.getPoints().get(1));
        }

        lines = new LinkedList<>();
        Circle t = new Circle(line.getPoints().get(line.getPoints().size()-1), rV, cV, nPolygons);
        Circle t1;

        for (int i = 0; i < line.getPoints().size() - 1; i++) {//за один шаг этого цикла вычисляется одна секция
            //вычисление изменения наклона rV измерением угла между двумя секциями
            rV = turnMatrix.mul(new Vector4(new Vector3(
                    line.getPoints().get(i), line.getPoints().get(i + 1)).mul((float) r), 1)).asVector3();
            cV = new Vector3(
                    line.getPoints().get(i), line.getPoints().get(i + 1));
            t1 = new Circle(line.getPoints().get(i), rV, cV, nPolygons);
            lines.addAll(connectCircles(t, t1));
            t = t1;
        }
        if (line.isClosed()) {
            rV = turnMatrix.mul(new Vector4(new Vector3(
                    line.getPoints().get(line.getPoints().size() - 1), line.getPoints().get(0)).mul((float) r), 1)).asVector3();
            cV = new Vector3(
                    line.getPoints().get(line.getPoints().size() - 1), line.getPoints().get(0));
            t1 = new Circle(line.getPoints().get(line.getPoints().size() - 1), rV, cV, nPolygons);
            lines.addAll(connectCircles(t, t1));
        }
    }

    private LinkedList<PolyLine3D> connectCircles(Circle t, Circle t1) {
        LinkedList<PolyLine3D> line = new LinkedList<>();

        for (int i = 0; i < t.getLines().get(0).getPoints().size() - 1; i++) {
            line.add(new PolyLine3D(Arrays.asList(
                    t.getLines().get(0).getPoints().get(i),
                    t1.getLines().get(0).getPoints().get(i),
                    t1.getLines().get(0).getPoints().get(i + 1),
                    t.getLines().get(0).getPoints().get(i + 1),
                    t.getLines().get(0).getPoints().get(i)), true));

        }

        line.add(new PolyLine3D(Arrays.asList(
                t.getLines().get(0).getPoints().get(t.getLines().get(0).getPoints().size()-1),
                t1.getLines().get(0).getPoints().get(t1.getLines().get(0).getPoints().size()-1),
                t1.getLines().get(0).getPoints().get(0),
                t.getLines().get(0).getPoints().get(0),
                t.getLines().get(0).getPoints().get(t.getLines().get(0).getPoints().size()-1)), true));

        return line;
    }

    private float getCosAngle(Vector3 p1, Vector3 p2, Vector3 p3, Vector3 p4) {//returns cos
        Vector3 v1 = new Vector3(p1, p2);
        Vector3 v2 = new Vector3(p3, p4);
        return (float) (v1.dot(v2) / (v1.length() * v2.length()));
    }

    @Override
    public List<PolyLine3D> getLines() {
        return lines;
    }
}
