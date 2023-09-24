package telran.java47.communication.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import telran.java47.fintech.dao.PackageRepository;
import telran.java47.fintech.dao.StockRepository;
import telran.java47.fintech.model.DimensionProcedure;
import telran.java47.fintech.model.NameAmount;
import telran.java47.fintech.model.StockKey;
import telran.java47.fintech.model.Stock;
import telran.java47.fintech.model.TimeHistoryLimitsForIndex;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class CommunicationServiceImpl implements CommunicationService {

	final StockRepository stockRepository;
	final DimensionRepository dimensionRepository;
	final PackageRepository packageRepository;
	static final RestTemplate restTemplate = new RestTemplate();
	static final String BASE_URL = "https://api.twelvedata.com";
	static final String API_KEY = "b4130a696aff4fa0b4e71c0400ded3b0";
	static LocalDate prevStockDate = LocalDate.now().plusDays(1);

	@Override
	public TimeHistoryLimitsForIndexDto findTimeLimitsById(String id) throws Exception, InterruptedException {
		URI uri = URI.create("https://api.twelvedata.com/earliest_timestamp?symbol=" + id
				+ "&interval=1day&outputsize=30&apikey=" + API_KEY);
		RequestEntity<String> request = new RequestEntity<>(HttpMethod.GET, uri);
		ResponseEntity<EarlestTimestampDto> response = restTemplate.exchange(request, EarlestTimestampDto.class);
		TimeHistoryLimitsForIndexDto result = new TimeHistoryLimitsForIndexDto();
		result.setSource(id);
		result.setToData(LocalDate.now());
		LocalDate datetime = response.getBody() != null ? response.getBody().getDatetime() : null;
	    if (datetime != null) {
	        result.setFromData(datetime);
	    } else {
	        result.setFromData(LocalDate.now()); 
	    }
		return result;
	}

	@Override
	public String[] getAllIndices() {
		URI uri = URI.create("https://api.twelvedata.com/stocks?exchange=NASDAQ&apikey=" + API_KEY);
		RequestEntity<String> request = new RequestEntity<>(HttpMethod.GET, uri);
		ResponseEntity<TwelveDataSymbolListDto> response = restTemplate.exchange(request,
				TwelveDataSymbolListDto.class);
		return Arrays.stream(response.getBody().getData()).map(s -> s.getSymbol()).toArray(String[]::new);
	}

	@Override
	public String[] getAllIndicesBD() {

		return stockRepository.findDistinctByStockKeyName().stream().toArray(String[]::new);
	}

	@Override
	public List<PeriodBeetwinIfoDto> periodBeetwin(PeriodBeetwinDto periodBeetwinDto) {
		List<PeriodBeetwinIfoDto> resultList = new ArrayList<>();

		PeriodBeetwinIfoDto pbInfoDto;
		System.out.println(periodBeetwinDto.toString());
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < periodBeetwinDto.getIndices().length; i++) {
			String info = stockRepository.periodInfo(periodBeetwinDto.getIndices()[i], periodBeetwinDto.getType(),
					periodBeetwinDto.getQuantity(), periodBeetwinDto.getFrom(), periodBeetwinDto.getTo());
			if (info != null) {
				System.out.println(info);
				String[] infoData = info.split(",");
				pbInfoDto = new PeriodBeetwinIfoDto(periodBeetwinDto.getFrom(), periodBeetwinDto.getTo(),
						periodBeetwinDto.getIndices()[i],
						String.valueOf(periodBeetwinDto.getQuantity()) + ' ' + periodBeetwinDto.getType(),
						Double.valueOf(infoData[0]), Double.valueOf(infoData[1]), Double.valueOf(infoData[2]),
						Double.valueOf(infoData[3]), Double.valueOf(infoData[4]));
				resultList.add(pbInfoDto);
			}
		}
		long t2 = System.currentTimeMillis();
		System.out.println("Time: " + (t2 - t1));
		return resultList;
	}

	@Override
	public ArrayList<ValueCloseBeetwinDto> valueCloseBeetwin(PeriodBeetwinDto periodBeetwinDto) {

		String source = periodBeetwinDto.getIndices()[0];
		LocalDate minDate = periodBeetwinDto.getFrom();
		LocalDate maxDate = periodBeetwinDto.getTo();
		List<Stock> stocks = stockRepository.findByStockKeyNameIgnoreCaseStockKeyDateStockBetween(source, minDate,
				maxDate);

		String typeS = String.valueOf(periodBeetwinDto.getQuantity()) + ' ' + periodBeetwinDto.getType();

		TemporalUnit temporalUnit = getPeriodUnit(periodBeetwinDto.getType().toUpperCase());

		ArrayList<ValueCloseBeetwinDto> valuesReturnList = stocks.stream()
				.filter(s -> s.getWorkDayOrNot().equals(Boolean.TRUE))
				.map(s -> new ValueCloseBeetwinDto(minDate, maxDate, source, typeS, s.getStockKey().getDateStock(),
						s.getStockKey().getDateStock().plus(periodBeetwinDto.getQuantity(), temporalUnit),
						s.getCloseV(), 0, 0, new ArrayList<Double>()))
				.filter(v -> v.getTo().isBefore(v.getMaxDate().plusDays(1)))
				.collect(Collectors.toCollection(ArrayList::new));

		Map<LocalDate, Double> mapClosesMap = stocks.stream()
				.collect(Collectors.toMap(s -> s.getStockKey().getDateStock(), s -> s.getCloseV()));

		LocalDate maxDate1;
		LocalDate dateCur;

		for (ValueCloseBeetwinDto vCDto : valuesReturnList) {
			dateCur = vCDto.getFrom();
			System.out.println(dateCur);
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
		LocalDateTime timePackage = LocalDateTime.now();
		Double idPackage = Math.random();

		for (int i = 0; i < calcSumPackageDto.getIndices().size(); i++) {
			NameAmount nameAmount = new NameAmount(Long.valueOf(0), idPackage, timePackage,
					calcSumPackageDto.getIndices().get(i), calcSumPackageDto.getAmount().get(i));
			packageRepository.save(nameAmount);
		}
		packageRepository.flush();
		String info = stockRepository.calcSumPackage(idPackage, timePackage, calcSumPackageDto.getType(),
				calcSumPackageDto.getQuantity(), calcSumPackageDto.getFrom(), calcSumPackageDto.getTo());
		System.out.println(info);
		PeriodBeetwinIfoDto pbInfoDto = null;
		if (info != null) {
			String concatenatedNames = calcSumPackageDto.getIndices().stream().collect(Collectors.joining(", "));

			String[] infoData = info.split(",");
			pbInfoDto = new PeriodBeetwinIfoDto(calcSumPackageDto.getFrom(), calcSumPackageDto.getTo(),
					"Package for: " + concatenatedNames,
					String.valueOf(calcSumPackageDto.getQuantity()) + ' ' + calcSumPackageDto.getType(),
					Double.valueOf(infoData[0]), Double.valueOf(infoData[1]), Double.valueOf(infoData[2]),
					Double.valueOf(infoData[3]), Double.valueOf(infoData[4]));
		}
		return pbInfoDto;
	}

	@Override
	@Transactional
	public ApyIncomDto calcIncomeApy(PeriodBeetwinDto periodBeetwinDto) {
		double periodYears = getPeriodInYears(periodBeetwinDto.getType().toUpperCase(), periodBeetwinDto.getQuantity());
		ArrayList<DimensionProcedure> dimensionList = dimensionRepository.incomeWithAPI(
				periodBeetwinDto.getIndices()[0], periodBeetwinDto.getType(), periodBeetwinDto.getQuantity(),
				periodYears, periodBeetwinDto.getFrom(), periodBeetwinDto.getTo());

		IncomeApyDto minApyDto = createIncomeApyDtoFromDimensionProcedure(dimensionList.get(0));
		IncomeApyDto maxApyDto = createIncomeApyDtoFromDimensionProcedure(dimensionList.get(1));
		String typeS = String.valueOf(periodBeetwinDto.getQuantity()) + ' ' + periodBeetwinDto.getType();

		return new ApyIncomDto(periodBeetwinDto.getFrom(), periodBeetwinDto.getTo(), periodBeetwinDto.getIndices(),
				typeS, minApyDto, maxApyDto);
	}

	private IncomeApyDto createIncomeApyDtoFromDimensionProcedure(DimensionProcedure dim) {

		return new IncomeApyDto(dim.getDateOfPurchase(), dim.getPurchaseAmount(), dim.getDateOfSale(),
				dim.getSaleAmount(), dim.getIncome(), dim.getDimension());
	}

	@Override
	public ArrayList<IrrIncomeDto> calcIncomeIrr(PeriodBeetwinDto periodBeetwinDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String correlation(CorrelationDto correlationDto) {
		return stockRepository
				.correlationCalc(correlationDto.getIndices()[0].toUpperCase(),
						correlationDto.getIndices()[1].toUpperCase(), correlationDto.getFrom(), correlationDto.getTo())
				.toString();
	}

	@Override
	public ArrayList<AllApyIncomeDto> calcIncomeAllApy(PeriodBeetwinDto periodBeetwinDto) {
		String source = periodBeetwinDto.getIndices()[0];
		LocalDate minDate = periodBeetwinDto.getFrom();
		LocalDate maxDate = periodBeetwinDto.getTo();

		System.out.println(periodBeetwinDto.toString());
		List<Stock> stocks = stockRepository.findByStockKeyNameIgnoreCaseStockKeyDateStockBetween(source, minDate,
				maxDate);
		System.out.println(stocks.size());

		String typeS = String.valueOf(periodBeetwinDto.getQuantity()) + ' ' + periodBeetwinDto.getType();
		double yearsCount = getPeriodInYears(periodBeetwinDto.getType().toUpperCase(), periodBeetwinDto.getQuantity());
		System.out.println(yearsCount);
		TemporalUnit temporalUnit = getPeriodUnit(periodBeetwinDto.getType().toUpperCase());

		ArrayList<AllApyIncomeDto> valuesReturnList = stocks.stream()
				.filter(s -> s.getWorkDayOrNot().equals(Boolean.TRUE)).filter(s -> s.getCloseV() != null)
				.map(s -> new AllApyIncomeDto(source, minDate, maxDate, typeS, s.getStockKey().getDateStock(),
						s.getStockKey().getDateStock().plus(periodBeetwinDto.getQuantity(), temporalUnit),
						s.getCloseV(), Double.valueOf(0), Double.valueOf(0), Double.valueOf(0)))
				.filter(v -> v.getTo().isBefore(v.getHistoryTo().plusDays(1)))
				.collect(Collectors.toCollection(ArrayList::new));

		Map<LocalDate, Double> mapClosesMap = stocks.stream().filter(s -> s.getCloseV() != null)
				.collect(Collectors.toMap(s -> s.getStockKey().getDateStock(), s -> s.getCloseV()));

		valuesReturnList.stream().forEach(v -> {
			v.setSaleAmount(mapClosesMap.get(v.getTo()));
			v.setIncome(v.getSaleAmount() - v.getPurchaseAmount());
			v.setApy((Math.pow(v.getSaleAmount() / v.getPurchaseAmount(), 1 / yearsCount) - 1) * 100);
		});

		return valuesReturnList;
	}

	private double getPeriodInYears(String periodString, int i) {
		int daysInYear = LocalDate.of(LocalDate.now().getYear(), 1, 1).minusDays(1).getDayOfYear();
		switch (periodString) {
		case "DAYS":
			return i / daysInYear;
		case "WEEKS":
			return i / (daysInYear / 7);
		case "MONTHS":
			return i / 12;
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
	public LocalDate parsing(ParserRequestForTwelveDataDto parserRequestForTwelveData) {
		ArrayList<Stock> stockList = new ArrayList<>();
		HashMap<String, ParsedInfoDto> mapStocks = new HashMap<>();
		String symbols = parserRequestForTwelveData.getSource()[0];
		for (int i = 1; i <= parserRequestForTwelveData.getSource().length - 1; i++) {
			symbols = symbols + "," + parserRequestForTwelveData.getSource()[i];
		}
		prevStockDate = parserRequestForTwelveData.getToData();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL + "/time_series")
				.queryParam("end_date", parserRequestForTwelveData.getToData())
				.queryParam("start_date", parserRequestForTwelveData.getFromData())
				.queryParam("interval", parserRequestForTwelveData.getType()).queryParam("symbol", symbols)
				.queryParam("apikey", API_KEY);
		RequestEntity<String> request = new RequestEntity<>(HttpMethod.GET, builder.build().toUri());
		System.out.println(request.getBody());
		System.out.println("source :" + symbols);
		ParameterizedTypeReference<HashMap<String, ParsedInfoDto>> responseType = new ParameterizedTypeReference<HashMap<String, ParsedInfoDto>>() {
		};
		if (parserRequestForTwelveData.getSource().length == 1) {
			ResponseEntity<ParsedInfoDto> response = restTemplate.exchange(request, ParsedInfoDto.class);
			Arrays.stream(response.getBody().getValues())
					.map(s -> new Stock(
							new StockKey(parserRequestForTwelveData.getSource()[0].toUpperCase(), s.getDatetime()),
							s.getOpen(), s.getHigh(), s.getLow(), s.getClose(), s.getClose(), s.getVolume(), true))
					.forEach(s -> {
						if (s.getStockKey().getDateStock().plusDays(1).isBefore(prevStockDate)) {
							System.out.println("in if");
							LocalDate currentStockDate = s.getStockKey().getDateStock();
							while (currentStockDate.isBefore(prevStockDate)) {
								prevStockDate = prevStockDate.minusDays(1);
								Stock stock = new Stock(new StockKey(parserRequestForTwelveData.getSource()[0].toUpperCase(),prevStockDate),
										s.getOpenV(), s.getHighV(), s.getLowV(), s.getCloseV(), s.getCloseV(), s.getVolume(), true);
								if (currentStockDate.isEqual(prevStockDate)) {
									stock.setWorkDayOrNot(true);
									stockList.add(stock);
								} else {
									stock.setWorkDayOrNot(false);
									stockList.add(stock);
									System.out.println("добавлено" + stockList.contains(s) + s.getWorkDayOrNot());
									System.out.println(stockList.stream().filter(e -> e.getWorkDayOrNot().equals(false)).count());
								}
							}
						} else {
							stockList.add(s);
							prevStockDate = s.getStockKey().getDateStock();
						}
						System.out.println(stockList.stream().filter(e -> e.getWorkDayOrNot().equals(false)).count());
					});
		} else {
			ResponseEntity<HashMap<String, ParsedInfoDto>> response = restTemplate.exchange(request, responseType);
			System.out.println("response :" + response.getBody().toString());
			mapStocks = response.getBody();
			mapStocks.entrySet().stream().forEach(en -> {
				Arrays.stream(en.getValue().getValues())
						.map(s -> new Stock(new StockKey(en.getKey().toUpperCase(), s.getDatetime()), s.getOpen(),
								s.getHigh(), s.getLow(), s.getClose(), s.getClose(), s.getVolume(), true))
						.forEach(s -> {
							if (s.getStockKey().getDateStock().plusDays(1).isBefore(prevStockDate)) {
								System.out.println("in if");
								LocalDate currentStockDate = s.getStockKey().getDateStock();
								while (currentStockDate.isBefore(prevStockDate)) {
									System.out.println(prevStockDate + "prev in while");
									System.out.println(currentStockDate.plusDays(1) + "s + 1 in while");
									prevStockDate = prevStockDate.minusDays(1);
									s.setStockKey(new StockKey(en.getKey().toUpperCase(), prevStockDate));
									if (currentStockDate.isEqual(prevStockDate)) {
										s.setWorkDayOrNot(true);
									} else
										s.setWorkDayOrNot(false);
									stockList.add(s);
								}
							} else {
								stockList.add(s);
								prevStockDate = s.getStockKey().getDateStock();
								System.out.println(prevStockDate);
							}
						});
				prevStockDate = parserRequestForTwelveData.getToData();
			});
		}
//		System.out.println(stockList.stream().filter(e -> e.getWorkDayOrNot().equals(false)).count());
//    	stockList.stream().forEach(e -> System.out.println(e));
		stockRepository.saveAll(stockList);

		return stockList.get(stockList.size() - 1).getStockKey().getDateStock();
	}

	@Override
	public TimeHistoryLimitsForIndexDto findTimeLimitsByIdInDB(String id) {
		TimeHistoryLimitsForIndex tIndex = stockRepository.timeLimits(id);
		return new TimeHistoryLimitsForIndexDto(id, tIndex.getFromData(), tIndex.getToData());
	}

	@Override
	public String testReq(String name) {
		List<NameAmount> listNameAmounts = packageRepository.findByTimePackageIsBefore(LocalDateTime.now());
		listNameAmounts.stream().forEach(s -> System.out.println(s));
		System.out.println(listNameAmounts.size());
		return null;
	}

}
