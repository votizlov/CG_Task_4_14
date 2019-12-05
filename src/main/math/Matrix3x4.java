package main.math;

import static main.math.Matrix4Factories.zero;

public class Matrix3x4 {
    private float[] matrix;

    public Matrix3x4(float[][] m){
        matrix = new float[12];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 4; j++)
                matrix[i * 4 + j] = m[i][j];
    }

    public static Matrix3x4 one(){
        return  new Matrix3x4(new float[]{0,0,4,0,0,1,0,0,0,1,0,0});
    }

    /**
     * Скрытый конструктор, который создаёт экземпляр матрицы на основе
     * одномерного массива без дополнительных проверок.
     * Предполагается, что массив будет иметь правильный размер.
     * @param arr исходный массив.
     */
    private Matrix3x4(float[] arr) {
        matrix = arr;
    }

    /**
     * Возвращает значение элемента матрицы по заданным индексам
     * @param row номер строки
     * @param col номер столбца
     * @return значение соответствующей ячейки
     */
    public float getAt(int row, int col) {
        return matrix[row * 4 + col];
    }

    /**
     * Устанавличвает значение элемента матрицы по заданным индексам
     * @param row номер строки
     * @param col номер столбца
     * @param value присваиваемое значение.
     */
    public void setAt(int row, int col, float value) {
        matrix[row * 4 + col] = value;
    }

    public Matrix4 mul(Matrix4 m) {
        Matrix4 r = zero();
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                for (int k = 0; k < 4; k++)
                    r.setAt(i, j, r.getAt(i, j) +
                            this.getAt(i, k) * m.getAt(k, j));
        return r;
    }

    public Vector4 mul(Vector4 v) {
        float[] arr = new float[4];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 4; j++)
                arr[i] += this.getAt(i, j) * v.at(j);
        return new Vector4(arr[0], arr[1], arr[2], arr[3]);
    }
}
