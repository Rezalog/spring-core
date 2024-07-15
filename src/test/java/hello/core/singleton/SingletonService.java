package hello.core.singleton;

public class SingletonService {

    /**
     * Singleton pattern 직접 구현(자기자신 클래스에서 직접 생성한 instance 하나외에 생성 불가능하도록 설계)
     * 1. static 하게 private 접근제어자로 1개의 instance 만 생성
     * 2. 외부에서 new 로 인스턴스 생성 불가능 하도록 설계
     * 3. instance 필요시 getInstance로만 접근할 수 있도록 설계
     * */
    private static final SingletonService instance = new SingletonService();

    private SingletonService() {
    }

    public static SingletonService getInstance() {
        return instance;
    }

    public void logic() {
        System.out.println("single logic");
    }

}
