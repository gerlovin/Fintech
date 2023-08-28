package telran.java47.communication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OldParsedInfoDto {
	 UploadInfoIdDto uploadInfoId;
     double close;
     int volume;
     double open;
     double high;
     double low;
}
