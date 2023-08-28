package telran.java47.communication.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import telran.java47.communication.dto.AllApyIncomeDto;
import telran.java47.communication.dto.ApyIncomDto;
import telran.java47.communication.dto.CalcSumPackageDto;
import telran.java47.communication.dto.CorrelationDto;
import telran.java47.communication.dto.EarlestTimestampDto;
import telran.java47.communication.dto.IrrIncomeDto;
import telran.java47.communication.dto.ParsedInfoDto;
import telran.java47.communication.dto.ParserRequestForYahooDto;
import telran.java47.communication.dto.PeriodBeetwinDto;
import telran.java47.communication.dto.PeriodBeetwinIfoDto;
import telran.java47.communication.dto.TimeHistoryLimitsForIndexDto;
import telran.java47.communication.dto.TwelveDataSymbolListDto;
import telran.java47.communication.dto.ValueCloseBeetwinDto;
import telran.java47.fintech.dao.StockRepository;
import telran.java47.fintech.model.Stock;
import telran.java47.fintech.model.TimeHistoryLimitsForIndex;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class CommunicationServiceImpl implements CommunicationService {

	final StockRepository stockRepository;
	static final RestTemplate restTemplate = new RestTemplate();
	static final String BASE_URL = "https://api.twelvedata.com";
	static final String API_KEY = "b4130a696aff4fa0b4e71c0400ded3b0";

	@Override
	public TimeHistoryLimitsForIndexDto findTimeLimitsById(String id) throws Exception, InterruptedException {
//		HttpRequest request = HttpRequest.newBuilder()
//				.uri(URI.create("https://api.twelvedata.com/earliest_timestamp?symbol=" + id + "&interval=1day&outputsize=30&apikey=" + API_KEY))
//				.header("X-RapidAPI-Key", API_KEY)
//				.header("X-RapidAPI-Host", BASE_URL)
//				.method("GET", HttpRequest.BodyPublishers.noBody())
//				.build();
//		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//		System.out.println(response.body());
		URI uri = URI.create("https://api.twelvedata.com/earliest_timestamp?symbol=" + id
				+ "&interval=1day&outputsize=30&apikey=" + API_KEY);
		HttpHeaders headers = new HttpHeaders();
//		headers.add("X-RapidAPI-Key", "324c69fb60msh93f7cf5cb241a94p12eb0bjsn5863365f3ef9");
//	    headers.add("X-RapidAPI-Host", "twelve-data1.p.rapidapi.com");
		RequestEntity<String> request = new RequestEntity<>(HttpMethod.GET, uri);
		ResponseEntity<EarlestTimestampDto> response = restTemplate.exchange(request, EarlestTimestampDto.class);
		System.out.println(response.getBody().getDatetime());
		TimeHistoryLimitsForIndexDto result = new TimeHistoryLimitsForIndexDto();
		result.setSource(id);
		result.setToData(LocalDate.now());
		result.setFromData(response.getBody().getDatetime());
		return result;

	}

//	static RestTemplate restTemplate = new RestTemplate();
//	static final String BASE_URL = "https://api.apilayer.com/fixer/convert"
//	static final String API_KEY = "gHBm67EZiz0YuSFtzNLmM3VIUEDa74t6";
//	
//	public static void main(String[] args) throws URISyntaxException, IOException {
//		
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		System.out.println("Enter currency from:");
//		String from = br.readLine().trim();
//		System.out.println("Enter currency to:");
//		String to = br.readLine().trim();
//		System.out.println("Enter sum:");
//		double sum = Double.parseDouble(br.readLine().trim());
//		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL)
//				.queryParam("from", from)
//				.queryParam("to", to)
//				.queryParam("amount", sum);
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("apikey", API_KEY);
//		RequestEntity<String> request = new RequestEntity<>(headers, HttpMethod.GET, builder.build().toUri());
//		ResponseEntity<RatesDto> response = restTemplate.exchange(request, RatesDto.class);	
//		System.out.println("Date = " + response.getBody().getDate());
//		System.out.println("Result = " + response.getBody().getResult());
//	}
	@Override
	public String[] getAllIndexes() {
		URI uri = URI.create("https://api.twelvedata.com/stocks?exchange=NASDAQ&apikey=" + API_KEY);
		RequestEntity<String> request = new RequestEntity<>(HttpMethod.GET, uri);
		ResponseEntity<TwelveDataSymbolListDto> response = restTemplate.exchange(request,
				TwelveDataSymbolListDto.class);
		return Arrays.stream(response.getBody().getData()).map(s -> s.getSymbol()).toArray(String[]::new);
	}

	@Override
	public List<PeriodBeetwinIfoDto> periodBeetwin(PeriodBeetwinDto periodBeetwinDto) {
		List<PeriodBeetwinIfoDto> resultList = new ArrayList<>();
		
		PeriodBeetwinIfoDto pbInfoDto;
		System.out.println(periodBeetwinDto.toString());
		for (int i = 0; i < periodBeetwinDto.getIndexes().length; i++) {
			String info = stockRepository.periodInfo(periodBeetwinDto.getIndexes()[i], periodBeetwinDto.getType(), periodBeetwinDto.getQuantity(), periodBeetwinDto.getFrom(), periodBeetwinDto.getTo());
			if (info != null) {
				System.out.println(info);
				String[] infoData = info.split(",");
				pbInfoDto = new PeriodBeetwinIfoDto(periodBeetwinDto.getFrom(), periodBeetwinDto.getTo(),
						periodBeetwinDto.getIndexes()[i],
						String.valueOf(periodBeetwinDto.getQuantity()) + ' ' + periodBeetwinDto.getType(),
						Double.valueOf(infoData[0]), Double.valueOf(infoData[1]), Double.valueOf(infoData[2]),
						Double.valueOf(infoData[3]), Double.valueOf(infoData[4]));
				resultList.add(pbInfoDto);
			}
		}
		return resultList;
	}

	@Override
	public ArrayList<ValueCloseBeetwinDto> valueCloseBeetwin(PeriodBeetwinDto periodBeetwinDto) {
		//List<Stock> stocks = stockRepository.findStockNameAndDate(periodBeetwinDto.getIndexes()[0], periodBeetwinDto.getFrom(), periodBeetwinDto.getTo());
		String source = periodBeetwinDto.getIndexes()[0];
		LocalDate minDate = periodBeetwinDto.getFrom();
		LocalDate maxDate = periodBeetwinDto.getTo();
		List<Stock> stocks = stockRepository.findByStockKeyNameIgnoreCaseStockKeyDateStockBetween(source, minDate, maxDate);
		
	
		System.out.println(stocks.size());
		System.out.println(periodBeetwinDto.toString());
		
		
		String typeS = String.valueOf(periodBeetwinDto.getQuantity()) + ' ' + periodBeetwinDto.getType();
		
		ArrayList<ValueCloseBeetwinDto> valuesReturnList  =  stocks.stream()
			.filter(s -> s.getWorkDayOrNot().equals(Boolean.TRUE))
			.map(s -> new ValueCloseBeetwinDto(s.getStockKey().getDateStock(), 
					s.getStockKey().getDateStock().plus(periodBeetwinDto.getQuantity(), ChronoUnit.DAYS), 
					source, typeS, minDate, maxDate, s.getCloseV(), 0, 0, new ArrayList<Double>()))
			.collect(Collectors.toCollection(ArrayList::new));
		
		return valuesReturnList;
	}

	@Override
	public PeriodBeetwinIfoDto calcSumPackage(CalcSumPackageDto calcSumPackageDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApyIncomDto calcIncomeApy(PeriodBeetwinDto periodBeetwinDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<IrrIncomeDto> calcIncomeIrr(PeriodBeetwinDto periodBeetwinDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String correlation(CorrelationDto correlationDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AllApyIncomeDto calcIncomeAllApy(PeriodBeetwinDto periodBeetwinDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteAllHistoryForCompany(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double prediction(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ParsedInfoDto> parsing(ParserRequestForYahooDto parserRequestForYahooDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimeHistoryLimitsForIndexDto findTimeLimitsByIdInDB(String id) {
		TimeHistoryLimitsForIndex tIndex = stockRepository.timeLimits(id);
		return new TimeHistoryLimitsForIndexDto(id, tIndex.getFromData(), tIndex.getToData());
	}

	@Override
	public String testReq(String name) {

		return stockRepository.testProcedureCall("GOLD", "DAY", 5, LocalDate.now().minusYears(2), LocalDate.now().minusYears(1));
	}

}
