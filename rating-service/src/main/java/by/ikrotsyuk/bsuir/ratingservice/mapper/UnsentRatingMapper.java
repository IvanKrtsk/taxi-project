package by.ikrotsyuk.bsuir.ratingservice.mapper;

import by.ikrotsyuk.bsuir.communicationparts.event.RatingUpdatedEvent;
import by.ikrotsyuk.bsuir.ratingservice.entity.UnsentRatingEntity;
import org.bson.types.ObjectId;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UnsentRatingMapper {
    @Mapping(target = "id", expression = "java(objectId)")
    UnsentRatingEntity toEntity(RatingUpdatedEvent ratingUpdatedEvent, @Context ObjectId objectId);

    RatingUpdatedEvent toEvent(UnsentRatingEntity unsentRatingEntity);
}
