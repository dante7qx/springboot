package org.dante.springboot.slot.helloliteflow;

import java.util.Date;

import com.yomahub.liteflow.entity.data.AbsSlot;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class HelloSlot extends AbsSlot {
	
	private String msgId;
	private Date msgDate;
	
}
