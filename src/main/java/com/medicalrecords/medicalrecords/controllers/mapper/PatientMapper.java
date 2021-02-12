package com.medicalrecords.medicalrecords.controllers.mapper;

import com.medicalrecords.medicalrecords.dto.PatientDTO;
import com.medicalrecords.medicalrecords.entities.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PatientMapper {

    PatientMapper MAPPER = Mappers.getMapper(PatientMapper.class);

    @Mapping(target = "name", expression ="java(patient.getName().concat(\" \").concat(patient.getSurname()))")
    PatientDTO patientToDto( Patient patient);
}
