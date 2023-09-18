package telran.java47.communication.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
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
import telran.java47.communication.service.CommunicationService;

@RestController
@RequiredArgsConstructor
//@RequestMapping("")
public class CommunicationController {

	final CommunicationService communicationService;
	
	@GetMapping("/parser/{id}")
	@CrossOrigin(origins = "*") 
	public TimeHistoryLimitsForIndexDto findTimeLimitsForId(@PathVariable String id) throws InterruptedException, Exception {
		return communicationService.findTimeLimitsById(id);
	}
//	@GetMapping("/index")
	//Committed by Livshits because there was a mistake
//	@GetMapping("/parser/{id}")
//	public TimeHistoryLimitsForIndexDto findPostById(@PathVariable String id) {
//		return communicationService.findTimeLimitsById(id);
//	}
	
	@GetMapping("communication/index")
	@CrossOrigin(origins = "*") 
	public String[] getAllIndices() {
		return communicationService.getAllIndices();
	}
	
	@GetMapping("communication/indexBD")
	@CrossOrigin(origins = "*") 
	public String[] getAllIndicesBD() {
		return communicationService.getAllIndicesBD();
	}
	
	@GetMapping("/communication/index/{id}")
	@CrossOrigin(origins = "*") 
	public TimeHistoryLimitsForIndexDto findTimeLimitsForIdDB(@PathVariable String id) {
		return communicationService.findTimeLimitsByIdInDB(id);
	}
	

	@PostMapping("/communication/data")
	@CrossOrigin(origins = "*") 
	public ArrayList<ValueCloseBeetwinDto> valueCloseBeetwin(@RequestBody PeriodBeetwinDto periodBeetwinDto) {
		return communicationService.valueCloseBeetwin(periodBeetwinDto);
	}
	@PostMapping("/communication/index/sum")
	@CrossOrigin(origins = "*") 
	public PeriodBeetwinIfoDto calcSumPackage(@RequestBody CalcSumPackageDto calcSumPackageDto) {
		return communicationService.calcSumPackage(calcSumPackageDto);
	}
	@PostMapping("/communication/index/apy")
	@CrossOrigin(origins = "*") 
	public ApyIncomDto calcIncomApy(@RequestBody PeriodBeetwinDto periodBeetwinDto) {
		return communicationService.calcIncomeApy(periodBeetwinDto);
	}
	@PostMapping("/communication/index/apy_all")
	@CrossOrigin(origins = "*") 
	public ArrayList<AllApyIncomeDto> calcIncomAllApy(@RequestBody PeriodBeetwinDto periodBeetwinDto) {
		//DONE
		return communicationService.calcIncomeAllApy(periodBeetwinDto);
	}
	@PostMapping("/indexIrr")
	@CrossOrigin(origins = "*") 
	public ArrayList<IrrIncomeDto> calcIncomIrr(@RequestBody PeriodBeetwinDto periodBeetwinDto) {
		return communicationService.calcIncomeIrr(periodBeetwinDto);
	}
	@PostMapping("/communication/index/correlation")
	@CrossOrigin(origins = "*") 
	public String /*ENUM*/ correlation(@RequestBody CorrelationDto correlationDto) {
		//DONE
		return communicationService.correlation(correlationDto);
	}
	@DeleteMapping("/index{id}")
	@CrossOrigin(origins = "*") 
	public boolean deleteAllHistoryForCompany(@PathVariable String id) {
		return communicationService.deleteAllHistoryForCompany(id);
	}
	@GetMapping("/communication/index/prediction/{id}")
	@CrossOrigin(origins = "*") 
	public double prediction(@PathVariable String id) {
		return communicationService.prediction(id);
	}
	@PostMapping("/parser")
	@CrossOrigin(origins = "*") 
	public boolean parsing(@RequestBody ParserRequestForTwelveDataDto parserRequestForTwelveData) {
		return communicationService.parsing(parserRequestForTwelveData);
	}
	
	@PostMapping("/communication/test")
	@CrossOrigin(origins = "*") 
	public String testReq() {
		return communicationService.testReq("GOLD");
	}
	
	@PostMapping("/communication/index")
	@CrossOrigin(origins = "*") 
	public List<PeriodBeetwinIfoDto> periodBeetwin(@RequestBody PeriodBeetwinDto periodBeetwinDto) {
		//DONE
		return communicationService.periodBeetwin(periodBeetwinDto);
	}
}