package telran.java47.communication.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.hibernate.mapping.Array;
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

	//(cron = "0 39 10 * * ?") // 10:39 am
	
	@Scheduled(cron = "0 0 1 * * ?")
	public void cleanNameAmount() {
		LocalDateTime dateTimeDelete = LocalDateTime.now().minusHours(1);
		List<NameAmount> listNameAmounts = packageRepository.findByTimePackageIsBefore(dateTimeDelete);
		if (listNameAmounts != null) {
			packageRepository.deleteAll(listNameAmounts);
		}
	}
	@Scheduled(cron = "0 14 10 * * ?")
	public void LoadNewData() {
		String[] indexes =communicationService.getAllIndexes();
		String[] subIndexes = Arrays.copyOfRange(indexes, 10,12 );
		communicationService.parsing(new ParserRequestForTwelveDataDto(subIndexes, LocalDate.now().minusDays(2), LocalDate.now(), "1day"));				
	}
}
