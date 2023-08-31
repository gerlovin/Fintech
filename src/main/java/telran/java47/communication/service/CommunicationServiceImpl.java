package telran.java47.communication.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.text.StyledEditorKit.BoldAction;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import telran.java47.communication.dto.AllApyIncomeDto;
import telran.java47.communication.dto.ApyIncomDto;
import telran.java47.communication.dto.CalcSumPackageDto;
import telran.java47.communication.dto.CorrelationDto;
import telran.java47.communication.dto.EarlestTimestampDto;
import telran.java47.communication.dto.IncomeApyDto;
import telran.java47.communication.dto.IrrIncomeDto;
import telran.java47.communication.dto.ParsedInfoDto;
import telran.java47.communication.dto.ParserRequestForTwelveDataDto;
import telran.java47.communication.dto.PeriodBeetwinDto;
import telran.java47.communication.dto.PeriodBeetwinIfoDto;
import telran.java47.communication.dto.TimeHistoryLimitsForIndexDto;
import telran.java47.communication.dto.TwelveDataSymbolListDto;
import telran.java47.communication.dto.ValueCloseBeetwinDto;
import telran.java47.fintech.dao.DimensionRepository;
import telran.java47.fintech.dao.StockRepository;
import telran.java47.fintech.model.DimensionProcedure;
import telran.java47.fintech.model.StockKey;
import telran.java47.fintech.model.Stock;
import telran.java47.fintech.model.TimeHistoryLimitsForIndex;

