/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.math;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


public class Vector3 {
    private float[] values; /*Значения хранятся в виде массива из трёх элементов*/
    
    /**
     * Создаёт экземпляр вектора на основе значений трёх составляющих
     * @param x первая составляющая, описывающая X-координату
     * @param y вторая составляющая, описывающая Y-координату
     * @param z третья составляющая, описывающая Z-координату
     */
    public Vector3(float x, float y, float z) {
        values = new float[]{x, y, z};
    }

    public Vector3(Vector3 point, Vector3 point2) {

    }

    /**
     * X-составляющая вектора
     * @return X-составляющая вектора
     */
    public float getX() {
        return values[0];
    }

    /**
     * Y-составляющая вектора
     * @return Y-составляющая вектора
     */
    public float getY() {
        return values[1];
    }

    /**
     * Z-составляющая вектора
     * @return Z-составляющая вектора
     */
    public float getZ() {
        return values[2];
    }
    
    /**
     * Метод, возвращающий составляющую вектора по порядковому номеру
     * @param idx порядковый номер
     * @return значение составляющей вектора
     */
    public float at(int idx) {
        return values[idx];
    }
    
    private static final float EPSILON = 1e-10f;
    /**
     * Метод, возвращающий длину вектора
     * @return длина вектора
     */
    public float length() {
        float lenSqr = values[0] * values[0] + values[1] * values[1] + values[2] * values[2];
        if (lenSqr < EPSILON)
            return 0;
        return (float) sqrt(lenSqr);
    }
    /**
     * Метод, возвращающий расстояние между точками
     * @param point2 вторая точка
     * @return расстояние
     */
    public double getDistance(Vector3 point2){
        return sqrt(pow(point2.getX()-getX(),2)+pow(point2.getY()-getY(),2)+pow(point2.getZ()-getZ(),2));
    }

    //The dot product is a float value equal to the magnitudes of the two vectors multiplied together and then multiplied by the cosine of the angle between them.
    //Скалярное произведение
    public double dot(Vector3 eye) {
        double r = 0;
        for (float i:values
             ) {
            for (float j:eye.values
                 ) {
                r+=i*j;
            }
        }
        return r;
    }

    //верторное произведение
    public Vector3 cross(Vector3 b) {
        return new Vector3(getY()*b.getZ()-getZ()*b.getY(),getZ()*b.getX()-getX()*b.getZ(),getX()*b.getY()-getY()*b.getX());
    }
    //нормализация
    public void normalize() {
        float l = 1/length();
        values[0]*=l;
        values[1]*=l;
        values[2]*=l;
    }
    //разность
    public Vector3 extract(Vector3 center) {
        return new Vector3(getX()-center.getX(),getY()-center.getY(),getZ()-center.getZ());
    }

    public Vector3 add(Vector3 v){
        return new Vector3(getX()+v.getX(),getY()+v.getY(),getZ()+v.getZ());
    }

    public Vector3 mul(float l) {
        return new Vector3(getX()*l,getY()*l,getZ()*l);
    }
}
