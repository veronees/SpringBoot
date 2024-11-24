package com.example.umc7.global.validation.validator;

import com.example.umc7.apipayload.code.status.ErrorStatus;
import com.example.umc7.domain.membermission.dto.request.CreateMemberMissionDTO;
import com.example.umc7.domain.membermission.repository.MemberMissionRepository;
import com.example.umc7.global.validation.annotation.ValidChallengingMission;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChallengingMissionValidator implements
    ConstraintValidator<ValidChallengingMission, CreateMemberMissionDTO> {

    private final MemberMissionRepository memberMissionRepository;

    @Override
    public void initialize(ValidChallengingMission constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CreateMemberMissionDTO createMemberMissionDTO,
        ConstraintValidatorContext context) {
        boolean isChallenging = memberMissionRepository.existsByMemberIdAndMissionIdAndClearStatusIsTrue(
            createMemberMissionDTO.memberId(), createMemberMissionDTO.missionId());

        if (isChallenging) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                ErrorStatus.MEMBERMISSION_ALREADY_CHALLENGING.getMessage()
            ).addConstraintViolation();
            return false;
        }

        return true;
    }
}