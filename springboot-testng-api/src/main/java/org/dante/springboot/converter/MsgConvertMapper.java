package org.dante.springboot.converter;

import org.dante.springboot.vo.MsgDTO;
import org.dante.springboot.vo.MsgPO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MsgConvertMapper {
	
	@Mapping(target = "sendTime", dateFormat = "yyyy-MM-dd HH:mm:ss.SSS")
	MsgDTO toDTO(MsgPO po);
	
	@InheritInverseConfiguration
	MsgPO toPO(MsgDTO dto);
	
}
