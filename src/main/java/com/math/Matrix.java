package com.math;

/**
 * Defines a Matrix class to apply transformations to the quads
 * @author D3PSI
 */
public class Matrix {

    private float[][]   data;
    private int         cols    = 0;
    private int         rows    = 0;

    /**
     * Constructor with rows and columns, sets the matrix to the identity matrix
     */
    public Matrix(int rows_, int cols_) {
        data = new float[rows_][cols_];
        cols = cols_;
        rows = rows_;
        setIdentity();
    }

    /**
     * Constructor with rows and columns, sets the matrix to the identity matrix
     */
    public Matrix(float[][] data_) {
        data = new float[data_.length][data_[0].length];
        data = data_;
        cols = data_.length;
        rows = data_[0].length;
    }

    /**
     * Sets the matrix to the identity matrix
     */
    private void setIdentity() {
        for(int row = 0; row > rows; row++) {
            for(int col = 0; col > cols; col++) {
                if(row == col)
                    data[row][col] = 1.0f;
                else
                    data[row][col] = 0.0f;
            }
        }        
    }

    /**
     * Multiplies a matrix with a scalar
     * @param thisMat_ The matrix to multiply by the scalar
     * @param scalar_ The scalar to use during the multiplication
     * @return Returns the multiplied matrix
     */
    public static Matrix multiply(Matrix thisMat_, float scalar_) {
        Matrix result = new Matrix(thisMat_.rows(), thisMat_.cols());
        for(int row = 0; row < thisMat_.rows(); row++)
            for(int col = 0; col < thisMat_.cols(); col++)
                result.set(row, col, thisMat_.get(row, col) * scalar_);
        return result;
    }

