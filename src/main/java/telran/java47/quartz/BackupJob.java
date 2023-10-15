package telran.java47.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.io.IOException;
import java.io.*;

public class BackupJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            // setting of mysqldump parameters
        	System.out.println("------------ setting of mysqldump parameters ---------------------------");
            String command = "mysqldump";
            String user = "root";
            String password = "1234";
            String database = "java47";
            String outputFile = "infra/backup_dump.sql"; // path in the container
             
            // building of command
            System.out.println("------------ building of command ---------------------------");
            ProcessBuilder processBuilder = new ProcessBuilder(
                    command,
                    "--host=mysql",
                    "--user=" + user,
                    "--password=" + password,
                    "--all-databases",
                    "--routines",
                    "--result-file=" + outputFile
            );
//            processBuilder.redirectError(/log.txt);

            // Starting process
            System.out.println("------------ Starting process ---------------------------");
            Process process = processBuilder.start();
            
            InputStream errorStream = process.getErrorStream();
            InputStreamReader errorStreamReader = new InputStreamReader(errorStream);
            BufferedReader errorReader = new BufferedReader(errorStreamReader);

            String line;
            while ((line = errorReader.readLine()) != null) {
                // It is possible to write errors to log, for example with using of log4j or java.util.logging
                System.err.println("Error: " + line);
            }


            // waiting result of process
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Backup completed successfully.");
            } else {
                System.err.println("Backup failed. Exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); 
        }
    }
}