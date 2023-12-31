package telran.java47.communication.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import org.quartz.*;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import telran.java47.communication.dto.ParserRequestForTwelveDataDto;
import telran.java47.quartz.BackupJob;
import telran.java47.quartz.NightLoader;

@Service
@RequiredArgsConstructor
public class SymbolLoader {
	final CommunicationService communicationService;
	public HashMap<String, String> topIndices = new HashMap<>();
	boolean flag = true;
	public static String[] indices;

	public boolean loader() throws Exception, Exception {
		topIndices.put("AAPL", "Apple Inc.D");
		topIndices.put("MSFT", "Microsoft CorporationD");
		topIndices.put("GOOG", "Alphabet Inc.D");
		topIndices.put("GOOGL", "Alphabet Inc.D");
		topIndices.put("AMZN", "Amazon.com, Inc.D");
		topIndices.put("NVDA", "NVIDIA CorporationD");
		topIndices.put("TSLA", "Tesla, Inc.D");
		topIndices.put("BRK.A", "Berkshire Hathaway Inc.D");
		topIndices.put("META", "Meta Platforms, Inc.D");
		topIndices.put("LLY", "Eli Lilly and CompanyD");
		topIndices.put("V", "Visa Inc.D");
		topIndices.put("UNH", "UnitedHealth Group IncorporatedD");
		topIndices.put("XOM", "Exxon Mobil CorporationD");
		topIndices.put("WMT", "Walmart Inc.D");
		topIndices.put("JPM", "JP Morgan Chase & Co.D");
		topIndices.put("JNJ", "Johnson & JohnsonD");
		topIndices.put("MA", "Mastercard IncorporatedD");
		topIndices.put("PG", "Procter & Gamble Company (The)D");
		topIndices.put("AVGO", "Broadcom Inc.D");
		topIndices.put("CVX", "Chevron CorporationD");
		topIndices.put("HD", "Home Depot, Inc. (The)D");
		topIndices.put("ORCL", "Oracle CorporationD");
		topIndices.put("MRK", "Merck & Company, Inc.D");
		topIndices.put("ABBV", "AbbVie Inc.D");
		topIndices.put("KO", "Coca-Cola Company (The)D");
		topIndices.put("COST", "Costco Wholesale CorporationD");
		topIndices.put("PEP", "PepsiCo, Inc.D");
		topIndices.put("ADBE", "Adobe Inc.D");
		topIndices.put("BAC", "Bank of America CorporationD");
		topIndices.put("CSCO", "Cisco Systems, Inc.D");
		topIndices.put("ACN", "Accenture plcD"); 
		topIndices.put("CRM", "Salesforce, Inc.D");
		topIndices.put("MCD", "McDonald's CorporationD");
		topIndices.put("TMO", "Thermo Fisher Scientific IncD");
		topIndices.put("CMCSA", "Comcast CorporationD");
		topIndices.put("SPX", "S&P500 index");
		topIndices.put("NDX", "Nasdaq 100 index");
		topIndices.put("DJI", "Dow Jones Industrial Average Index");
		topIndices.put("NKY", "Nikkei 225 Index");
		topIndices.put("UKX", "FTSE 100");
		topIndices.put("GOLD", "Gold");

		LocalDate from;
		LocalDate to;
		indices = topIndices.keySet().toArray(new String[0]);
		for (String index : indices) {
			try {
				from = communicationService.findTimeLimitsById(index).getFromDate();
				to = LocalDate.now();
				while (to.isAfter(from))
					to = communicationService
							.parsing(new ParserRequestForTwelveDataDto(new String[] { index }, from, to, "1day"));
				Thread.sleep(16000);
				System.out.println("Timeout");
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("load loop interrupted.");
				flag = false;
			}
			System.out.println("load loop finished.");
		}
		return flag;
	}

	public static void scheduleMyJobWithRepeatCount(Scheduler scheduler, int repeatCount,String indicesString) throws SchedulerException {
		
		JobDetail jobDetail = JobBuilder.newJob(NightLoader.class)
				                        .withIdentity("myJob", "group1")
//				                        .usingJobData("Indices", indicesString)
				                        .build();

		SimpleTrigger trigger = TriggerBuilder.newTrigger()
				                              .withIdentity("myTrigger", "group1")
				                              .startNow()
				                              .withSchedule(SimpleScheduleBuilder.simpleSchedule()
				                                                                 .withIntervalInSeconds(16)
				                                                                 .withRepeatCount(repeatCount - 1)
				                                            )
									          .build();

		scheduler.scheduleJob(jobDetail, trigger);
	}
	public static void backUpScedule(Scheduler scheduler) throws SchedulerException {

        JobDetail job = JobBuilder.newJob(BackupJob.class).build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("BackupTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(24)
                        .repeatForever())
                .build();

        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }

}
