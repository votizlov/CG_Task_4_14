package main.third;

import java.awt.*;

public class Material {
    Color color;
    float reflectivity;//0<=reflectivity<=1

    public Color getColor() {
        return color;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public Material(Color color, float reflectivity) {
        this.color = color;
        this.reflectivity = reflectivity;
    }
}
