package com.example.umc7.domain.membermission.service;

import com.example.umc7.apipayload.PageResponseDTO;
import com.example.umc7.apipayload.code.status.ErrorStatus;
import com.example.umc7.apipayload.exception.GeneralException;
import com.example.umc7.domain.member.Member;
import com.example.umc7.domain.member.repository.MemberRepository;
import com.example.umc7.domain.membermission.MemberMission;
import com.example.umc7.domain.membermission.converter.MemberMissionConverter;
import com.example.umc7.domain.membermission.dto.response.MemberMissionResponseDTO;
import com.example.umc7.domain.membermission.repository.MemberMissionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberMissionQueryService {

    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;

    public PageResponseDTO<List<MemberMissionResponseDTO>> getChallengingMissionList(Long memberId,
        int page, int size) {
        Member member = memberRepository.findById(memberId).orElseThrow(
            () -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND)
        );

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<MemberMission> memberMissionPage = memberMissionRepository.findByMember(member,
            pageable);

        List<MemberMissionResponseDTO> memberMissionResponseDTOList = memberMissionPage.stream()
            .map(memberMission -> MemberMissionConverter.toMemberMissionResponseDTO(memberMission))
            .toList();

        return PageResponseDTO.<List<MemberMissionResponseDTO>>builder()
            .page(page)
            .hasNext(memberMissionPage.hasNext())
            .result(memberMissionResponseDTOList)
            .build();
    }
}