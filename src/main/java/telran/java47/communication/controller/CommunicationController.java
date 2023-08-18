package telran.java47.communication.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
import telran.java47.communication.service.CommunicationService;

@RestController
@RequiredArgsConstructor
//@RequestMapping("")
public class CommunicationController {

	final CommunicationService comunicationService;
	
	@GetMapping("/parser/{id}")
	public TimeHistoryLimitsForIndexDto findTimeLimitsForId(@PathVariable String id) throws InterruptedException, Exception {
		return comunicationService.findTimeLimitsById(id);
	}
	@GetMapping("/index")
	public String[] getAllIndexes() {
		return comunicationService.getAllIndexes();
	}
	@PostMapping("/index")
	public PeriodBeetwinIfoDto periodBeetwin(@RequestBody PeriodBeetwinDto periodBeetwinDto) {
		return comunicationService.periodBeetwin(periodBeetwinDto);
	}
	@PostMapping("/communication/data")
	public ArrayList<ValueCloseBeetwinDto> valueCloseBeetwin(@RequestBody PeriodBeetwinDto periodBeetwinDto) {
		return comunicationService.valueCloseBeetwin(periodBeetwinDto);
	}
	@PostMapping("/index/sum")
	public PeriodBeetwinIfoDto calcSumPackage(@RequestBody CalcSumPackageDto calcSumPackageDto) {
		return comunicationService.calcSumPackage(calcSumPackageDto);
	}
	@PostMapping("/communication/index/apy")
	public ApyIncomDto calcIncomApy(@RequestBody PeriodBeetwinDto periodBeetwinDto) {
		return comunicationService.calcIncomeApy(periodBeetwinDto);
	}
	@PostMapping("/communication/index/apy_all")
	public AllApyIncomeDto calcIncomAllApy(@RequestBody PeriodBeetwinDto periodBeetwinDto) {
		return comunicationService.calcIncomeAllApy(periodBeetwinDto);
	}
	@PostMapping("/indexIrr")
	public ArrayList<IrrIncomeDto> calcIncomIrr(@RequestBody PeriodBeetwinDto periodBeetwinDto) {
		return comunicationService.calcIncomeIrr(periodBeetwinDto);
	}
	@PostMapping("/communication/index/correlation")
	public String /*ENUM*/ correlation(@RequestBody CorrelationDto correlationDto) {
		return comunicationService.correlation(correlationDto);
	}
	@DeleteMapping("/index{id}")
	public boolean deleteAllHistoryForCompany(@PathVariable String id) {
		return comunicationService.deleteAllHistoryForCompany(id);
	}
	@GetMapping("/communication/index/prediction/{id}")
	public double prediction(@PathVariable String id) {
		return comunicationService.prediction(id);
	}
	@PostMapping("/parser")
	public List<ParsedInfoDto> parsing(@RequestBody ParserRequestForYahooDto parserRequestForYahooDto) {
		return comunicationService.parsing(parserRequestForYahooDto);
	}
}