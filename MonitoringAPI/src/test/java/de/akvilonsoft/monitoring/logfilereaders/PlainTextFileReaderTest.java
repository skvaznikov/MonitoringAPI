package test.java.de.akvilonsoft.monitoring.logfilereaders;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;



import main.de.akvilonsoft.monitoring.logfilereaders.PlainTextLogfileReader;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlainTextFileReaderTest {
    static PlainTextLogfileReader pt = null;
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        pt = new PlainTextLogfileReader();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetLogfiles() {
        try {
            List<FileInputStream> test = pt.getAllLogfiles("E:/logs", ".*logfile1.*" );
            int content;
            StringBuffer output = new StringBuffer();
            for (FileInputStream is : test) {
                while ((content = is.read()) != -1) {
                    // convert to char and display it
                    System.out.print((char) content);
                    output.append((char) content);       
                }
            }
            assertEquals("test1" + System.getProperty("line.separator") + "test2", output.toString());           
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
    @Test
    public void testGetLogfilesInTimeIntervall() {
        try {
            List<String> test = pt.getLogfiles("E:/logs", ".*logfile1.*", "2015/10/04 10:00",  "2015/10/06 10:00");
            StringBuffer output = new StringBuffer();
            for (String is : test) {
                    output.append(is);       
            }
            assertEquals("logfile1.txt", output.toString());           
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
    @Test
    public void testGetLogfilesNames() {
        try {
            List<String> test = pt.getLogfiles("E:/logs", ".*", "", "");
            List<String> input = new ArrayList<String>();
            input.add("logfile1.txt");
            input.add("logfile2.txt");
            input.add("logfile3.txt");
            input.add("logfile4.txt");
            assertEquals(input, test);           
        } catch (IOException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
    }

}
