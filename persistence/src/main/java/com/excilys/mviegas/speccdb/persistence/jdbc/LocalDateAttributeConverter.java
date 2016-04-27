package com.excilys.mviegas.speccdb.persistence.jdbc;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Class pour convertir la nouvelle API de temps de Java8
 *
 * Created by excilys on 26/04/16.
 */
@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {

	@Override
	public Date convertToDatabaseColumn(LocalDate locDate) {
		return (locDate == null ? null : Date.valueOf(locDate));
	}

	@Override
	public LocalDate convertToEntityAttribute(Date sqlDate) {
		return (sqlDate == null ? null : sqlDate.toLocalDate());
	}
}

