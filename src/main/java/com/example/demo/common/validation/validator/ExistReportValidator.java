package com.example.demo.common.validation.validator;

import com.example.demo.common.response.BaseResponseStatus;
import com.example.demo.common.validation.annotation.ExistReport;
import com.example.demo.common.validation.annotation.ExistUser;
import com.example.demo.src.report.ReportRepository;
import com.example.demo.src.user.UserRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistReportValidator implements ConstraintValidator<ExistReport, Long> {

    private final ReportRepository reportRepository;

    @Override
    public void initialize(ExistReport constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long reportId, ConstraintValidatorContext context) {
        BaseResponseStatus errorStatus;

        boolean isValid;

        // null is invalid
        if (reportId == null) {
            errorStatus = BaseResponseStatus.REPORT_ID_NULL;
            isValid = false;
        } else {
            errorStatus = BaseResponseStatus.REPORT_NOT_FOUND;
            isValid = reportRepository.findById(reportId).isPresent();
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorStatus.toString()).addConstraintViolation();
        }

        return isValid;
    }
}
