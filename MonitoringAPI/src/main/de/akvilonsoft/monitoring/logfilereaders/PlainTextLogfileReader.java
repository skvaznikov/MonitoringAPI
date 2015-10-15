package main.de.akvilonsoft.monitoring.logfilereaders;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlainTextLogfileReader {
    int zaehler = 0;
    public List<FileInputStream> getAllLogfiles(String filename, String filterForFileName) throws FileNotFoundException {
        ;
        File logfile = new File(filename);
        List<FileInputStream> allLogfiles = new ArrayList<FileInputStream>();
        if (logfile.isDirectory()) {
            for (File file : logfile.listFiles()) {
                if (file.getName().matches(filterForFileName)) {
                    allLogfiles.add(new FileInputStream(file));
                }
            }
        }
        if (logfile.isFile()) {
            if (logfile.getName().matches(filterForFileName)) {
                allLogfiles.add(new FileInputStream(logfile));
            }
        }
        return allLogfiles;
    }

    public List<String> getLogfiles(String filename, String filterForFileName, String von, String to) throws FileNotFoundException, ParseException {
        File logfile = new File(filename);
        boolean isTimeQuery = false;
        Date dateVon = new Date();
        Date dateTo = new Date();;
        List<String> allLogfiles = new ArrayList<String>();
        if (von.length()>0 && to.length()>0) {
            isTimeQuery = true;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            dateVon = simpleDateFormat.parse(von);
            dateTo = simpleDateFormat.parse(to);
        }

        if (logfile.isDirectory()) {
            for (File file : logfile.listFiles()) {
                if (file.getName().matches(filterForFileName)) {
                    if (isTimeQuery) {
                        if (dateVon.getTime() < file.lastModified() && file.lastModified() < dateTo.getTime()) {
                            allLogfiles.add(file.getName());
                        }  
                    }
                    else {
                        allLogfiles.add(file.getName());
                    }
                }
            }
        }
        if (logfile.isFile()) {
            if (logfile.getName().matches(filterForFileName)) {
                allLogfiles.add(logfile.getName());
            }
        }
        return allLogfiles;
    }
    public Map<String, String> searchAllLogfilesNamesForPattern(String filename, String filterForFileName, String searchPattern) throws FileNotFoundException {
        File logfile = new File(filename);
        Map<String, String> allLogfiles = new HashMap<String, String>();
        try {
        if (logfile.isDirectory()) {
            for (File file : logfile.listFiles()) {
                if (file.getName().matches(filterForFileName)) {
                    InputStream is = new FileInputStream(file);
                    String str;              
                        str = readFileInString(is, "UTF-8");                  
                    if (str.contains(searchPattern)) {
                        allLogfiles.put(file.getName(), searchPattern);
                    }
                    
                }
            }
        }
        if (logfile.isFile()) {
            if (logfile.getName().matches(filterForFileName)) {
                InputStream is = new FileInputStream(logfile);
                String str = readFileInString(is, "UTF-8");
                if (str.contains(searchPattern)) {
                    allLogfiles.put(logfile.getName(), searchPattern);
                }
                
            }
            
        }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return allLogfiles;
    }
    
    public Map<String, Integer> countAllEntriesForPattern(String filename, String filterForFileName, String searchPattern) throws FileNotFoundException {
        File logfile = new File(filename);
        Map<String, Integer> allLogfiles = new HashMap<String, Integer>();
        try {
        if (logfile.isDirectory()) {
            for (File file : logfile.listFiles()) {
                if (file.getName().matches(filterForFileName)) {
                    zaehler = 0;
                    InputStream is = new FileInputStream(file);
                    String str;              
                        str = readFileInString(is, "UTF-8");  
                        searchNextToken(str, searchPattern);
                        allLogfiles.put(file.getName(), zaehler);
                    }          
                }
            }
        if (logfile.isFile()) {
            if (logfile.getName().matches(filterForFileName)) {
                InputStream is = new FileInputStream(logfile);
                String str = readFileInString(is, "UTF-8");
                if (str.contains(searchPattern)) {
                    searchNextToken(str, searchPattern);
                    allLogfiles.put(logfile.getName(), zaehler);
                }
                
            }
            
        }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return allLogfiles;
    }

    /**
     * @param searchPattern
     * @param zaehler
     * @param str
     * @return
     */
    private void searchNextToken(String str, String searchPattern ) {
        ArrayList<Integer> positions = new ArrayList<Integer>();
        Pattern p = Pattern.compile(searchPattern);  // insert your pattern here
        Matcher m = p.matcher(str);
        while (m.find()) {
           positions.add(m.start());
           zaehler ++;
        }
    }
    
    public String readFileInString(InputStream inputStream, String encoding)
            throws IOException {
        return new String(readFileInString(inputStream), encoding);
    }    

    private byte[] readFileInString(InputStream inputStream)
            throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos.toByteArray();
    }
    
}
