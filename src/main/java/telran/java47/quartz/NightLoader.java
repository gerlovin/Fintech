package telran.java47.quartz;

import java.time.LocalDate;

import javax.mail.Message;

import org.springframework.context.ApplicationContext;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import telran.java47.communication.dto.ParserRequestForTwelveDataDto;
import telran.java47.communication.service.CommunicationService;
import telran.java47.communication.service.CommunicationServiceImpl;
import telran.java47.communication.service.SymbolLoader;


@RequiredArgsConstructor
public class NightLoader implements Job {
	CommunicationService communicationService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		try {
			ApplicationContext applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");
//			String[] allBeanNames = applicationContext.getBeanDefinitionNames();
//	        for(String beanName : allBeanNames) {
//	            System.out.println(beanName);        <<<<<PRINTING ALL BEAN'S NAMES>>>>>
//	        }
			communicationService = (CommunicationServiceImpl)applicationContext.getBean("communicationServiceImpl");
			LocalDate toDate = communicationService.findTimeLimitsByIdInDB(SymbolLoader.indices[0]).getToDate();
			communicationService.parsing(new ParserRequestForTwelveDataDto(new String[] {SymbolLoader.indices[0]}, toDate, LocalDate.now(), "1day"));	
			shiftArrayLeft(SymbolLoader.indices, 1);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

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

