package com.example.security.security;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.security.entity.ClubMember;
import com.example.security.entity.ClubMemberRole;
import com.example.security.repository.ClubMemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class ClubOAuthMemberDetailsService extends DefaultOAuth2UserService {

    private final ClubMemberRepository clubMemberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 로그인 요청 정보 확인
        log.info("username test : {}", userRequest);

        // 클라이언트 이름, 토큰 추출
        String clientName = userRequest.getClientRegistration().getClientName();
        System.out.println("===========================");
        log.info("clientName{}", clientName);
        log.info(userRequest.getAdditionalParameters());
        log.info("=============================== OAuth2User가 가지고 있는 값");

        // 소설 사용자 정보 로드, 키:값 형태로 로깅
        OAuth2User oAuth2User = super.loadUser(userRequest);
        oAuth2User.getAttributes().forEach((key, value) -> {
            log.info("{} : {}", key, value);
        });

        // 사용자 이메일 추출(소셜 계정이 구글일 경우에만)
        // 소셜 계정 확장시(kakao, naver, ...) 해당 코드 분기 확장 소요 있음
        String email = null;
        if (clientName.equals("Google")) {
            email = oAuth2User.getAttribute("email");
        }

        // 해당 이메일이 DB에 존재하면 불러오고 존재하지 않으면 DB에 기입
        ClubMember member = saveSocialMember(email);

        // ClubAuthMemberDTO 객체 생성
        ClubAuthMemberDTO clubAuthMemberDTO = new ClubAuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                member.isFromSocial(),
                member.getRoleSet()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList()),
                oAuth2User.getAttributes());
        clubAuthMemberDTO.setName(member.getName());

        return clubAuthMemberDTO;
    }

    // 해당 이메일과 관련된 유저 DB에서 탐색, 존재하지 않을 시 DB에 새로 기입하여 반환
    private ClubMember saveSocialMember(String email) {

        // 해당 이메일로 가입된 정보 DB에 있는지 확인
        ClubMember clubMember = clubMemberRepository.findByEmailAndFromSocial(email, true);

        // 해당 이메일로 가입된 정보 DB에 존재하지 않을 시 DB에 새 정보 추가
        if (clubMember == null) {
            ClubMember saveMember = ClubMember.builder()
                    .email(email)
                    .name(email)
                    .password(passwordEncoder.encode("1111")) // 비밀번호 필수 기입이므로 임시로 생성후 암호화
                    .fromSocial(true)
                    .build();

            saveMember.addMemberRole(ClubMemberRole.USER);
            clubMemberRepository.save(saveMember);
            return saveMember;
        }
        return clubMember;
    }

}
