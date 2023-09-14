package telran.java47.communication.dto;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ListParsedInfoDto {
     HashMap<String, ParsedInfoDto> mapStocks;
}
