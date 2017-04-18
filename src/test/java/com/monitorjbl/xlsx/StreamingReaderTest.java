package com.monitorjbl.xlsx;

import com.monitorjbl.xlsx.exceptions.MissingSheetException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class StreamingReaderTest {
  @BeforeClass
  public static void init() {
    Locale.setDefault(Locale.ENGLISH);
  }

  @Test
  public void testTypes() throws Exception {
    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    try(
        InputStream is = new FileInputStream(new File("src/test/resources/data_types.xlsx"));
        StreamingReader reader = StreamingReader.builder().read(is);
    ) {

      List<List<Cell>> obj = new ArrayList<>();

      for(Row r : reader) {
        List<Cell> o = new ArrayList<>();
        for(Cell c : r) {
          o.add(c);
        }
        obj.add(o);
      }

      assertEquals(7, obj.size());
      List<Cell> row;

      row = obj.get(0);
      assertEquals(2, row.size());
      assertEquals(Cell.CELL_TYPE_STRING, row.get(0).getCellType());
      assertEquals(Cell.CELL_TYPE_STRING, row.get(1).getCellType());
      assertEquals("Type", row.get(0).getStringCellValue());
      assertEquals("Type", row.get(0).getRichStringCellValue().getString());
      assertEquals("Value", row.get(1).getStringCellValue());
      assertEquals("Value", row.get(1).getRichStringCellValue().getString());

      row = obj.get(1);
      assertEquals(2, row.size());
      assertEquals(Cell.CELL_TYPE_STRING, row.get(0).getCellType());
      assertEquals(Cell.CELL_TYPE_STRING, row.get(1).getCellType());
      assertEquals("string", row.get(0).getStringCellValue());
      assertEquals("string", row.get(0).getRichStringCellValue().getString());
      assertEquals("jib-jab", row.get(1).getStringCellValue());
      assertEquals("jib-jab", row.get(1).getRichStringCellValue().getString());

      row = obj.get(2);
      assertEquals(2, row.size());
      assertEquals(Cell.CELL_TYPE_STRING, row.get(0).getCellType());
      assertEquals(Cell.CELL_TYPE_NUMERIC, row.get(1).getCellType());
      assertEquals("int", row.get(0).getStringCellValue());
      assertEquals("int", row.get(0).getRichStringCellValue().getString());
      assertEquals(10, row.get(1).getNumericCellValue(), 0);

      row = obj.get(3);
      assertEquals(2, row.size());
      assertEquals(Cell.CELL_TYPE_STRING, row.get(0).getCellType());
      assertEquals(Cell.CELL_TYPE_NUMERIC, row.get(1).getCellType());
      assertEquals("double", row.get(0).getStringCellValue());
      assertEquals("double", row.get(0).getRichStringCellValue().getString());
      assertEquals(3.14, row.get(1).getNumericCellValue(), 0);

      row = obj.get(4);
      assertEquals(2, row.size());
      assertEquals(Cell.CELL_TYPE_STRING, row.get(0).getCellType());
      assertEquals(Cell.CELL_TYPE_NUMERIC, row.get(1).getCellType());
      assertEquals("date", row.get(0).getStringCellValue());
      assertEquals("date", row.get(0).getRichStringCellValue().getString());
      assertEquals(df.parse("1/1/2014"), row.get(1).getDateCellValue());
      assertTrue(DateUtil.isCellDateFormatted(row.get(1)));

      row = obj.get(5);
      assertEquals(7, row.size());
      assertEquals(Cell.CELL_TYPE_STRING, row.get(0).getCellType());
      assertEquals(Cell.CELL_TYPE_STRING, row.get(1).getCellType());
      assertEquals(Cell.CELL_TYPE_STRING, row.get(2).getCellType());
      assertEquals(Cell.CELL_TYPE_STRING, row.get(3).getCellType());
      assertEquals(Cell.CELL_TYPE_STRING, row.get(4).getCellType());
      assertEquals(Cell.CELL_TYPE_STRING, row.get(5).getCellType());
      assertEquals(Cell.CELL_TYPE_STRING, row.get(6).getCellType());
      assertEquals("long", row.get(0).getStringCellValue());
      assertEquals("long", row.get(0).getRichStringCellValue().getString());
      assertEquals("ass", row.get(1).getStringCellValue());
      assertEquals("ass", row.get(1).getRichStringCellValue().getString());
      assertEquals("row", row.get(2).getStringCellValue());
      assertEquals("row", row.get(2).getRichStringCellValue().getString());
      assertEquals("look", row.get(3).getStringCellValue());
      assertEquals("look", row.get(3).getRichStringCellValue().getString());
      assertEquals("at", row.get(4).getStringCellValue());
      assertEquals("at", row.get(4).getRichStringCellValue().getString());
      assertEquals("it", row.get(5).getStringCellValue());
      assertEquals("it", row.get(5).getRichStringCellValue().getString());
      assertEquals("go", row.get(6).getStringCellValue());
      assertEquals("go", row.get(6).getRichStringCellValue().getString());

      row = obj.get(6);
      assertEquals(3, row.size());
      assertEquals(Cell.CELL_TYPE_STRING, row.get(0).getCellType());
      assertEquals(Cell.CELL_TYPE_BOOLEAN, row.get(1).getCellType());
      assertEquals(Cell.CELL_TYPE_BOOLEAN, row.get(2).getCellType());
      assertEquals("boolean", row.get(0).getStringCellValue());
      assertEquals("boolean", row.get(0).getRichStringCellValue().getString());
      assertEquals(true, row.get(1).getBooleanCellValue());
      assertEquals(false, row.get(2).getBooleanCellValue());
    }
  }

  @Test
  public void testGetDateCellValue() throws Exception {
    try(
        InputStream is = new FileInputStream(new File("src/test/resources/data_types.xlsx"));
        StreamingReader reader = StreamingReader.builder().read(is);
    ) {

      List<List<Cell>> obj = new ArrayList<>();

      for(Row r : reader) {
        List<Cell> o = new ArrayList<>();
        for(Cell c : r) {
          o.add(c);
        }
        obj.add(o);
      }

      Date dt = obj.get(4).get(1).getDateCellValue();
      assertNotNull(dt);
      final GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(dt );
      assertEquals(cal.get(Calendar.YEAR), 2014);

      try {
        obj.get(0).get(0).getDateCellValue();
        fail("Should have thrown IllegalStateException");
      } catch(IllegalStateException e) { }
    }
  }
  @Test
  public void testGetDateCellValue1904() throws Exception {
    try(
        InputStream is = new FileInputStream(new File("src/test/resources/1904Dates.xlsx"));
        StreamingReader reader = StreamingReader.builder().read(is);
    ) {

      List<List<Cell>> obj = new ArrayList<>();

      for(Row r : reader) {
        List<Cell> o = new ArrayList<>();
        for(Cell c : r) {
          o.add(c);
        }
        obj.add(o);
      }

      Date dt = obj.get(1).get(5).getDateCellValue();
      assertNotNull(dt);
      final GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(dt );
      assertEquals(cal.get(Calendar.YEAR), 1991);

      try {
        obj.get(0).get(0).getDateCellValue();
        fail("Should have thrown IllegalStateException");
      } catch(IllegalStateException e) { }
    }
  }

  @Test
  public void testGetFirstCellNum() throws Exception {
    try(
        InputStream is = new FileInputStream(new File("src/test/resources/gaps.xlsx"));
        StreamingReader reader = StreamingReader.builder().read(is);
    ) {

      List<List<Cell>> obj = new ArrayList<>();
      List<Row> rows = new ArrayList<>();
      for(Row r : reader) {
        rows.add(r);
        List<Cell> o = new ArrayList<>();
        for(Cell c : r) {
          o.add(c);
        }
        obj.add(o);
      }

      assertEquals(3, rows.size());
      assertEquals(3, rows.get(2).getFirstCellNum());
    }
  }

  @Test
  public void testGaps() throws Exception {
    try(
        InputStream is = new FileInputStream(new File("src/test/resources/gaps.xlsx"));
        StreamingReader reader = StreamingReader.builder().read(is);
    ) {
      List<List<Cell>> obj = new ArrayList<>();

      for(Row r : reader) {
        List<Cell> o = new ArrayList<>();
        for(Cell c : r) {
          o.add(c);
        }
        obj.add(o);
      }

      assertEquals(3, obj.size());
      List<Cell> row;

      row = obj.get(0);
      assertEquals(2, row.size());
      assertEquals(Cell.CELL_TYPE_STRING, row.get(0).getCellType());
      assertEquals(Cell.CELL_TYPE_STRING, row.get(1).getCellType());
      assertEquals("Dat", row.get(0).getStringCellValue());
      assertEquals("Dat", row.get(0).getRichStringCellValue().getString());
      assertEquals(0, row.get(0).getColumnIndex());
      assertEquals(0, row.get(0).getRowIndex());
      assertEquals("gap", row.get(1).getStringCellValue());
      assertEquals("gap", row.get(1).getRichStringCellValue().getString());
      assertEquals(2, row.get(1).getColumnIndex());
      assertEquals(0, row.get(1).getRowIndex());

      row = obj.get(1);
      assertEquals(2, row.size());
      assertEquals(Cell.CELL_TYPE_STRING, row.get(0).getCellType());
      assertEquals(Cell.CELL_TYPE_STRING, row.get(1).getCellType());
      assertEquals("guuurrrrrl", row.get(0).getStringCellValue());
      assertEquals("guuurrrrrl", row.get(0).getRichStringCellValue().getString());
      assertEquals(0, row.get(0).getColumnIndex());
      assertEquals(6, row.get(0).getRowIndex());
      assertEquals("!", row.get(1).getStringCellValue());
      assertEquals("!", row.get(1).getRichStringCellValue().getString());
      assertEquals(6, row.get(1).getColumnIndex());
      assertEquals(6, row.get(1).getRowIndex());
    }
  }

  @Test
  public void testMultipleSheets_alpha() throws Exception {
    try(
        InputStream is = new FileInputStream(new File("src/test/resources/sheets.xlsx"));
        StreamingReader reader = StreamingReader.builder()
            .sheetIndex(0)
            .read(is);
    ) {
      List<List<Cell>> obj = new ArrayList<>();

      for(Row r : reader) {
        List<Cell> o = new ArrayList<>();
        for(Cell c : r) {
          o.add(c);
        }
        obj.add(o);
      }

      assertEquals(1, obj.size());
      List<Cell> row;

      row = obj.get(0);
      assertEquals(1, row.size());
      assertEquals("stuff", row.get(0).getStringCellValue());
      assertEquals("stuff", row.get(0).getRichStringCellValue().getString());
    }
  }

  @Test
  public void testMultipleSheets_zulu() throws Exception {
    try(
        InputStream is = new FileInputStream(new File("src/test/resources/sheets.xlsx"));
        StreamingReader reader = StreamingReader.builder()
            .sheetIndex(1)
            .read(is);
    ) {

      List<List<Cell>> obj = new ArrayList<>();

      for(Row r : reader) {
        List<Cell> o = new ArrayList<>();
        for(Cell c : r) {
          o.add(c);
        }
        obj.add(o);
      }

      assertEquals(1, obj.size());
      List<Cell> row;

      row = obj.get(0);
      assertEquals(1, row.size());
      assertEquals("yeah", row.get(0).getStringCellValue());
      assertEquals("yeah", row.get(0).getRichStringCellValue().getString());
    }
  }

  @Test
  public void testSheetName_zulu() throws Exception {
    try(
        InputStream is = new FileInputStream(new File("src/test/resources/sheets.xlsx"));
        StreamingReader reader = StreamingReader.builder()
            .sheetName("SheetZulu")
            .read(is);
    ) {

      List<List<Cell>> obj = new ArrayList<>();

      for(Row r : reader) {
        List<Cell> o = new ArrayList<>();
        for(Cell c : r) {
          o.add(c);
        }
        obj.add(o);
      }

      assertEquals(1, obj.size());
      List<Cell> row;

      row = obj.get(0);
      assertEquals(1, row.size());
      assertEquals("yeah", row.get(0).getStringCellValue());
      assertEquals("yeah", row.get(0).getRichStringCellValue().getString());
    }
  }

  @Test
  public void testSheetName_alpha() throws Exception {
    try(
        InputStream is = new FileInputStream(new File("src/test/resources/sheets.xlsx"));
        StreamingReader reader = StreamingReader.builder()
            .sheetName("SheetAlpha")
            .read(is);
    ) {
      List<List<Cell>> obj = new ArrayList<>();

      for(Row r : reader) {
        List<Cell> o = new ArrayList<>();
        for(Cell c : r) {
          o.add(c);
        }
        obj.add(o);
      }

      assertEquals(1, obj.size());
      List<Cell> row;

      row = obj.get(0);
      assertEquals(1, row.size());
      assertEquals("stuff", row.get(0).getStringCellValue());
      assertEquals("stuff", row.get(0).getRichStringCellValue().getString());
    }
  }

  @Test(expected = MissingSheetException.class)
  public void testSheetName_missingInStream() throws Exception {
    try(
        InputStream is = new FileInputStream(new File("src/test/resources/sheets.xlsx"));
        StreamingReader reader = StreamingReader.builder()
            .sheetName("adsfasdfasdfasdf")
            .read(is);
    ) {
      fail("Should have failed");
    }
  }

  @Test
  public void testSheetName_missingInFile() throws Exception {
    File f = new File("src/test/resources/sheets.xlsx");
    try(
        StreamingReader reader = StreamingReader.builder()
            .sheetName("adsfasdfasdfasdf")
            .read(f);
    ) {
      fail("Should have failed");
    } catch(MissingSheetException e) {
      assertTrue(f.exists());
    }
  }

  @Test
  public void testIteration() throws Exception {
    File f = new File("src/test/resources/large.xlsx");
    try(
        StreamingReader reader = StreamingReader.builder()
            .rowCacheSize(5)
            .read(f)) {
      int i = 1;
      for(Row r : reader) {
        assertEquals(i, r.getCell(0).getNumericCellValue(), 0);
        assertEquals("#" + i, r.getCell(1).getStringCellValue());
        assertEquals("#" + i, r.getCell(1).getRichStringCellValue().getString());
        i++;
      }
    }
  }

  @Test
  public void testLeadingZeroes() throws Exception {
    File f = new File("src/test/resources/leadingZeroes.xlsx");

    try(StreamingReader reader = StreamingReader.builder().read(f)) {
      Iterator<Row> iter = reader.iterator();
      iter.hasNext();

      Row r1 = iter.next();
      assertEquals(1, r1.getCell(0).getNumericCellValue(), 0);
      assertEquals("1", r1.getCell(0).getStringCellValue());
      assertEquals(Cell.CELL_TYPE_NUMERIC, r1.getCell(0).getCellType());

      Row r2 = iter.next();
      assertEquals(2, r2.getCell(0).getNumericCellValue(), 0);
      assertEquals("0002", r2.getCell(0).getStringCellValue());
      assertEquals("0002", r2.getCell(0).getRichStringCellValue().getString());
      assertEquals(Cell.CELL_TYPE_STRING, r2.getCell(0).getCellType());
    }
  }

  @Test
  public void testReadingEmptyFile() throws Exception {
    File f = new File("src/test/resources/empty_sheet.xlsx");

    try(StreamingReader reader = StreamingReader.builder().read(f)) {
      Iterator<Row> iter = reader.iterator();
      assertThat(iter.hasNext(), is(false));
    }
  }

  @Test
  public void testSpecialStyles() throws Exception {
    File f = new File("src/test/resources/special_types.xlsx");

    Map<Integer, List<Cell>> contents = new HashMap<>();
    try(StreamingReader reader = StreamingReader.builder().read(f)) {
      for(Row row : reader) {
        contents.put(row.getRowNum(), new ArrayList<Cell>());
        for(Cell c : row) {
          if(c.getColumnIndex() > 0) {
            contents.get(row.getRowNum()).add(c);
          }
        }
      }
    }

    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    assertThat(contents.size(), equalTo(2));
    assertThat(contents.get(0).size(), equalTo(4));
    assertThat(contents.get(0).get(0).getStringCellValue(), equalTo("Thu\", \"Dec 25\", \"14"));
    assertThat(contents.get(0).get(0).getDateCellValue(), equalTo(df.parse("25/12/2014")));
    assertThat(contents.get(0).get(1).getStringCellValue(), equalTo("02/04/15"));
    assertThat(contents.get(0).get(1).getDateCellValue(), equalTo(df.parse("04/02/2015")));
    assertThat(contents.get(0).get(2).getStringCellValue(), equalTo("14\". \"Mar\". \"2015"));
    assertThat(contents.get(0).get(2).getDateCellValue(), equalTo(df.parse("14/03/2015")));
    assertThat(contents.get(0).get(3).getStringCellValue(), equalTo("2015-05-05"));
    assertThat(contents.get(0).get(3).getDateCellValue(), equalTo(df.parse("05/05/2015")));

    assertThat(contents.get(1).size(), equalTo(4));
    assertThat(contents.get(1).get(0).getStringCellValue(), equalTo("3.12"));
    assertThat(contents.get(1).get(0).getNumericCellValue(), equalTo(3.12312312312));
    assertThat(contents.get(1).get(1).getStringCellValue(), equalTo("1,023,042"));
    assertThat(contents.get(1).get(1).getNumericCellValue(), equalTo(1023042.0));
    assertThat(contents.get(1).get(2).getStringCellValue(), equalTo("-312,231.12"));
    assertThat(contents.get(1).get(2).getNumericCellValue(), equalTo(-312231.12123145));
    assertThat(contents.get(1).get(3).getStringCellValue(), equalTo("(132)"));
    assertThat(contents.get(1).get(3).getNumericCellValue(), equalTo(-132.0));
  }

  @Test
  public void testBlankNumerics() throws Exception {
    File f = new File("src/test/resources/blank_cells.xlsx");
    try(StreamingReader reader = StreamingReader.builder().read(f)) {
      Row row = reader.iterator().next();
      assertThat(row.getCell(1).getStringCellValue(), equalTo(""));
      assertThat(row.getCell(1).getRichStringCellValue().getString(), equalTo(""));
      assertThat(row.getCell(1).getDateCellValue(), is(nullValue()));
      assertThat(row.getCell(1).getNumericCellValue(), equalTo(0.0));
    }
  }

  @Test
  public void testFirstRowNumIs0() throws Exception {
    File f = new File("src/test/resources/data_types.xlsx");
    try(StreamingReader reader = StreamingReader.builder().read(f)) {
      Row row = reader.iterator().next();
      assertThat(row.getRowNum(), equalTo(0));
    }
  }

  @Test
  public void testNoTypeCell() throws Exception {
    try(
        InputStream is = new FileInputStream(new File("src/test/resources/no_type_cell.xlsx"));
        StreamingReader reader = StreamingReader.builder().read(is);) {
      for(Row r : reader) {
        for(Cell c : r) {
          assertEquals("1", c.getStringCellValue());
        }
      }
    }
  }

  @Test
  public void testEncryption() throws Exception {
    try(
        InputStream is = new FileInputStream(new File("src/test/resources/encrypted.xlsx"));
        StreamingReader reader = StreamingReader.builder().password("test").read(is);) {
      OUTER:
      for(Row r : reader) {
        for(Cell c : r) {
          assertEquals("Demo", c.getStringCellValue());
          assertEquals("Demo", c.getRichStringCellValue().getString());
          break OUTER;
        }
      }
    }
  }

  @Test
  public void testStringCellValue() throws Exception {
    try(
        InputStream is = new FileInputStream(new File("src/test/resources/blank_cell_StringCellValue.xlsx"));
        StreamingReader reader = StreamingReader.builder().read(is);
    ) {
      for(Row r : reader) {
        if(r.getRowNum() == 1) {
          assertEquals("", r.getCell(1).getStringCellValue());
          assertEquals("", r.getCell(1).getRichStringCellValue().getString());
        }
      }
    }
  }

  @Test
  public void testNullValueType() throws Exception {
    try(
        InputStream is = new FileInputStream(new File("src/test/resources/null_celltype.xlsx"));
        StreamingReader reader = StreamingReader.builder().read(is);
    ) {
      for(Row r : reader) {
        for(Cell cell : r) {
          if(r.getRowNum() == 0 && cell.getColumnIndex() == 8) {
            assertEquals(0, cell.getCellType());
            assertEquals("8:00:00", cell.getStringCellValue());
          }
        }
      }
    }
  }

  @Test
  public void testInlineCells() throws Exception {
    try(
        InputStream is = new FileInputStream(new File("src/test/resources/inline.xlsx"));
        StreamingReader reader = StreamingReader.builder().read(is);
    ) {
      Row row = reader.iterator().next();
      assertEquals("First inline cell", row.getCell(0).getStringCellValue());
      assertEquals("First inline cell", row.getCell(0).getRichStringCellValue().getString());
      assertEquals("Second inline cell", row.getCell(1).getStringCellValue());
      assertEquals("Second inline cell", row.getCell(1).getRichStringCellValue().getString());
    }
  }

  @Test
  public void testClosingFiles() throws Exception {
    OPCPackage o = OPCPackage.open(new File("src/test/resources/blank_cell_StringCellValue.xlsx"), PackageAccess.READ);
    o.close();
  }

  @Test
  public void shouldIgnoreSpreadsheetDrawingRows() throws Exception {
    try(
        InputStream is = new FileInputStream(new File("src/test/resources/has_spreadsheetdrawing.xlsx"));
        StreamingReader reader = StreamingReader.builder().read(is);
    ) {
      Iterator<Row> iterator = reader.iterator();
      while(iterator.hasNext()) {
        iterator.next();
      }
    }
  }

	@Test
	public void testShouldReturnNullForMissingCellPolicy_RETURN_BLANK_AS_NULL() throws Exception {
  	  try (
	    InputStream is = new FileInputStream(new File("src/test/resources/blank_cells.xlsx"));
		StreamingReader reader = StreamingReader.builder()
		    .read(is);
		) {
  	  	  Row row = reader.iterator().next();
		  assertNotNull(row.getCell(0, Row.RETURN_BLANK_AS_NULL)); //Remain unchanged
		  assertNull(row.getCell(1, Row.RETURN_BLANK_AS_NULL));
		}
	}

	@Test
	public void testShouldReturnBlankForMissingCellPolicy_CREATE_NULL_AS_BLANK() throws Exception {
  	  try (
	    InputStream is = new FileInputStream(new File("src/test/resources/null_cell.xlsx"));
		StreamingReader reader = StreamingReader.builder()
		    .read(is);
		) {
  	  	  Row row = reader.iterator().next();
		  assertEquals("B1 is Null ->", row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue()); //Remain unchanged
		  assertEquals("B1 is Null ->", row.getCell(0, Row.CREATE_NULL_AS_BLANK).getRichStringCellValue().getString()); //Remain unchanged
		  assertThat(row.getCell(1), is(nullValue()));
		  assertNotNull(row.getCell(1, Row.CREATE_NULL_AS_BLANK));
		}
	}
}
