package test.java.de.akvilonsoft.monitoring.facade;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import main.de.akvilonsoft.monitoring.facade.MonitoringHelper;
import main.de.akvilonsoft.monitoring.facade.MonitoringHelper.PlainTextQueryType;
import main.de.akvilonsoft.monitoring.logfilereaders.TextFileQueryObject;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MonitoringHelperTest {
    static TextFileQueryObject tfqo = null;
    static MonitoringHelper mh = null;
    static List<String> inputFileNames = new ArrayList<String>();
    static Map<String, String> testmap = new HashMap<String, String>();
    static Map<String, Integer> searchTestMap = new TreeMap<String, Integer>();
    static int numberOfFiles;
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        tfqo = new TextFileQueryObject();
        mh = new MonitoringHelper();
        numberOfFiles = 4;
        inputFileNames.add("logfile1.txt");
        inputFileNames.add("logfile2.txt");
        inputFileNames.add("logfile3.txt");
        inputFileNames.add("logfile4.txt");
        testmap.put("logfile4.txt", "Exception");
        testmap.put("logfile2.txt", "Exception");
        searchTestMap.put("logfile3.txt", 0);
        searchTestMap.put("logfile4.txt", 2);
        searchTestMap.put("logfile1.txt", 0);
        searchTestMap.put("logfile2.txt", 15840);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        tfqo.setLogFilePath("E:/logs");
        tfqo.setLogFileName(".*");
        tfqo.setDateFrom("");
        tfqo.setDateTo("");
        tfqo.setSearchPattern("");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCountAllTextLogfiles() {
        assertEquals(numberOfFiles, PlainTextQueryType.ALLFILES.countAllTextLogfiles(tfqo)); 
    }
    @Test
    public void testTextLogfilesNames() {
        assertEquals(inputFileNames, mh.getTextLogfilesNames(tfqo)); 
    }
    
    @Test
    public void testSearchTextLogfilesForPatternPositiv() {
        tfqo.setSearchPattern("Exception");
        for (Map.Entry<String, String> entry : PlainTextQueryType.ALLFILES.searchTextLogfilesForPattern(tfqo).entrySet()) {
            System.out.println(entry);
        }
        assertEquals(testmap, PlainTextQueryType.ALLFILES.searchTextLogfilesForPattern(tfqo)); 
    }
    
    @Test
    public void testcountEntriesForPattern() {
        tfqo.setSearchPattern("Exception");
        for (Map.Entry<String, Integer> entry : PlainTextQueryType.ALLFILES.countEntriesForPattern(tfqo).entrySet()) {
            System.out.println(entry);
            
        }
        assertEquals(searchTestMap, PlainTextQueryType.ALLFILES.countEntriesForPattern(tfqo) ); 
    }
    
    @Test
    public void testSearchTextLogfilesForPatternNegativ() {
        tfqo.setSearchPattern("uups");
        assertEquals(new HashMap<String, String>(), PlainTextQueryType.ALLFILES.searchTextLogfilesForPattern(tfqo)); 
    }
    
    @Test
    public void testAllLogfilesNamesInTimeInterval() {
        String allFilesNames = "";
        tfqo.setDateFrom("2015/10/04 10:00");
        tfqo.setDateTo("2015/10/06 10:00");
        List<String>  output = mh.getTextLogfilesNames(tfqo);
        for (String name : output) {
            allFilesNames += name;
        }
        assertEquals("logfile1.txt", allFilesNames); 
    }

}
