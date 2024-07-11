package hello.core.member;

public class MemberServiceImpl implements MemberService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;  // MemeberServiceImpl이 더이상 의존성 주입에 개입하지않음(AppConfig가 역할 대체)

    public MemberServiceImpl(MemberRepository memberRepository) { // AppConfig에서 impl의 생성자 생성 시 MemoryMemberRepository와 의존관계 주입
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
