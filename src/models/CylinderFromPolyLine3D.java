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

        if (line.isClosed()) {
            /*
            rV = new Vector3(
                    line.getPoints().get(0),
                    Vector3.getMiddle(line.getPoints().get(line.getPoints().size()-1),line.getPoints().get(1)));
            rV = rV.mul((float) (r/rV.length()));
            */
            rV = new Vector3(line.getPoints().get(0),line.getPoints().get(1));
        } else {
            rV = Matrix4Factories.rotationXYZ(90, Matrix4Factories.Axis.Y)
                    .mul(Matrix4Factories.rotationXYZ(90, Matrix4Factories.Axis.X))
                    .mul(new Vector4(new Vector3(line.getPoints().get(0), line.getPoints().get(1))))
                    .asVector3();
        }

        lines = new LinkedList<>();
        Circle t = new Circle(line.getPoints().get(0), rV, line.getPoints().get(0), nPolygons);
        Circle t1;

        for (int i = 1; i < line.getPoints().size()-1; i++) {//за один шаг этого цикла вычисляется одна секция
            //вычисление изменения наклона rV измерением угла между двумя секциями
            rV = new Vector3(

                    line.getPoints().get(i),line.getPoints().get(i+1));
           /* cV = new Vector3(
                    line.getPoints().get(i-1),
                    line.getPoints().get(i)
            ).substract(rV);
            */
           cV = new Vector3(
                   line.getPoints().get(i),line.getPoints().get(i+1));
            t1 = new Circle(line.getPoints().get(i), rV, cV, nPolygons);
            lines.addAll(connectCircles(t, t1));
            t = t1;
        }
    }

    private LinkedList<PolyLine3D> connectCircles(Circle t, Circle t1) {
        LinkedList<PolyLine3D> line = new LinkedList<>();

        for (int i = 0; i < t.getLines().get(0).getPoints().size() - 1; i++) {
                line.add(new PolyLine3D(Arrays.asList(t.getLines().get(0).getPoints().get(i),
                        t1.getLines().get(0).getPoints().get(i),
                        t1.getLines().get(0).getPoints().get(i + 1),
                        t.getLines().get(0).getPoints().get(i + 1),
                        t.getLines().get(0).getPoints().get(i)), true));

        }

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
