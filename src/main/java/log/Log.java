package log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Criada em 26/01/2018
 * @author Otávio Lara
 */
public class Log{
	
    private final static String PATH_LOG = "log/log.edlog";
    private final static String PATH_FULL_LOG = "log/full_log.edlog";
    private Log() {

    }
    public Log writeOnLog(String mensage, StackTraceElement[] stackTrace) {
        try {
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter(PATH_LOG, true));
            String date = new SimpleDateFormat("yyyy-MM-dd' - 'HH:mm:ss").format(new Date());
            buffWrite.append(date+" -- "+mensage);
            buffWrite.newLine();
            buffWrite.close();
            writeStackTraceOnFullLog(stackTrace,mensage);
        } catch (IOException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }
    
    private void writeStackTraceOnFullLog(StackTraceElement[] stackTrace, String mensage){
        try {
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter(PATH_FULL_LOG, true));
            String date = new SimpleDateFormat("yyyy-MM-dd' - 'HH:mm:ss").format(new Date());
            buffWrite.append(date+"\n"+mensage);
            buffWrite.newLine();
            buffWrite.append(stackTraceToString(stackTrace));
            buffWrite.newLine();
            buffWrite.close();
        } catch (IOException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private String stackTraceToString(StackTraceElement[] stackTrace){
        String stackTraceString = "";
        for(int i=0; i< stackTrace.length; i++){
            
            stackTraceString += stackTrace[i].toString()+"\n";
        }
        System.out.println(stackTraceString);
        return stackTraceString;
    }
    public static Log getInstance() {
        return LogHolder.INSTANCE;
    }

    private static class LogHolder {

        public static final Log INSTANCE = new Log();
    }

}
