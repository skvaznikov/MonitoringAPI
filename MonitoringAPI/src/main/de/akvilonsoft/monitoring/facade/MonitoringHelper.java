package main.de.akvilonsoft.monitoring.facade;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.de.akvilonsoft.monitoring.logfilereaders.PlainTextLogfileReader;
import main.de.akvilonsoft.monitoring.logfilereaders.TextFileQueryObject;

/**
 * @author E. Skvaznikov
 * Facade of API functionality for monitoring.
 */
public class MonitoringHelper {
    private static final PlainTextLogfileReader textFilereader = new PlainTextLogfileReader();
    
    public List<String> getTextLogfilesNames(TextFileQueryObject queryObject) {
        try {
            return textFilereader.getLogfiles(queryObject.getLogFilePath(),
                    queryObject.getLogFileName(), queryObject.getDateFrom(), queryObject.getDateTo());
        } catch (FileNotFoundException | ParseException e) {
            return new ArrayList<String>();
        }
    }

    public enum PlainTextQueryType {

        /**
         * @author E. Skvaznikov 
         * Query for all logfiles for given pattern in given directory
         */

        ALLFILES() {


            @Override
            public Map<String, String> searchTextLogfilesForPattern(TextFileQueryObject queryObject) {
                try {
                   return textFilereader.searchAllLogfilesNamesForPattern(queryObject.getLogFilePath(), queryObject.getLogFileName(), queryObject.getSearchPattern());
                } catch (FileNotFoundException e) {
                    return new HashMap<String, String>();
                }
                
            }

            @Override
            public int countAllTextLogfiles(TextFileQueryObject queryObject) {
                try {
                    return textFilereader.getAllLogfiles(queryObject.getLogFilePath(), queryObject.getLogFileName())
                            .size();
                } catch (FileNotFoundException e) {
                    return 0;
                }
            }
            
            public Map<String, Integer> countEntriesForPattern(TextFileQueryObject queryObject) {
                try {
                    return textFilereader.countAllEntriesForPattern(queryObject.getLogFilePath(), queryObject.getLogFileName(), queryObject.getSearchPattern());
                 } catch (FileNotFoundException e) {
                     return new HashMap<String, Integer>();
                 }
            }
        },

        /**
         * @author E. Skvaznikov 
         * Query for all logfiles for given pattern in given directory for given timespan
         */
        FILES_IN_TIMESPAN() {



            
            @Override
            public HashMap<String, String> searchTextLogfilesForPattern(TextFileQueryObject queryObject) {
                return new HashMap<String, String>();
            }


            public int countAllTextLogfiles(TextFileQueryObject queryObject) {
                return 0;
            }
            @Override
            public Map<String, Integer> countEntriesForPattern(TextFileQueryObject queryObject) {
                     return new HashMap<String, Integer>();
            }
        };

 //       public abstract List<String> getTextLogfilesNames(TextFileQueryObject queryObject);
        public abstract Map<String, String> searchTextLogfilesForPattern(TextFileQueryObject queryObject);
        public abstract Map<String, Integer> countEntriesForPattern(TextFileQueryObject queryObject);
        public abstract int countAllTextLogfiles(TextFileQueryObject queryObject);
    }
}
