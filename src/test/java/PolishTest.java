
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/**
 * Created by Никита on 21.08.2016.
 */
public class PolishTest {


    @Test
    public void TestDevideByZero(){
        System.out.println("Test Programm: test 1 - Devide by zero");
        Polish pl = new Polish("1/0");
        Double d = Double.valueOf(1.0/0);
        String ideal = String.valueOf(d);
        System.out.println("1/0 = " + ideal);
        assertEquals(ideal, pl.getAnswer());
    }

    @Test
    public void TestReadedFromExcel() throws IOException {
        System.out.println("Test Programm: test 2 - expressions from Excel");
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream("src\\main\\resources\\DataExel.xls"));
        HSSFSheet sheet = wb.getSheet("Polish");

        double inNumber = 0;
        String inString = null;

        Iterator<Row> it = sheet.iterator();
        while (it.hasNext()) {
            Row row = it.next();
            Iterator<Cell> cells = row.iterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                int cellType = cell.getCellType();

                switch (cellType) {
                    case Cell.CELL_TYPE_STRING:
                        System.out.print((inString=cell.getStringCellValue()) + " = ");
                        break;

                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print((inNumber=cell.getNumericCellValue()));
                        break;

                    default:
                        break;
                }
            }
            System.out.println();
            Polish pl = new Polish(String.valueOf(inString));
            if ((inNumber - Double.parseDouble(pl.answer)) != 0) {
                assertEquals("Ошибка в числе: " + inString, inNumber, pl.answer);
            }
        }

    }
}
