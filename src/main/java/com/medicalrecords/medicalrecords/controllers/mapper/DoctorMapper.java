package com.medicalrecords.medicalrecords.controllers.mapper;

import com.medicalrecords.medicalrecords.dto.DoctorDTO;
import com.medicalrecords.medicalrecords.entities.Doctor;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DoctorMapper {

    DoctorMapper MAPPER = Mappers.getMapper(DoctorMapper.class);

    @Mapping(target = "name", expression ="java(doctor.getName().concat(\" \").concat(doctor.getSurname()))")
    DoctorDTO doctorToDto(Doctor doctor);
}
