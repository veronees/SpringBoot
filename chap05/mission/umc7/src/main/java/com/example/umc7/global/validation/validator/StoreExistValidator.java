package com.example.umc7.global.validation.validator;

import com.example.umc7.apipayload.code.status.ErrorStatus;
import com.example.umc7.domain.store.repository.StoreRepository;
import com.example.umc7.global.validation.annotation.ValidExistStore;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreExistValidator implements ConstraintValidator<ValidExistStore, Long> {

    private final StoreRepository storeRepository;

    @Override
    public void initialize(ValidExistStore constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long storeId, ConstraintValidatorContext context) {

        boolean isExist = storeRepository.existsById(storeId);

        if (!isExist) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.STORE_NOT_FOUND.getMessage())
                .addConstraintViolation();
        }

        return isExist;
    }
}