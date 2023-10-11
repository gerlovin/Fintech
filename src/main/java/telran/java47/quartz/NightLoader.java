package telran.java47.quartz;

import java.time.LocalDate;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import telran.java47.communication.dto.ParserRequestForTwelveDataDto;
import telran.java47.communication.service.CommunicationService;


@RequiredArgsConstructor
public class NightLoader implements Job {
	final CommunicationService communicationService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String[] message = context.getJobDetail().getJobDataMap().getString("Indices").split(",");
		communicationService.parsing(new ParserRequestForTwelveDataDto(new String[] {message[0]}, LocalDate.now().minusDays(2), LocalDate.now(), "1day"));	
		shiftArrayLeft(message, 1);

	}
	public static void shiftArrayLeft(String[] arr, int positions) {
        int length = arr.length;
        positions %= length; // Обработка случая, когда positions больше длины массива
        
        for (int i = 0; i < positions; i++) {
            String temp = arr[0];
            for (int j = 0; j < length - 1; j++) {
                arr[j] = arr[j + 1];
            }
            arr[length - 1] = temp;
        }
    }
}
