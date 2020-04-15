package com.enjoy.mathero.ui.controller;

import com.enjoy.mathero.exceptions.UserServiceException;
import com.enjoy.mathero.io.entity.UserEntity;
import com.enjoy.mathero.service.ClassService;
import com.enjoy.mathero.service.ResultService;
import com.enjoy.mathero.service.UserService;
import com.enjoy.mathero.shared.CustomList;
import com.enjoy.mathero.shared.MapUtils;
import com.enjoy.mathero.shared.dto.ClassDto;
import com.enjoy.mathero.shared.dto.ClassStageSummaryDto;
import com.enjoy.mathero.shared.dto.UserDto;
import com.enjoy.mathero.ui.model.request.ClassRequestModel;
import com.enjoy.mathero.ui.model.response.ClassRest;
import com.enjoy.mathero.ui.model.response.ClassStageSummaryRest;
import com.enjoy.mathero.ui.model.response.ErrorMessages;
import com.enjoy.mathero.ui.model.response.StudentRest;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(value = "Class Controller", description = "Endpoints connected with classes")
@RestController
@RequestMapping(path = "classes")
public class ClassController {

    @Autowired
    ClassService classService;

    @Autowired
    ResultService resultService;

    @ApiOperation(value = "Create new class")
    @PostMapping
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", paramType = "header", required = true)
    public ClassRest createClass(
            @ApiParam(value = "Class details to store in the database", required = true) @RequestBody ClassRequestModel classRequestModel){
        ClassRest returnValue = new ClassRest();

        ClassDto classDto = new ClassDto();
        classDto.setClassName(classRequestModel.getClassName());
        ClassDto saved = classService.create(classDto, classRequestModel.getTeacherId());

        returnValue.setClassName(saved.getClassName());
        returnValue.setClassId(saved.getClassId());
        returnValue.setTeacherId(saved.getTeacherDetails().getUserId());
        returnValue.setTeacherFirstName(saved.getTeacherDetails().getFirstName());
        returnValue.setTeacherLastName(saved.getTeacherDetails().getLastName());
        returnValue.setStudents(new ArrayList<>());

        return returnValue;
    }

    @ApiOperation(value = "View a list of available classes")
    @GetMapping
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", paramType = "header", required = true)
    public CustomList<ClassRest> getAllClasses(){
        CustomList<ClassRest> returnValue = new CustomList<>();

        List<ClassDto> classDtos = classService.getClasses();
        for(ClassDto classDto: classDtos){
            returnValue.add(MapUtils.classDtoToClassRest(classDto));
        }

        return returnValue;
    }

    @ApiOperation(value = "Return class with provided id")
    @GetMapping(path = "/{classId}")
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", paramType = "header", required = true)
    public ClassRest getClassById(
            @ApiParam(value = "Class id from which class object will be retrieved", required = true) @PathVariable String classId){
        ClassRest returnValue;

        ClassDto classDto = classService.getClassByClassId(classId);

        returnValue = MapUtils.classDtoToClassRest(classDto);
        returnValue.setClassId(classDto.getClassId());

        return returnValue;
    }

    @ApiOperation(value = "Return all summary reports for class with provided id")
    @GetMapping(path = "/{classId}/summary-report-all")
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", paramType = "header", required = true)
    public CustomList<ClassStageSummaryRest> getAllClassStageSummary(
            @ApiParam(value = "Class id for which summary reports will be generated", required = true) @PathVariable String classId){
        CustomList<ClassStageSummaryRest> returnValue = new CustomList<>();

        ClassDto classDto = classService.getClassByClassId(classId);

        List<ClassStageSummaryDto> results = resultService.getAllClassStageSummaryByClassId(classId);

        for(ClassStageSummaryDto dto: results){
            ClassStageSummaryRest rest = new ClassStageSummaryRest();
            BeanUtils.copyProperties(dto, rest);
            returnValue.add(rest);
        }

        return returnValue;

    }

    @ApiOperation(value = "Return specified stage summary report for class with provided id")
    @GetMapping(path = "/{classId}/summary-report")
    @ApiImplicitParam(name = "Authorization", value = "Authorization token", paramType = "header", required = true)
    public ClassStageSummaryRest getClassStageSummary(
            @ApiParam(value = "Stage number for which summary report will be generated", required = true) @RequestParam(name = "stageNumber") int stageNumber,
            @ApiParam(value = "Class id for which summary report will be generated", required = true) @PathVariable String classId){
        ClassStageSummaryRest returnValue = new ClassStageSummaryRest();

        ClassDto classDto = classService.getClassByClassId(classId);

        ClassStageSummaryDto stageSummaryReportDto = resultService.getClassStageSummaryByClassId(classId, stageNumber);
        BeanUtils.copyProperties(stageSummaryReportDto, returnValue);

        return returnValue;
    }

}
