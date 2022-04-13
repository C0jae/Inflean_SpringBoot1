package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

// MemberRepository의 구현체
public class MemoryMemberRepository implements MemberRepository {   // option + enter를 통해 모든 구현체 적용 가능

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()  // stream() :
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    // 들어있는 데이터 비우기 : Test 진행용
    public void clearStore() {
        store.clear();
    }
}
