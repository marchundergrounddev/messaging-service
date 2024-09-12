package com.vladputnikov.messaging_service.util.mapper;

import com.vladputnikov.messaging_service.persistent.dto.MessageDto;
import com.vladputnikov.messaging_service.persistent.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageMapper {

    MessageDto fromMessageToMessageDto(Message message);

    Message fromMessageDtoToMessage(MessageDto messageDto);
}
