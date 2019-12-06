package models;

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

    public CylinderFromPolyLine3D(PolyLine3D line,double r,int nPolygons) {
        LinkedList<Vector3> polygonPoints;

        Vector3 rV = Matrix4Factories.rotationXYZ(90, Matrix4Factories.Axis.Y)
                .mul(Matrix4Factories.rotationXYZ(90, Matrix4Factories.Axis.X))
                .mul(new Vector4(new Vector3(line.getPoints().get(0),line.getPoints().get(1))))
                .asVector3();
        double dA;
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

        for (int i = 0;i<t.getLines().get(0).getPoints().size()-1;i++) {
            for (int j = 0;j<t1.getLines().get(0).getPoints().size()-1;j++) {
                line.add(new PolyLine3D(Arrays.asList(t.getLines().get(0).getPoints().get(i),
                        t1.getLines().get(0).getPoints().get(j),
                        t1.getLines().get(0).getPoints().get(j+1),
                        t.getLines().get(0).getPoints().get(i+1),
                        t.getLines().get(0).getPoints().get(i)),true));
            }
        }

        return line;
    }

    @Override
    public List<PolyLine3D> getLines() {
        return lines;
    }
}
