package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service // @Service : 스프링이 해당 서비스를 인식하여 스프링컨테이너에 등록시켜준다.
public class MemberService {    // cmd + shift + t : Test클래스 만들기
    private final MemberRepository memberRepository;

    // MemberRepository를 new 로 생성하는것이 아닌 외부에서 주입시킨다. -> testService에서 new로 하나 더 생성하는것을 방지
    // DI(Dependency Injection : 의존성 주입(MemberRepository를 외부에서 주입한다.)
    @Autowired
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
