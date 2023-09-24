package telran.java47.communication.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import telran.java47.communication.dto.AllApyIncomeDto;
import telran.java47.communication.dto.ApyIncomDto;
import telran.java47.communication.dto.CalcSumPackageDto;
import telran.java47.communication.dto.CorrelationDto;
import telran.java47.communication.dto.IrrIncomeDto;
import telran.java47.communication.dto.ParserRequestForTwelveDataDto;
import telran.java47.communication.dto.PeriodBeetwinDto;
import telran.java47.communication.dto.PeriodBeetwinIfoDto;
import telran.java47.communication.dto.TimeHistoryLimitsForIndexDto;
import telran.java47.communication.dto.ValueCloseBeetwinDto;

public interface CommunicationService {
	
		
	TimeHistoryLimitsForIndexDto findTimeLimitsById(String id) throws Exception, InterruptedException;

	String[] getAllIndices();
	
	String[] getAllIndicesBD();

	List<PeriodBeetwinIfoDto> periodBeetwin(PeriodBeetwinDto periodBeetwinDto); 

	ArrayList<ValueCloseBeetwinDto> valueCloseBeetwin(PeriodBeetwinDto periodBeetwinDto);

	PeriodBeetwinIfoDto calcSumPackage(CalcSumPackageDto calcSumPackageDto);

	ApyIncomDto calcIncomeApy(PeriodBeetwinDto periodBeetwinDto);

	ArrayList<IrrIncomeDto> calcIncomeIrr(PeriodBeetwinDto periodBeetwinDto);

	String correlation(CorrelationDto correlationDto);

	ArrayList<AllApyIncomeDto> calcIncomeAllApy(PeriodBeetwinDto periodBeetwinDto);

	boolean deleteAllHistoryForCompany(String id);

	double prediction(String id);

	
	TimeHistoryLimitsForIndexDto findTimeLimitsByIdInDB(String id);
	
	String testReq(String name);
	LocalDate parsing(ParserRequestForTwelveDataDto parserRequestForTwelveData);

	
}