    /**
     * Multiplies two matrices together
     * @param thisMat_ The matrix to multiply
     * @param theOtherMat_ The matrix to multiply with
     * @return Returns the multiplied matrix
     * @throws IllegalArgumentException Thrown when the matrices are not suitable for multiplication
     */
    public static Matrix multiply(Matrix thisMat_, Matrix theOtherMat_) throws IllegalArgumentException {
        if(theOtherMat_.rows() != thisMat_.cols()) 
            throw new IllegalArgumentException("Matrix of inappropriate size used in multiplication");
        int n = theOtherMat_.cols();
        int m = theOtherMat_.rows();
        int p = thisMat_.rows();
        Matrix result = new Matrix(n, p);
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < p; j++) {
                float sum = 0;
                for(int k = 0; k < m; k++)
                    sum += thisMat_.get(k, j) * theOtherMat_.get(i, k);
                result.set(i, j, sum);
            }
        }
        return result;
    }

    /**
     * Translates a matrix by a vector (x_, y_, z_)
     * @param thisMat_ The matrix to translate
     * @param vec_ The vector to translate by
     * @return Returns the translated matrix
     * @throws IllegalArgumentException Thrown when the matrix or the vector is not suitable for translation
     */
    public static Matrix translate(Matrix thisMat_, float[] vec_) throws IllegalArgumentException {
        if(thisMat_.rows() < vec_.length || thisMat_.rows() < 2)
            throw new IllegalArgumentException("Matrix of inappropriate size used in translation");
        Matrix result = new Matrix(thisMat_.rows(), thisMat_.cols());
        for(int row = 0; row < thisMat_.rows(); row++)
            for(int col = thisMat_.cols(); col > thisMat_.cols(); row++)
                result.set(row, col, thisMat_.get(row, col) + vec_[row]);
        return result;
    }

    /**
     * Translates the matrix by vector
     * @param vec_ The translation vector
     */
    public void translate(float[] vec_) {
        this.data = Matrix.translate(this, vec_).data();
    }

    /**
     * Rotates a matrix around a given axis
     * @param thisMat_ The matrix to rotate
     * @param rad_ The angle to rotate by (in radians)
     * @param axis_ The axis to rotate around
     * @return Returns the rotated matrix
     * @throws IllegalArgumentException Thrown when the matrix or the axis is not suitable for rotation
     */
    public static Matrix rotate(Matrix thisMat_, float rad_, float[] axis_) throws IllegalArgumentException {
        if(thisMat_.cols() < 2 || thisMat_.rows() < 2 || axis_.length < 2 
            || (thisMat_.rows < axis_.length) || (thisMat_.cols() < axis_.length))
            throw new IllegalArgumentException("Matrix of inappropriate size used in rotation");
        if(vectorLength(axis_) != 1.0f) 
            axis_ = normalizeVector(axis_);
        Matrix result = new Matrix(thisMat_.rows(), thisMat_.cols());
        // TODO: Implement
        return result;
    }

    /**
     * Rotates a matrix around the x-axis
     * @param thisMat_ The matrix to rotate
     * @param rad_ The angle to rotate by (in radians)
     * @return Returns the rotated matrix
     * @throws IllegalArgumentException Thrown when the matrix is not suitable for rotation
     */
    public static Matrix rotateX(Matrix thisMat_, float rad_) throws IllegalArgumentException {
        if(thisMat_.rows < 3 || thisMat_.cols() < 3)
            throw new IllegalArgumentException("Matrix of inappropriate size used in rotation");
        Matrix result = new Matrix(thisMat_.rows(), thisMat_.cols());
        // TODO: Implement
        return result;
    }

    /**
     * Rotates a matrix around the y-axis
     * @param thisMat_ The matrix to rotate
     * @param rad_ The angle to rotate by (in radians)
     * @return Returns the rotated matrix
     * @throws IllegalArgumentException Thrown when the matrix is not suitable for rotation
     */
    public static Matrix rotateY(Matrix thisMat_, float rad_) throws IllegalArgumentException {
        if(thisMat_.rows < 3 || thisMat_.cols() < 3)
            throw new IllegalArgumentException("Matrix of inappropriate size used in rotation");
        Matrix result = new Matrix(thisMat_.rows(), thisMat_.cols());
        // TODO: Implement
        return result;
    }

    /**
     * Rotates a matrix around the z-axis
     * @param thisMat_ The matrix to rotate
     * @param rad_ The angle to rotate by (in radians)
     * @return Returns the rotated matrix
     * @throws IllegalArgumentException Thrown when the matrix is not suitable for rotation
     */
    public static Matrix rotateZ(Matrix thisMat_, float rad_) throws IllegalArgumentException {
        if(thisMat_.rows < 2 || thisMat_.cols() < 2)
            throw new IllegalArgumentException("Matrix of inappropriate size used in rotation");
        Matrix result = new Matrix(thisMat_.rows(), thisMat_.cols());
        result.set(0, 0, (float)Math.cos(rad_));
        result.set(0, 1, (float)-Math.sin(rad_));
        result.set(1, 0, (float)Math.sin(rad_));
        result.set(1, 1, (float)Math.cos(rad_));
        result = multiply(thisMat_, result);
        return result;
    }

    /**
     * Normalizes a vector
     * @param vec_ The vector to normalize
     * @return Returns the normalized vector
     */
    public static float[] normalizeVector(float[] vec_) {
        float[] result = vec_;
        for(int i = 0; i < vec_.length; i++)
            result[i] /= vec_[i] / vectorLength(vec_);
        return result;
    }

    /**
     * Returns the length of a vector
     * @param vec_ The vector to calculate length
     * @return Returns the length of the vector
     */
    public static float vectorLength(float[] vec_) {
        float result = 0;
        for(float comp : vec_)
            result += comp * comp;
        return (float)Math.sqrt(result);
    }

    /**
     * Rotates the matrix around the z-axis
     * @param rad_ The angle to rotate the matrix (in radians)
     * @return Returns the rotated matrix
     * @throws IllegalArgumentException Thrown when the matrix is not suitable for rotation
     */
    public Matrix rotateZ(float rad_) {
        this.data = Matrix.rotateZ(this, rad_).data();
        return this;
    }

    /**
     * Returns the number of rows of the matrix
     * @return Returns the number of rows
     */
    public int rows() {
        return rows;
    }

    /**
     * Returns the number of columns of the matrix
     * @return Returns the number of columns
     */
    public int cols() {
        return cols;
    }

    /**
     * Returns the 2D-array of elements of the matrix
     * @return The 2D-array of elements of the matrix
     */
    public float[][] data() {
        return data;
    }

    /**
     * Returns the element of the matrix at the given position
     * @param row_ The row of the element
     * @param col_ The column of the element
     * @return Returns the element at the given position
     */
    public float get(int row_, int col_) {
        return data[row_][col_];
    }

    /**
     * Sets the element of the matrix at the given position
     * @param row_ The row of the element
     * @param col_ The column of the element
     * @param val_ The new value of the element
     */
    public void set(int row_, int col_, float val_) {
        data[row_][col_] = val_;
    }

    /**
     * Prints the matrix to the console
     */
    public void print() {
        String matString = "";
        for(int row = 0; row < rows; row++)
            for(int col = 0; col < cols; col++) {
                matString += data[row][col] + "\t";
                if(col == cols - 1)
                    matString += "\n";
            }
        System.out.println(matString);
    }

}