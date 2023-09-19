/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

/**
 * @author Morgan
 * program that when given a matrix will return if it is in reduced row echelon form
 * row echelon form or neither
 */
// reading from file imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RREF {

    static int rows = 5;
    static int columns = 6;
    static int[][] matrix = new int[rows][columns];
    static String filePath = "test.txt";

    public static void writeMatrix(String dataLines, int row) {
        // i is the index of the string        
        int matrixIndex = 0;
        for (int i = 0; i < dataLines.length(); i++) {
            if (dataLines.charAt(i) != ' ') {
                char data = dataLines.charAt(i);
                matrix[row][matrixIndex] = data - 48;
                matrixIndex++;
            }
        }
    }

    public static void readMatrix() {
        try {
            File myFile = new File(filePath);
            Scanner myReader = new Scanner(myFile);
            int currRow = 0;
            while (myReader.hasNextLine()) {
                // now parse each line
                String data = myReader.nextLine();
                RREF.writeMatrix(data, currRow);
                currRow++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occured while reading file and file path");
        }
    }

    public static void printMatrix() {
        System.out.println("Matrix at file path: " + filePath);
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                System.out.print(matrix[row][column]);
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    // check if any row is without non zero values
    public static boolean bottomZeros() {
        boolean bottomZerosRow[] = new boolean[rows];
        // find all non zero rows
        for (int row = 0; row < rows; row++) {
            bottomZerosRow[row] = true;
            for (int column = 0; column < columns; column++) {
                if (matrix[row][column] != 0) {
                    bottomZerosRow[row] = false;
                }
            }
        }
        // find the first all zero
        for (int row = 0; row < rows; row++) {
            if (bottomZerosRow[row] == true) {
                // loop through the rest of the rows if there is one that is not 
                // non zero after this first all zero row return zero
                for (; row < rows; row++) {
                    if (bottomZerosRow[row] == false) {
                        //System.out.println("The zero's are not the bottom");
                        return false;
                    }
                }
            }
        }
        //System.out.println("The zero's are at the bottom");
        return true;
    }

    public static boolean leadingOnesColumnClear(int row) {
        for (int index = 0; index < columns; index++) {
            if (matrix[row][index] == 1) {
                break;
            } else if (matrix[row][index] != 0) {
                return false;
            }
        }
        return true;
    }

    // check if the first non zero values in a row is a one
    public static boolean leadingOnes() {
        for (int row = 0; row < rows; row++) {
            if (RREF.leadingOnesColumnClear(row) == false) {
                //System.out.println("The leading ones are not the first non zero value in the row");
                return false;
            }
        }
        //System.out.println("The leading ones are the first non zero value in the row");
        return true;
    }
    public static int decendingLeadingOnesColumn(int row){
        for (int column = 0; column < columns; column++){
            if (matrix[row][column] == 1){
                return column;
            }
        }
        return -1;
    }
    // check if the leading One's are in a nature decending to the right
    public static boolean decendingLeadingOnes() {
        int[] leadingOnesColumn = new int[rows];
        for (int row = 0; row < rows; row++){
            leadingOnesColumn[row] = RREF.decendingLeadingOnesColumn(row);
        }
        for (int row = 1; row < rows; row++){
            if (leadingOnesColumn[row] == -1){
                row++;
                if (row == rows){
                    row = rows - 1;
                }
            }
            if (leadingOnesColumn[row] > leadingOnesColumn[row - 1] ){
                return true;
            }
        }
        return false;
    }

    public static boolean checkColumnLeadingOnes(int leadingOneRow, int leadingOneColumn) {
        for (int column = 0; column < columns; column++) {
            if (column == leadingOneColumn) {
                column++;
            }
            if (matrix[leadingOneRow][column] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean leadingOnesColumn(int row) {
        int index = 0;
        for (; index < columns; index++) {
            if (matrix[row][index] == 1) {
                break;
            }
        }
        return RREF.checkColumnLeadingOnes(row, index);
    }

    //check if the column with a leading zero is all non zero's
    public static boolean LeadingOneClearColumn() {
        for (int row = 0; row < rows; row++) {
            return leadingOnesColumn(row);
        }
        return false;
    }

    //returns false on not RREF return value
    public static boolean checkColumn(int leadingOneColumn, int leadingOneRow) {
        for (int column = 0; column < columns; columns++) {
            if (matrix[leadingOneRow][column] != 0 && column != leadingOneColumn) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        RREF.readMatrix();
        RREF.printMatrix();
        if (RREF.bottomZeros() == true
                && RREF.leadingOnes() == true
                && RREF.decendingLeadingOnes() == true) {
            if (RREF.LeadingOneClearColumn() == true) {
                System.out.println("The Matrix is in RREF");
            } else {
                System.out.println("The Matrix is in REF");
            }
        } else {
            System.out.println("The Matrix is in neither REF or RREF");
        }
    }
}
