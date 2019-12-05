package main.third;


import main.math.Vector3;

import java.awt.*;

public class PointLight {
    private Vector3 pos;
    private double intensity;//how color changes over 1 meter
    private Color color;

    public double getdIntensity() {
        return dIntensity;
    }

    private double dIntensity;//light intensity at pos

    public PointLight(Vector3 pos, double intensity,double dIntensity, Color color) {
        this.pos = pos;
        this.intensity = intensity;
        this.color = color;
        this.dIntensity = dIntensity;
    }

    public Vector3 getPos() {
        return pos;
    }

    public double getIntensity() {
        return intensity;
    }

    public Color getColor() {
        return color;
    }
}
