package models;

import main.math.Vector3;
import main.third.IModel;
import main.third.PolyLine3D;

import java.util.List;

public class Anchor implements IModel {
    private Vector3 p;

    public Anchor(Vector3 p) {
        this.p = p;
    }

    @Override
    public List<PolyLine3D> getLines() {
        return null;
    }
}
