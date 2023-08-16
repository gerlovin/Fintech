package telran.java47.communication.service;

import java.util.ArrayList;
import java.util.List;

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

public interface CommunicationService {
	
		
	TimeHistoryLimitsForIndexDto findTimeLimitsById(String id);

	String[] getAllIndexes();

	PeriodBeetwinIfoDto periodBeetwin(PeriodBeetwinDto periodBeetwinDto); 

	ArrayList<ValueCloseBeetwinDto> valueCloseBeetwin(PeriodBeetwinDto periodBeetwinDto);

	PeriodBeetwinIfoDto calcSumPackage(CalcSumPackageDto calcSumPackageDto);

	ApyIncomDto calcIncomeApy(PeriodBeetwinDto periodBeetwinDto);

	ArrayList<IrrIncomeDto> calcIncomeIrr(PeriodBeetwinDto periodBeetwinDto);

	String correlation(CorrelationDto correlationDto);

	AllApyIncomeDto calcIncomeAllApy(PeriodBeetwinDto periodBeetwinDto);

	boolean deleteAllHistoryForCompany(String id);

	double prediction(String id);

	List<ParsedInfoDto> parsing(ParserRequestForYahooDto parserRequestForYahooDto);

	
}
