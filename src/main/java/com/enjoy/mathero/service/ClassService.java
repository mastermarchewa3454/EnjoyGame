package com.enjoy.mathero.service;

import com.enjoy.mathero.shared.dto.ClassDto;

import java.util.List;

public interface ClassService {
    ClassDto create(ClassDto classDto, String teacherId);
    ClassDto getClassByClassName(String className);
    List<ClassDto> getClasses();
}
