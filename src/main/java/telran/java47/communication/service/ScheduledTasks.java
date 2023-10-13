package telran.java47.communication.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import telran.java47.communication.dto.ParserRequestForTwelveDataDto;
import telran.java47.fintech.dao.PackageRepository;
import telran.java47.fintech.model.NameAmount;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
	final PackageRepository packageRepository;
	final CommunicationService communicationService;
	final SymbolLoader symbolLoader;
	final Scheduler scheduler;
	

	//(cron = "0 39 10 * * ?") // 10:39 am
	
	@Scheduled(cron = "0 0 1 * * ?")
	public void cleanNameAmount() {
		LocalDateTime dateTimeDelete = LocalDateTime.now().minusHours(1);
		List<NameAmount> listNameAmounts = packageRepository.findByTimePackageIsBefore(dateTimeDelete);
		if (listNameAmounts != null) {
			packageRepository.deleteAll(listNameAmounts);
		}
	}
	@Scheduled(cron = "0 20 18 * * ?")
	public void LoadNewData() throws SchedulerException {
		System.out.println("In loader");
		SymbolLoader.indices = communicationService.getAllIndicesBD();
		SymbolLoader.scheduleMyJobWithRepeatCount(scheduler, SymbolLoader.indices.length, "");
//		String indicesString = Arrays.stream(indices).reduce("",(s, n) -> s+n+",") ;
//		SymbolLoader.scheduleMyJobWithRepeatCount(scheduler, indices.length, indicesString);
//		String[] indices =communicationService.getAllIndices();
//		String[] subIndices = Arrays.copyOfRange(indices, 10,12 );
//		communicationService.parsing(new ParserRequestForTwelveDataDto(subIndices, LocalDate.now().minusDays(2), LocalDate.now(), "1day"));				
	}
	@Scheduled(cron = "0 51 19 * * ?")
	public void cronTest() {
		System.out.println("************************In test******************************************");
	}
}

 