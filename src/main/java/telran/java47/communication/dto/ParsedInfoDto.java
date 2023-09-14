package telran.java47.communication.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParsedInfoDto implements Serializable {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
MetaDto meta;
  ValueDto[] values;
  String status;
}
