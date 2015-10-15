package main.de.akvilonsoft.monitoring.logfilereaders;

public final class TextFileQueryObject {
     String logFilePath;
     String logFileName;
     String dateFrom;
     String dateTo;
     String searchPattern;
     
    public final void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }
    public final void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }
    public final void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }
    public final void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
    public final void setSearchPattern(String searchPattern) {
        this.searchPattern = searchPattern;
    }    
    public final String getLogFilePath() {
        return logFilePath;
    }
    public final String getLogFileName() {
        return logFileName;
    }
    public final String getDateFrom() {
        return dateFrom;
    }
    public final String getDateTo() {
        return dateTo;
    }
    public final String getSearchPattern() {
        return searchPattern;
    }
}