import java.net.URI;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class CommunicationServiceImpl implements CommunicationService {

	final StockRepository stockRepository;
	final DimensionRepository dimensionRepository;
	static final RestTemplate restTemplate = new RestTemplate();
	static final String BASE_URL = "https://api.twelvedata.com";
	static final String API_KEY = "b4130a696aff4fa0b4e71c0400ded3b0";
	static LocalDate prevStockDate = LocalDate.now().plusDays(1);

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
		URI uri = URI.create("https://api.twelvedata.com/earliest_timestamp?symbol=" + id + "&interval=1day&outputsize=30&apikey=" + API_KEY);
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("X-RapidAPI-Key", "324c69fb60msh93f7cf5cb241a94p12eb0bjsn5863365f3ef9");
//	    headers.add("X-RapidAPI-Host", "twelve-data1.p.rapidapi.com");
		RequestEntity<String> request = new RequestEntity<>(HttpMethod.GET, uri);
		ResponseEntity<EarlestTimestampDto> response = restTemplate.exchange(request,EarlestTimestampDto.class);	
		TimeHistoryLimitsForIndexDto result= new TimeHistoryLimitsForIndexDto();
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
		
		String source = periodBeetwinDto.getIndexes()[0];
		LocalDate minDate = periodBeetwinDto.getFrom();
		LocalDate maxDate = periodBeetwinDto.getTo();
		List<Stock> stocks = stockRepository.findByStockKeyNameIgnoreCaseStockKeyDateStockBetween(source, minDate, maxDate);
		
		
		String typeS = String.valueOf(periodBeetwinDto.getQuantity()) + ' ' + periodBeetwinDto.getType();
		
		TemporalUnit temporalUnit = getPeriodUnit(periodBeetwinDto.getType().toUpperCase());
		
		ArrayList<ValueCloseBeetwinDto> valuesReturnList  =  stocks.stream()
			.filter(s -> s.getWorkDayOrNot().equals(Boolean.TRUE))
			.map(s -> new ValueCloseBeetwinDto(s.getStockKey().getDateStock(), 
					s.getStockKey().getDateStock().plus(periodBeetwinDto.getQuantity(), temporalUnit), 
					source, typeS, minDate, maxDate, s.getCloseV(), 0, 0, new ArrayList<Double>()))
			.filter(v -> v.getTo().isBefore( v.getMaxDate().plusDays(1)))
			.collect(Collectors.toCollection(ArrayList::new));
		
		Map<LocalDate, Double> mapClosesMap = stocks.stream()
					.collect(Collectors.toMap(s -> s.getStockKey().getDateStock(), s -> s.getCloseV()));
		
		LocalDate maxDate1;
		LocalDate dateCur;

		
		for (ValueCloseBeetwinDto vCDto : valuesReturnList) {
			dateCur = vCDto.getFrom();
			maxDate1 = vCDto.getTo();
			while (!(dateCur.isAfter(maxDate1))) {

				vCDto.getListClose().add(mapClosesMap.get(dateCur));
				if (dateCur.isEqual(maxDate1)) {
					vCDto.setEndClose(mapClosesMap.get(dateCur));
					vCDto.setValueClose(vCDto.getEndClose() - vCDto.getStartClose());
				}
				dateCur = dateCur.plusDays(1);
			}
		}

		return valuesReturnList;
	}

	@Override
	public PeriodBeetwinIfoDto calcSumPackage(CalcSumPackageDto calcSumPackageDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public ApyIncomDto calcIncomeApy(PeriodBeetwinDto periodBeetwinDto) {
		double periodYears = getPeriodInYears(periodBeetwinDto.getType().toUpperCase(), periodBeetwinDto.getQuantity());
		ArrayList<DimensionProcedure>  dimensionList = dimensionRepository.incomeWithAPI(periodBeetwinDto.getIndexes()[0], periodBeetwinDto.getType(), periodBeetwinDto.getQuantity(), periodYears, periodBeetwinDto.getFrom(), periodBeetwinDto.getTo());

		IncomeApyDto minApyDto = createIncomeApyDtoFromDimensionProcedure(dimensionList.get(0));
		IncomeApyDto maxApyDto = createIncomeApyDtoFromDimensionProcedure(dimensionList.get(1));
		String typeS = String.valueOf(periodBeetwinDto.getQuantity()) + ' ' + periodBeetwinDto.getType();
		
		return new ApyIncomDto(periodBeetwinDto.getFrom(), periodBeetwinDto.getTo(), 
				periodBeetwinDto.getIndexes(), typeS, minApyDto, maxApyDto);
	}

	private IncomeApyDto createIncomeApyDtoFromDimensionProcedure(DimensionProcedure dim) {
		
		return new IncomeApyDto(dim.getDateOfPurchase(), dim.getPurchaseAmount(), 
					dim.getDateOfSale(), dim.getSaleAmount(), dim.getIncome(), dim.getDimension());
	}

	@Override
	public ArrayList<IrrIncomeDto> calcIncomeIrr(PeriodBeetwinDto periodBeetwinDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String correlation(CorrelationDto correlationDto) {
		return  stockRepository.correlationCalc(correlationDto.getIndexes()[0].toUpperCase(), 
				correlationDto.getIndexes()[1].toUpperCase(), correlationDto.getFrom(), correlationDto.getTo()).toString();		
	}

	@Override
	public ArrayList<AllApyIncomeDto> calcIncomeAllApy(PeriodBeetwinDto periodBeetwinDto) {
		String source = periodBeetwinDto.getIndexes()[0];
		LocalDate minDate = periodBeetwinDto.getFrom();
		LocalDate maxDate = periodBeetwinDto.getTo();

		System.out.println(periodBeetwinDto.toString());
		List<Stock> stocks = stockRepository.findByStockKeyNameIgnoreCaseStockKeyDateStockBetween(source, minDate, maxDate);
		System.out.println(stocks.size());
		
		String typeS = String.valueOf(periodBeetwinDto.getQuantity()) + ' ' + periodBeetwinDto.getType();
		double yearsCount = getPeriodInYears(periodBeetwinDto.getType().toUpperCase(), periodBeetwinDto.getQuantity());
		System.out.println(yearsCount);
		TemporalUnit temporalUnit = getPeriodUnit(periodBeetwinDto.getType().toUpperCase());
		
		ArrayList<AllApyIncomeDto> valuesReturnList  =  stocks.stream()
				.filter(s -> s.getWorkDayOrNot().equals(Boolean.TRUE))
				.filter(s -> s.getCloseV()!=null)
				.map(s -> new AllApyIncomeDto(source, minDate, maxDate, typeS, 
						s.getStockKey().getDateStock(), s.getStockKey().getDateStock().plus(periodBeetwinDto.getQuantity(), temporalUnit),
						s.getCloseV(), Double.valueOf(0), Double.valueOf(0), Double.valueOf(0)) )
				.filter(v -> v.getTo().isBefore( v.getHistoryTo().plusDays(1)))
				.collect(Collectors.toCollection(ArrayList::new));
			
			Map<LocalDate, Double> mapClosesMap = stocks.stream()
					.filter(s -> s.getCloseV()!=null)
					.collect(Collectors.toMap(s -> s.getStockKey().getDateStock(), s -> s.getCloseV()));
			
		valuesReturnList.stream()
			.forEach(v -> {v.setSaleAmount(mapClosesMap.get(v.getTo())); 
						v.setIncome(v.getSaleAmount() - v.getPurchaseAmount());	
						v.setApy((Math.pow(v.getSaleAmount()/v.getPurchaseAmount(), 1/yearsCount) - 1) *100);
						});
			
		return valuesReturnList;
	}

	private double getPeriodInYears(String periodString, int i) {
		int daysInYear = LocalDate.of(LocalDate.now().getYear(), 1, 1).minusDays(1).getDayOfYear();
		switch (periodString) {
		case "DAYS":
			return i/daysInYear;
		case "WEEKS":
			return i/(daysInYear/7);
		case "MONTHS":
			return i/12;
		case "YEARS":
			return i;
		default:
			return 0;
		}
	}

	private TemporalUnit getPeriodUnit(String periodString) {
		switch (periodString) {
		case "DAYS":
			return ChronoUnit.DAYS;
		case "WEEKS":
			return ChronoUnit.WEEKS;
		case "MONTHS":
			return ChronoUnit.MONTHS;
		case "YEARS":
			return ChronoUnit.YEARS;
		default:
			return null;
		}
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
//	public ParsedInfoDto parsing(ParserRequestForTwelveDataDto parserRequestForTwelveData) {
	public boolean parsing(ParserRequestForTwelveDataDto parserRequestForTwelveData) {
		for (int i = 0; i <= parserRequestForTwelveData.getSource().length-1; i++) {
			prevStockDate =  parserRequestForTwelveData.getToData();
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/time_series")				
				.queryParam("end_date", parserRequestForTwelveData.getToData())
				.queryParam("start_date", parserRequestForTwelveData.getFromData())
				.queryParam("interval", parserRequestForTwelveData.getType())
				.queryParam("symbol", parserRequestForTwelveData.getSource()[i])
				.queryParam("apikey", API_KEY);
		RequestEntity<String> request = new RequestEntity<>(HttpMethod.GET,builder.build().toUri());
		ResponseEntity <ParsedInfoDto> response = restTemplate.exchange(request, ParsedInfoDto.class);
		System.out.println("response :" + response.getBody());
		ParsedInfoDto newData = response.getBody();
		Arrays.stream(newData.getValues())
		             .map(
		                 s -> new Stock(new StockKey(newData.getMeta().getSymbol(), s.getDatetime()),
		            	 s.getOpen(),s.getHigh(),s.getLow(),s.getClose(),s.getClose(),s.getVolume(), true)
		            	 )
				.forEach(s -> {
					System.out.println("in forEach");
					if (s.getStockKey().getDateStock().plusDays(1).isBefore(prevStockDate)) {
						System.out.println("in if");
						LocalDate currentStockDate = s.getStockKey().getDateStock();
						while (currentStockDate.isBefore(prevStockDate)) 
						{
						System.out.println(prevStockDate + "prev in while");
						System.out.println(currentStockDate.plusDays(1) + "s + 1 in while");
						prevStockDate = prevStockDate.minusDays(1);
						s.setStockKey(new StockKey(newData.getMeta().getSymbol(),prevStockDate));
						if (currentStockDate.isEqual(prevStockDate)) {s.setWorkDayOrNot(true);}
						else s.setWorkDayOrNot(false);
						stockRepository.save(s);
						
						}
					} else {
					stockRepository.save(s);
					prevStockDate = s.getStockKey().getDateStock();
					System.out.println(prevStockDate);
				}});
	}
//		return 
		
		return true;
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
