package models;

import main.math.Matrix4;
import main.math.Matrix4Factories;
import main.math.Vector3;
import main.math.Vector4;
import main.third.IModel;
import main.third.PolyLine3D;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.toRadians;

public class Tor implements IModel {
    private List<PolyLine3D> lines = new LinkedList<>();

    public Tor(Vector3 pos, double r, double width, int sectionN) {

        //Vector3 translateV = new Vector3(new Vector3(0,0,0),pos);
        //double dA = toRadians(360 / (double) sectionN);
        Vector3 rV = new Vector3((float) r,0,0);
        /*
        LinkedList<Vector3> points = new LinkedList<>();
        Matrix4 turnM;
        for (int i = 0; i < sectionN; i++) {
            turnM =Matrix4Factories.rorationAroundVector(new Vector3(0, 1, 0), (float) cos(dA)).mul( Matrix4Factories.translation(translateV));
            rV = turnM.mul(new Vector4(rV, 1)).asVector3();
            points.add(rV);
        }
        points.add(points.getFirst());
*/
        CylinderFromPolyLine3D cylinder = new CylinderFromPolyLine3D(new Circle(pos,rV,new Vector3(0,1,0),sectionN).lines.getFirst(),width,sectionN);

        lines.addAll(cylinder.getLines());
    }

    @Override
    public List<PolyLine3D> getLines() {
        return lines;
    }
}
