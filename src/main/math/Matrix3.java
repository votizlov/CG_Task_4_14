package main.math;

public class Matrix3 {
    private float[] matrix;

    /**
     * Создаёт экзмпляр матрицы на основе двумерного массива.
     * В рамках данной реализации нет необходимых проверок на
     * корректность входных аргументов.
     * @param m входная матрица.
     */
    public Matrix3(float[][] m) {
        matrix = new float[9];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                matrix[i * 3 + j] = m[i][j];
    }

    /**
     * Скрытый конструктор, который создаёт экземпляр матрицы на основе
     * одномерного массива без дополнительных проверок.
     * Предполагается, что массив будет иметь правильный размер.
     * @param arr исходный массив.
     */
    private Matrix3(float[] arr) {
        matrix = arr;
    }

    /**
     * Возвращает значение элемента матрицы по заданным индексам
     * @param row номер строки
     * @param col номер столбца
     * @return значение соответствующей ячейки
     */
    public float getAt(int row, int col) {
        return matrix[row * 3 + col];
    }

    /**
     * Устанавличвает значение элемента матрицы по заданным индексам
     * @param row номер строки
     * @param col номер столбца
     * @param value присваиваемое значение.
     */
    public void setAt(int row, int col, float value) {
        matrix[row * 3 + col] = value;
    }

    /**
     * Метод, создающий новую нулевую матрицу
     * @return соданная матрица, состоящая из нулей.
     */
    public static Matrix3 zero() {
        return new Matrix3(new float[9]);
    }

    /**
     * Метод, создающий матрицу, состоящую из единриц, расположенных на главной диагонали, и нулей
     * @return единичная матрица
     */
    public static Matrix3 one() {
        Matrix3 m = zero();
        for (int i = 0; i < 4; i++)
            m.setAt(i, i, 1);
        return m;
    }

    /**
     * Умножает матрицу на число
     * @param number число, на которое умножается матрица
     * @return новая матрица, являющаяся результатом умножения текущей матрицы на число.
     */
    public Matrix3 mul(float number) {
        float[] arr = new float[9];
        for (int i = 0; i < arr.length; i++)
            arr[i] = this.matrix[i] * number;
        return new Matrix3(arr);
    }

    public float det() {
        return matrix[0]*(matrix[4]*matrix[8]-matrix[5]*matrix[7])-matrix[1]*(matrix[3]*matrix[8]-matrix[5]*matrix[6])+matrix[2]*(matrix[3]*matrix[7]-matrix[4]*matrix[6]); //matrix[0]*(matrix[5]*())-matrix[1]*()+matrix[2]*()-matrix[3]*();
    }
}
