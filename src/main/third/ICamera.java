/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.third;

import main.math.Matrix4;
import main.math.Vector3;

/**
 * Описывает основную функциональность камеры - превращение координат 
 * из мировой системы координат в систему координат камеры.
 * @author Alexey
 */
public interface ICamera {
    /**
     * Преобразует точку из мировой системы координат в систему координат камеры
     * @param v преобразуемая точка
     * @return новая точка
     */
    Vector3 w2s(Vector3 v);
    void modifyRotate(Matrix4 dp);
    void modifyTranslate(Matrix4 dp);
    void modifyProjection(Matrix4 dp);
    void modifyScale(Matrix4 dp);

    void modifyCameraPos(Vector3 vector3);

    Vector3 getCameraDir();

    float getFov();

    float getAspect();
}
