package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

// cmd + shift + t : Test클래스 만들기
public class MemberService {
    private final MemberRepository memberRepository;

    // DI(Dependency Injection : 의존성 주입(MemberRepository를 외부에서 주입한다.)
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원가입
    public Long join(Member member) {
        validateDuplicateMember(member);    // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // ctrl + t -> Extract Method : 해당 소스를 메소드로 변환
        memberRepository.findByName(member.getName())
            .ifPresent(m -> {   // Optional이기 때문에 가능
                throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
