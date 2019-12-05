/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.math;

import static java.lang.Math.PI;
import static java.lang.Math.tan;

/**
 * Класс, хранящий методы создания особых матриц.
 * @author Alexey
 */
public final class Matrix4Factories {
    /**
     * Продублированный метод создания нулевой матрицы
     * @return нулевая матрица
     */
    public static Matrix4 zero() {
        return Matrix4.zero();
    }
    
    /**
     * Продублированный метод создания единичной матрицы
     * @return единичная матрица
     */
    public static Matrix4 one() {
        return Matrix4.one();
    }
    
    /**
     * Создаёт матрицу поворота вокруг одной из осей,
     * заданной с помощью индекса на указанный угол в радианах.
     * Индексы осей: X=0, Y=1, Z=2.
     * В случае некорректного индекса, возваращается единичная матрица.
     * !!! надо кидать в этом случае exception !!!
     * @param alpha угол поворота
     * @param axisIndex индекс оси
     * @return Матрица поворота или единичная матрица.
     */
    public static Matrix4 rotationXYZ(double alpha, int axisIndex) {
        Matrix4 m = one();
        if (axisIndex < 0 || axisIndex > 2)
            return m;
        int a1 = (axisIndex + 1) % 3;
        int a2 = (axisIndex + 2) % 3;
        
        m.setAt(a1, a1, (float)Math.cos(alpha));
        m.setAt(a1, a2, (float)Math.sin(alpha));
        m.setAt(a2, a1, -(float)Math.sin(alpha));
        m.setAt(a2, a2, (float)Math.cos(alpha));
        
        return m;
    }
    
    public static enum Axis {X, Y, Z};
    
    /**
     * Создаёт матрицу поворота вокруго одной из осей на заданный угол в радианах
     * @param alpha угол в радианах
     * @param axis название оси, вокруг которой происходит вращение
     * @return матрица поворота
     */
    public static Matrix4 rotationXYZ(double alpha, Axis axis) {
        return rotationXYZ(alpha, axis == Axis.X ? 0 : axis == Axis.Y ? 1 : 2);
    }
    
    /**
     * Создаёт новую матрицу переноса по заданным параметрам
     * @param x X-составялющая смещения
     * @param y Y-составляющая смещения
     * @param z Z-составляющая смещения
     * @return матрица переноса
     */
    public static Matrix4 translation(float x, float y, float z) {
        Matrix4 m = one();
        m.setAt(0, 3, x);
        m.setAt(1, 3, y);
        m.setAt(2, 3, z);
        return m;
    }
    
    /**
     * Создаёт новую матрицу переноса на указанный вектор
     * @param v вектор, на который производится перенос
     * @return матрица переноса
     */
    public static Matrix4 translation(Vector3 v) {
        return translation(v.getX(), v.getY(), v.getZ());
    }
    
    /**
     * Создаёт матрицу масштабирования по заданным параметрам
     * @param factorX масштабирование вдоль оси X
     * @param factorY масштабирование вдоль оси Y
     * @param factorZ масштабирование вдоль оси Z
     * @return матрица масштабирования
     */
    public static Matrix4 scale(float factorX, float factorY, float factorZ) {
        Matrix4 m = one();
        m.setAt(0, 0, factorX);
        m.setAt(1, 1, factorY);
        m.setAt(2, 2, factorZ);
        return m;
    }
    
    /**
     * Создаёт матрицу масштабирования с одинаковыми коэффициентами по всем осям.
     * @param factor коэффициент масштабирования
     * @return матрица масштабирования
     */
    public static Matrix4 scale(float factor) {
        return scale(factor, factor, factor);
    }
    
    /**
     * Создаёт матрицу центральной проекции вдоль выбранной оси
     * @param point точка схода на этой оси
     * @param axisIndex номер оси (X=0, Y=1, Z=2)
     * @return Матрица проекции
     */
    public static Matrix4 centralProjection(float point, int axisIndex) {
        Matrix4 m = one();
        if (axisIndex < 0 || axisIndex > 2)
            return m;
        m.setAt(3, axisIndex, 1/point);
        return m;
    }
    
    /**
     * Создаёт матрицу центральной проекции вдоль выбранной оси
     * @param point точка схода на этой оси
     * @param axis название оси
     * @return Матрица проекции
     */
    public static Matrix4 centralProjection(float point, Axis axis) {
        return centralProjection(point, axis == Axis.X ? 0 : axis == Axis.Y ? 1 : 2);
    }

    public static Matrix4 cameraProjectionMatrix(double fov, double nearClipping, double farClipping, double aspect){
        /*float s = (float) (1/tan(fov/2*PI/180));
        return new Matrix4(new float[][]{{s,0,0,0},{0,s,0,0},{0,0, (float) (-farClipping/(farClipping-nearClipping)),0},{0,0, 0,(float) (-farClipping*nearClipping/(farClipping-nearClipping))}});*/
        double top =nearClipping*tan(PI/180*fov/2);
        double bottom = -top;
        double right = top*aspect;
        double left = -right;
        return new Matrix4(new float[][]{{(float) (2*nearClipping/(right-left)),0, (float) ((right+left)/(right-left)),0},
                {0, (float) (2*nearClipping/(top-bottom)), (float) ((top+bottom)/(top-bottom)),0},
                {0,0, (float) (-(farClipping+nearClipping)/(farClipping-nearClipping)), (float) (-2*farClipping*nearClipping/(farClipping-nearClipping))},
                {0,0, -1,0}});
    }

    public static Matrix4 cameraViewMatrix(Vector3 eye, Vector3 center, Vector3 top){
        Matrix4 m = Matrix4.zero();
        Vector3 z = eye.extract(center);
        z.normalize();
        Vector3 y = top;
        Vector3 x = y.cross(z);
        y = z.cross(x);
        x.normalize();
        y.normalize();
        m.setAt(0,0, x.getX());
        m.setAt(1,0, x.getY());
        m.setAt(2,0, x.getZ());
        m.setAt(3,0, (float) -x.dot( eye ));
        m.setAt(0,1, y.getX());
        m.setAt(1,1, y.getY());
        m.setAt(2,1, y.getZ());
        m.setAt(3,1, (float) -y.dot( eye ));
        m.setAt(0,2, z.getX());
        m.setAt(1,2, z.getY());
        m.setAt(2,2, z.getZ());
        m.setAt(3,2, (float) -z.dot( eye ));
        //m.setAt(0,3, 0);
        //[1][3] = 0;
        //Matrix[2][3] = 0;
        m.setAt(3,3, 1.0f);

        return m;//new Matrix4(new float[][]{{x.getX(),y.getX(),z.getX(), (float) -x.dot(eye)},{x.getY(),y.getY(),z.getY(), (float) -y.dot(eye)},{x.getZ(),y.getZ(), z.getZ(), (float) -z.dot(eye)},{0,0, 0,1}});
    }
}
