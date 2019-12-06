/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.third;

import main.math.Matrix4;
import main.math.Vector3;
import main.math.Vector4;


public class Camera implements ICamera {
    private Matrix4 translate, rotate, scale, projection;

    /**
     * Создаёт простую камеру
     */
    public Camera() {
        translate = Matrix4.one();
        rotate = Matrix4.one();
        scale = Matrix4.one();
        projection = Matrix4.one();
    }

    /**
     * Метод, преобразуюший точку из меировой системы координат в систему координат камеры.
     * Сначала к вектору применяется масштаб(S), далее поворот(R), потом перенос(T) и в конце - проекция(P).
     * r = P * T * R * S * v
     *
     * @param v вектор, который надо перевести
     * @return новый вектор
     */
    @Override
    public Vector3 w2s(Vector3 v) {
        return projection.mul(
                translate.mul(
                        rotate.mul(
                                scale.mul(
                                        new Vector4(v, 1)
                                )
                        )
                )
        ).asVector3();
    }

    public void modifyProjection(Matrix4 dp) {
        this.projection = dp.mul(this.projection);
    }

    public Matrix4 getProjection() {
        return projection;
    }

    public void setProjection(Matrix4 projection) {
        this.projection = projection;
    }

    public void modifyRotate(Matrix4 dp) {
        this.rotate = dp.mul(this.rotate);
    }

    public Matrix4 getRotate() {
        return rotate;
    }

    public void setRotate(Matrix4 rotate) {
        this.rotate = rotate;
    }

    public void modifyScale(Matrix4 dp) {
        this.scale = dp.mul(this.scale);
    }

    @Override
    public void modifyCameraPos(Vector3 vector3) {

    }

    @Override
    public Vector3 getCameraDir() {
        return new Vector3(translate.getAt(3,0),translate.getAt(3,1),translate.getAt(3,2));
    }

    @Override
    public float getFov() {
        return 60;
    }

    @Override
    public float getAspect() {
        return 0;
    }

    public Matrix4 getScale() {
        return scale;
    }

    public void setScale(Matrix4 scale) {
        this.scale = scale;
    }

    public void modifyTranslate(Matrix4 dp) {
        this.translate = dp.mul(this.translate);
    }

    public Matrix4 getTranslate() {
        return translate;
    }

    public void setTranslate(Matrix4 translate) {
        this.translate = translate;
    }

    public Vector3 getCameraPos(){
        return new Vector3(translate.getAt(3,0),translate.getAt(3,1),translate.getAt(3,2));
    }
}
