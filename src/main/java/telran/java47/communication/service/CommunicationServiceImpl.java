package telran.java47.communication.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import telran.java47.communication.dto.AllApyIncomeDto;
import telran.java47.communication.dto.ApyIncomDto;
import telran.java47.communication.dto.CalcSumPackageDto;
import telran.java47.communication.dto.CorrelationDto;
import telran.java47.communication.dto.IrrIncomeDto;
import telran.java47.communication.dto.ParsedInfoDto;
import telran.java47.communication.dto.ParserRequestForYahooDto;
import telran.java47.communication.dto.PeriodBeetwinDto;
import telran.java47.communication.dto.PeriodBeetwinIfoDto;
import telran.java47.communication.dto.TimeHistoryLimitsForIndexDto;
import telran.java47.communication.dto.ValueCloseBeetwinDto;
import telran.java47.fintech.dao.StockRepository;


@Service
@RequiredArgsConstructor
public class CommunicationServiceImpl implements CommunicationService {
	
	final StockRepository stockRepository;
	final RestTemplate restTemplate = new RestTemplate();

	@Override
	public TimeHistoryLimitsForIndexDto findTimeLimitsById(String id) {
		// TODO Auto-generated method stub
		TimeHistoryLimitsForIndexDto result=null;
		return result;
		
	}

	@Override
	public String[] getAllIndexes() {	 

	    String[] Indexes = restTemplate.getForObject("http://jsonplaceholder.typicode.com/posts?_limit=10", String.class);
		return null;
	}

	@Override
	public PeriodBeetwinIfoDto periodBeetwin(PeriodBeetwinDto periodBeetwinDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ValueCloseBeetwinDto> valueCloseBeetwin(PeriodBeetwinDto periodBeetwinDto) {
		// TODO Auto-generated method stub
		return null;
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

}
