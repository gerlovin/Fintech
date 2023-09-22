package telran.java47.communication.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class SymbolLoader {	
	final CommunicationService communicationService;
	
	public HashMap<String, String> topIndices = new HashMap<>();

	 public void loader() {
		    topIndices.put("AAPL", "Apple Inc.D");
	        topIndices.put("MSFT", "Microsoft CorporationD");
	        topIndices.put("GOOG", "Alphabet Inc.D");
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
	        topIndices.put("GOLD", "Golg");
	        topIndices.put("SPX", "S&P500 index");
	        topIndices.put("NDX", "Nasdaq 100 index");
	        topIndices.put("DJI", "Dow Jones Industrial Average Index");
	        topIndices.put("NKY", "Nikkei 225 Index");
	        topIndices.put("UKX", "FTSE 100");
	        
	        String[] indices = (String[]) topIndices.entrySet().stream()
	        					  .map(e -> e.getKey())	
	        					  .toArray();
			
	        for(String index:indices) {
	        	
	        }
//	        communicationService.parsing(indices);
     }
}
