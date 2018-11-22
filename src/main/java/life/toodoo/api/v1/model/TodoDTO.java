package life.toodoo.api.v1.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TodoDTO 
{
	private String     statusCd;
	private BigDecimal completePct;
	private String     priorityCd;
}
