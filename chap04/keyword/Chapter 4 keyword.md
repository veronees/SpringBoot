# Chapter 4. Spring Boot의 코어 개념 (1)

- DI, IOC, 서블릿, DB연결 원리 및 실습

# 📝 학습 목표

---

1. Spring boot의 코어 개념인 IoC 컨테이너, DI(의존성 주입), 서블릿에 대한 개념을 숙지한다.

# 📸 잠깐 ! 스터디 인증샷은 찍으셨나요?📸

---

* 스터디리더께서 대표로 매 주차마다 한 장 남겨주시면 좋겠습니다!🙆💗
 (사진을 저장해서 이미지 임베드를 하셔도 좋고, 복사+붙여넣기해서 넣어주셔도 좋습니다!)

[https://www.notion.so](https://www.notion.so)

# 📑 4주차 주제

1. Spring 프레임워크가 무엇이고, 프레임워크와 API의 개념에 대해 알아본다.
2. Spring의 3대 핵심 개념인 DI, IoC, 서블릿에 대한 기본 개념에 대해 이해한다.

# 🌱 Spring이란?

---

먼저, 가장 기초적인 이야기부터 들어가봅시다. ‘Spring’ 이란 무엇일까요?

관련하여 검색을 해보면 매번 가장 먼저 뜨는 문구가 있습니다.

![image.png](Chapter%204%20Spring%20Boot%E1%84%8B%E1%85%B4%20%E1%84%8F%E1%85%A9%E1%84%8B%E1%85%A5%20%E1%84%80%E1%85%A2%E1%84%82%E1%85%A7%E1%86%B7%20(1)%200cb534e0e42c4b689f0a6798370426e7/image.png)

<aside>
💫

***엔터프라이즈용 Java 애플리케이션 개발을 편하게 할 수 있게 해주는 오픈소스 경량급 애플리케이션 프레임워크***

</aside>

읽다 보면 여기서 하나 의문이 생깁니다.

개발할 때 프레임워크, 프레임워크 하는데 도대체 프레임워크가 무엇일까요…🧐❓❓

## 프레임워크(Framework)

---

**프레임워크란, 어떠한 목적을 쉽게 달성할 수 있도록 해당 목적과 관련된 코드의 뼈대를 미리 만들어둔 것을 의미합니다.**

즉 자주 사용하는 기능에 대한 골조를 만들어놓고, 개발자는 그 안에 구현할 수 있게 되는 거죠.

Spring을 사용하면, Frame(프레임, 뼈대) 안에 Work(작업)을 하는 것이 되는 겁니다.

그런데 여기서 추가적인 의문이 생기실 수도 있을 거예요. 

- ***미리 구성된 걸 끌어서 쓴다는 의미에서, API도 똑같은 거 아닌가??
둘의 차이가 대체 무엇이지?***

## API(Application Programming Interface)

---

API는 Interface(인터페이스, 상호작용) 란 이름에서 알 수 있듯이, 2개 이상의 소프트웨어 컴포넌트 사이에서 상호작용 할 수 있도록 정의된 인터페이스를 말합니다.

즉, 다른 개발자들이 사용할 수 있도록 함수나 메서드, 클래스를 정의하는 것이지요.

## 프레임워크 VS API

---

여턔까지는 너무 추상적으로 설명해드려서 와닿지 않으실 수도 있습니다.

좀 더 보충 설명을 위해 간단한 예시를 하나 들어볼게요.

오랜만에 당신에게 공강의 날이 다가왔습니다. 

오늘만큼은 학교 도서관 구석에서 쓸쓸히 삼각김밥을 먹고 코드 들여다보며 전진하던 거북목 생활에서 잠시라도 벗어나, 늦잠을 자고 맛있는 걸 잔뜩 만들어 먹을 수 있는 특권이 주어진 거죠 ‼️
 ~~*완전 럭키비키자나~🍀🍀*~~

침대 이불 속에 쏙 들어가서 숨만 쉬며 유튜브 쇼츠를 보고 있던 당신은, 우연히 *‘**자취생 요리 쇼츠’*** 를 보게 됩니다.  

그리고 *명란 간장 달걀밥🍳🥚 레시피* 와 눈이 마주쳐버렸습니다.

그 순간 … 당신은 운명적으로 침대에서 벌떡 일어나버리죠.

> ***“ 오늘 저녁은 이 녀석이다! “***
> 

그치만 슬프게도… 당신은 요리 젬병입니다. 

그래서 **쇼츠에 나와있는 레시피 ‘그대로’ 따라**하기로 마음 먹습니다. 이걸 벗어나면 재료만 버린 꼴이 될테니까요‼️

자, 이 경우가 바로 **‘프레임 워크’**입니다. 

‘쇼츠에 나와 있는 레시피’는 따라할 만한 좋은 모범 사례이고, 저는 그대로 따라 하기만 하면, 저녁으로 맛있는 명란 간장 달걀밥을 먹을 수 있겠죠.

그렇게 레시피 그대로 따라 요리하기로 결심한 당신… 

열심히 계란🥚도 까고, 명란도 손질해줍니다. 여기까진 아주 손 쉽다니까요?

그리고 이제 간장 소스만 만들면 되죠. 

그런데 역시나 요리 젬병인 당신은 난관에 부딪히게 되는데… 바로

아무리 만들어도 간장 소스에서 ~~사약~~ 같은 맛이 납니다. 🙀

부엌에서 사부작거리다 결국에는 절망하는 당신을 거실에서 지켜보다 못한 어머니가 말씀하십니다.

> ***으휴, 그러니까 내가 해준다고 했잖아‼️***
> 

주부 경력 3n년 째 이신 믿음직스러운 어머니는 당신을 멀리 보내시고 팔을 걷어붙히시더니 톡 쏘아붙히십니다. 

당신은 이내 환하게 웃음기를 띄운 채, 어머니께  요구사항을 덧붙입니다.

> ***엄마, 간장 소스는 와사비를 조금 넣고 짭짤한 스타일로 부탁해요‼️**  
~~바라는 게 많은 금쪽이…~~*
> 

이게 바로 **API**입니다. 애플리케이션에 비유하자면, 외부 함수나 클래스를 요청하여 끌어 사용하는 것이죠.

이제 잘 이해가 되셨을까요? 😉😉

# Spring IoC 컨테이너

---

Spring IoC(제어의 역전) 컨테이너는 **객체의 생성과 관리를 개발자가 아닌 Spring 프레임워크가 담당**하는 개념입니다. 쉽게 말하면, 객체를 직접 생성하고 연결하는 대신, Spring에게 외주 맡겼다고 생각하면 됩니다. 

개발자는 필요한 객체들만 선언해 두고, Spring이 알아서 적절한 객체를 주입해주는 형태죠.

![**그림 1. Spring IoC 컨테이너**
출처: [https://docs.spring.io/spring-framework/reference/core/beans/basics.html](https://docs.spring.io/spring-framework/reference/core/beans/basics.html)](Chapter%204%20Spring%20Boot%E1%84%8B%E1%85%B4%20%E1%84%8F%E1%85%A9%E1%84%8B%E1%85%A5%20%E1%84%80%E1%85%A2%E1%84%82%E1%85%A7%E1%86%B7%20(1)%200cb534e0e42c4b689f0a6798370426e7/image%201.png)

**그림 1. Spring IoC 컨테이너**
출처: [https://docs.spring.io/spring-framework/reference/core/beans/basics.html](https://docs.spring.io/spring-framework/reference/core/beans/basics.html)

### IoC 컨테이너의 작동 방식

1. **객체를 class로 정의합니다.** 
2. **객체들 간의 연관성 지정**: Spring 설정 파일(Config) 또는 어노테이션(`@Component` , `@Configuration`, `@Autowired`, `@Bean` )을 통해 객체들이 어떻게 연결될지(**의존성 주입**) 지정해줍니.
3. IoC 컨테이너가 이 정보를 바탕으로 객체들을 **생성하고 필요한 곳에 주입합니다**.

IoC 컨테이너는 위의 일련의 과정을 거쳐서 **POJO 기반의 개발**을 가능하게 해주는데요‼️

POJO(Plain Old Java Object)는 복잡한 라이브러리나 프레임워크에 의존하지 않고, 순수한 자바 객체를 의미합니다. 

**Spring은 POJO 기반의 개발을 지향**하며, 이는 프레임워크에 종속적이지 않고 일반적인 자바 객체를 사용하여 우리의 비즈니스 로직을 구현할 수 있다는 거겠죠😉🥳

이렇게 이론적으로만 설명드리면 와닿지 않으시죠? 

그래서 예시 코드를 하나 들어 설명해가면서 차근차근 이해해볼게요☺️

```java
// UserService: POJO 클래스
public class UserService {
    private UserRepository userRepository;

    // 의존성 주입 방법 = 생성자
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id);
    }
}

```

이 예시에서 **`UserService`는 어느 특정한 프레임워크나 라이브러리에 의존하지 않는 순수한 POJO**이며,

Spring IoC 컨테이너가 **`UserRepository`** 객체를 자동으로 주입합니다. 

우리가 직접 의존성 객체(`UserRepository`)를 관리하지 않고도, 간결한 POJO로 비즈니스 로직을 구현할 수 있겠죠!

## 빈(Bean)

간단하게 말해, **Spring 컨테이너가 관리하는 자바의 객체**를 의미합니다.

`ApplicationContext.getBean()` 함수를 호출했을 때 얻어질 수 있는 것이 Spring의 빈입니다.

Spring은 빈(Bean)을 통해 객체를 인스턴스화한 후, 객체 간의 의존 관계를 관리합니다 ‼️

객체가 의존관계를 등록할 때 Spring 컨테이너에서 해당하는 빈을 찾고, 그 빈과 의존성을 만들게 됩니다.

일반적인 자바로 클래스를 하나 만들고, 객체를 생성해봅시다.

```java
public class UMC7thMember {
		private int age;
		private String nickname;
		private String school;
		private String studyPart;
		
		public UMC7thMember(){			
		}
		
		public UMC7thMember(int age, String nickname, String school, String studayPart) {
				this.age = age;
				this.nickname = nickname;
				this.school = school;
				this.studyPart = studyPart;
		}
}
```

### 객체를 직접 생성하는 방식(`new` 생성자)

```java
UMC7thMember umc7thMember = new UMC7thMember(23, "베뉴","이화여자대학교", "Server(SpringBoot)");
```

- 여태껏 자바에서 배웠던 객체 생성 방식은 `new` 키워드를 이용해서 생성하는 방식이었습니다.
- 객체 생성과 의존성 관리를 개발자가 전적으로 책임져야 하는 상황이 발생합니다.

이때, 저희가 채택할 수 있는 것이 바로 `UMC7thMember` 객체를 Bean으로 등록하는 방법이겠죠‼️

### Bean으로 등록하여 관리하는 방식

객체를 Bean으로 등록하는 방법에는 기본적으로 크게 2가지 방법이 있습니다.

[1] `@Component` → `@Autowired` : **묵시적 빈 정의**

- 클래스에 어노테이션을 추가하고, Autowired로 다른 클래스에서 해당 Bean을 끌어온다.

[2] `@Configuration` → `@Bean` : **명시적 빈 정의** 

- Spring 설정 파일에 Configuration 어노테이션을 추가하고, Bean 어노테이션을 붙여 명시적으로 빈을 지정한다.

**[1] `@Component` 어노테이션 사용**

클래스에 `@Component` 어노테이션을 추가하여, Spring이 자동으로 해당 클래스를 스캔하고 Bean으로 등록하도록 할 수 있습니다.

```java
@Component
public class UMC7thMember {
		private int age;
		private String nickname;
		private String school;
		private String studyPart;
		
		public UMC7thMember(){			
		}
		
		public UMC7thMember(int age, String nickname, String school, String studayPart) {
				this.age = age;
				this.nickname = nickname;
				this.school = school;
				this.studyPart = studyPart;
		}
}
```

예를 들어서, 커피 원두(CoffeeBeans)와 커피 머신(CoffeeMachine)을 예시로 들어볼게요.

`@Component` 어노테이션을 붙여주면, Spring은 자동으로 `UMC7thMember` 을 Bean으로 등록하며, 이를 사용할 수 있습니다.

따라서 다른 클래스에서는 `UMC7thMember` 을 `@Autowired` 어노테이션을 통해 주입  받아 사용할 수 있겠죠?

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UMCService {

    private final UMC7thMember umc7thMember;

    @Autowired
    public UMCService(UMC7thMember umc7thMember) {
        this.umc7thMember = umc7thMember;
    }

    public void printMemberInfo() {
        System.out.println("나이: " + umc7thMember.getAge());
        System.out.println("너디나리 닉네임: " + umc7thMember.getNickname());
    }
}
```

# Spring DI(Dependency Injection)

우리나라 말로 풀어쓰면 의존성(dependency) 주입(injection)의 뜻으로, 

객체가 생성자 인수,  메서드에 대한 , 팩토리 메서드에서 생성되거나 반환된 후에, 객체 인스턴스에 설정된 속성을 통해서만 종속성을 정의하는 프로세스입니다.

그런 다음 Spring 컨테이너는 빈을 생성할 때, 이러한 종속성을 주입합니다. 

이 프로세스는 기본적으로 클래스를 직접 생성하거나, service locater 패턴을 사용하여 종속성의 인스턴스화 또는 위치를 스스로 제어하는 것과는 전혀 반대죠.(Inversion of Control)

DI 원칙을 사용하면 코드가 더 깔끔해지고, 객체에 종속성이 제공되면 분리가 더 효과적입니다.

객체는 종속하는 객체의 위치나 클래스를 알지 못하겠죠? 전혀 종속성에 대한 정보가 없습니다.

결과적으로 클래스를 테스트하기가 더 쉬워지며, 특히 종속성이 인터페이스나 추상 기본 클래스에 있는 경우 단위 테스트에서 스텁이나 모의 구현을 할 수 있습니다.

## 의존성(Dependency)이 뭐길래, 왜 외부로부터 주입 받아야 하는데 ⁉️

> ***의존대상 B가 변하면, 그것이 A에 영향을 미친다.
-** 이일민, 토비의 스프링 3.1, 에이콘(2012), p113*
> 

외부로부터 의존성을 주입하는 것이 대체 왜 필요한 건지부터 알아볼까요? 🧐

일단 우리가 개발 상황에서 마주하는 객체들은 서로 ‘의존’되어있을 수밖에 없습니다. 독립적으로 존재하는 객체도 존재할 수도 있지만 로직을 구현하면서 필연적으로 서로 의존성을 지닐 수밖에 없는 경우들이 생길 거예요.

다음과 같은 커피 원두 클래스가 있다고 칩시다. 

```java
public static class CoffeeBeans{
	    public void grind() {
        System.out.println("기본 원두로 갈고 있습니다");
    }
}
```

그 다음은 커피 머신 클래스를 작성하고자 해요.  

```java
public static class CoffeeMachine {
    private CoffeeBeans coffeeBeans;

    public CoffeeMachine() {
        this.coffeeBeans = new CoffeeBeans();
    }

    public void brew() {
        coffeeBeans.grind();
        System.out.println("기본 원두로 커피를 내립니다.");
    }
}
```

이렇게 작성했을 경우를 살펴보시죠.

즉, 커피 원두라는 필드값을 우리가 일전에 정의했던 CoffeeBeans 클래스에서 가져왔어요.

그리고 이제 brew()함수를 통하여 커피를 내리면 되겠죠.

이렇게 코드를 작성한 경우를 바로 **‘강한 결합(Tight Coupling)’**이라고 합니다.

이렇게 되면 오로지 ‘기본 원두’만을 갈 수 있는 **‘원두 일체형’ 커피 머신**이 되는 거예요.

*만약 디카페인 원두를 사용하고 싶다는 문제가 생기면*, 
Coffee Beans클래스 뿐만 아니라, 그 클래스를 필드값으로 하는 Coffee Machine 클래스도 수정해야겠죠.

그렇다면 아무래도 비효율적일 겁니다. **커피 원두(부품)를 바꾸기 위해서** **커피 머신 자체(전체)를 변경해야 하는 셈**이 되니까요. 굉장히 좋지 않은 형태가 되겠죠.

이에 반해, **‘느슨한 결합(Loose Coupling)’**은 **‘원두 분리형’ 커피 머신**을 생각하시면 됩니다.

그러면 코드 단에서는 어떻게 구현될 수 있을까요?

우선 CoffeeBeans를 인터페이스로 정의해봅시다.

```java
public interface CoffeeBeans {
    void grind();
}
```

그 다음, CoffeeBeans 인터페이스를 구현하는 구현체 클래스를 만들어야겠죠?

```java
public static class RegularCoffeeBeans implements CoffeeBeans {
	   @Override
    public void grind() {
        System.out.println("기본 원두로 갈고 있습니다");
    }
}
```

이렇게 구현하게 되었을 때, 다시 똑같이 *만약 디카페인 원두를 사용하고 싶다는 문제가 생겼을 때에는 어떻게 하면 될까요?*

**너무 간단하게도, 다시 커피 원두 인터페이스를 상속 받아, ‘디카페인’이라는 구현체 클래스를 하나 정의해주면 되겠죠.**

```java
public static class DecafCoffeeBeans implements CoffeeBeans {
	   @Override
    public void grind() {
        System.out.println("디카페인 원두로 갈고 있습니다");
    }
}
```

자, 이렇게 분리형 장치는 전체를 이루는 작은 장치들을 각 역할에 따라 ‘부품화’ 합니다. 

그런 다음 똑같이 장치 일부분이 망가졌다고 가정해보죠. 분리형 장치의 경우에는 어떨까요?

고장이 난 일부분에 해당하는 부품만 교체해주면 되겠죠?

‘분리형 장치’는 아까의 장치 전체를 교환해야만 하는 불상사가 일어나지 않게 ‘일체형 장치’ 의 단점을 보완하게 됩니다. 

<aside>
👌

**이처럼 각 객체끼리의 의존성이 존재할 때, 의존성을 외부에서 주입받는 것이 바람직합니다.**

그리고, Spring은 이런 의존성 주입을 자동화해줌으로써, 개발자의 부담을 줄여줍니다.

</aside>

## Spring의 의존성 주입 방식

**1️⃣ 생성자 주입(Constructor Injection) (⭐️)** 권장 방식

**2️⃣ setter 주입(Setter Injection)**

**3️⃣ 필드 주입(Field Injection)**

### **1️⃣** 생성자(Constructor) 주입**(⭐️)**

생성자 기반 DI는 컨테이너가 종속성을 나타내는 여러 인수로 생성자를 호출하여 객체를 생성합니다. 즉, 객체가 생성될 때 필요한 의존성을 모조리 설정해버리는 거죠.

객체의 불변성(immunability)를 보장해줍니다. 즉 대부분의 경우는 의존성이 변경되면 안 되는 경우겠죠. 이때는 생성자 주입이 더욱 권장되는 이유기도 해요.

```java
@Service
public class MemberSignupService {
    private final MemberRepository memberRepository;

    **@Autowired
    public MemberSignupService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }**

		/* 나머지 로직들 */
}
```

또한 근본적으로 생성자는 객체가 생성될 때 필수적인 의존성(필드값)들을 보장하기 때문에, 의존성이 없는 객체를 만들 수가 없게 됩니다. 

### **2️⃣** Setter 주입(Setter Injection)

**Setter 주입 방식은 런타임에 의존성을 주입하기 때문에, 의존성이 없더라도 객체가 생성될 수 있습니다.**

1. 주입 받으려는 빈의 생성자를 호출하여 빈을 찾거나, 빈 팩토리에 등록
2. 생성자 인자에 사용하는 빈을 찾거나, 생성
3. Setter의 인자로 주입

```java
@Service
public class MemberSignupService {
	private final MemberRepository memberRepository;
	
	**@Autowired
	public void setMemberSignupService(final MemberRepository memberRepository) {
			this.memberRepository = memberRepository;
	}**
	
	/* 나머지 로직들 */
}

```

선택적으로 의존성 주입이 가능하겠지만, 필수적인 의존성이 주입되어야만 할 때 이를 강제할 수 없겠죠.

위의 코드에서도 회원가입을 위한 SignupService에서 DB에서 데이터를 접근하는 Repository를 주입받지 않은 채 작동할 수 있으므로, NullPointException 에러가 발생할 수 있다는 매우 치명적인 단점을 수반합니다.

### **3️⃣** 필드 주입(Field Injection)

필드 주입 방식 역시 Setter 주입 방식과 마찬가지로, 

**런타임에 의존성을 주입하기 때문에, 의존성이 없더라도 객체가 생성될 수 있습니다.**

1. 주입받으려는 빈의 생성자를 호출하여 빈을 찾거나 빈 팩토리에 등록
2. 생성자 인자에 사용하는 빈을 찾거나 생성
3. 필드에 주입

```java
@Service
public class MemberSignupService {
		**@Autowired
		private MemberRepository memberRepository;**
	
	/* 나머지 로직들 */
}
```

코드가 깔끔하고 단순해지지만, 필드에 직접 주입되기 때문에 테스트 중에 의존성을 주입하는 것이 어렵다는 단점이 있어요. 

그리고 명시적으로 드러나는 의존성이 없기 때문에, 의존성 구조를 이해하기 어렵게 만들기도 하고요.

특히나 마지막 단점이 매우 치명적인데요. 의존성 관계가 드러나지 않으니까, Spring 컨테이너를 헷갈리게 해서 Bean들 간의 **순환 참조 문제**가 발생할 수 있습니다. *~~(저도 처음에 이것 때문에 삽질을 엄청 했었구요…🙀🙀하하)~~*

# Spring 서블릿(Servlet)이란?

Spring MVC 패턴에서 Controller에 해당하는 부분입니다.

Controller은 다음 주차인 5주차 때 전반적인 프로젝트 구조를 배우면서 더 자세히 알아볼 것이지만, 사용자의 요청을 받아 처리해주는 가장 앞단의 역할을 한다고 생각해주면 됩니다😉

## 서블릿(Servlet)이란?

웹 애플리케이션에서 클라이언트의 요청을 처리하고, 그에 대한 응답을 생성하는 중요한 구성 요소입니다.

즉, 자바에서 웹 애플리케이션을 만들 때 HTTP 요청을 처리하는 역할을 하는 녀석이죠.

![image.png](Chapter%204%20Spring%20Boot%E1%84%8B%E1%85%B4%20%E1%84%8F%E1%85%A9%E1%84%8B%E1%85%A5%20%E1%84%80%E1%85%A2%E1%84%82%E1%85%A7%E1%86%B7%20(1)%200cb534e0e42c4b689f0a6798370426e7/image%202.png)

1. Http Request에 사용자의 Id/Password가 들어가면
2. Http Response로는 로그인 후 페이지를 전송해야 합니다.
3. 사용자의 로그인 정보를 받아 확인 후, 다음 페이지를 보내는 프로그램이 바로 Servlet입니다.
4. 웹 서버는 요청을 WAS(Web Application Server)에게 넘기고, WAS는 요청에 따른 Servlet을 실행합니다. Servlet은 요청에 대한 응답을 클라이언트에게 보냅니다.

![image.png](Chapter%204%20Spring%20Boot%E1%84%8B%E1%85%B4%20%E1%84%8F%E1%85%A9%E1%84%8B%E1%85%A5%20%E1%84%80%E1%85%A2%E1%84%82%E1%85%A7%E1%86%B7%20(1)%200cb534e0e42c4b689f0a6798370426e7/image%203.png)

1. 사용자(클라이언트)가 URL을 입력하면 HTTP Request가 Servlet Container로 전송합니다.
2. 요청을 전송받은 Servlet Container는 HttpServletRequest, HttpServletResponse 객체를 생성합니다.
3. web.xml을 기반으로 사용자가 요청한 URL이 어느 Servlet에 대한 요청인지 찾습니다.
4. 해당 Servlet에서 service() 메소드를 호출한 후, 클라이언트의 GET, POST 여부에 따라 doGet() 혹은 doPost()를 호출합니다.
5. doGet() 혹은 doPost() 메소드는 동적 페이지를 생성한 후, HttpServletResponse 객체에 응답을 보냅니다.
6. 응답이 끝나면 HttpServletRequest, HttpServletResponse 두 객체를 소멸 시킵니다.

## Servlet Container란?

Servlet을 관리해주는 컨테이너 역할입니다.

Servlet이 어떤 역할을 수행하는 **메뉴얼**이라면, Servlet 컨테이너는 해당 메뉴얼을 보고 직접 **핸들링**한다고 생각하시면 됩니다.

서블릿 컨테이너는 클라이언트의 요청(Request)를 받아주고 응답(Response)할 수 있게, 웹 서버와 Socket으로 통신합니다. 

우리가 잘 아는 걸로는 아파치 톰캣이 있죠‼️

톰캣은 실제로 웹 서버와 통신하여 JSP(Java Server Page)와 Servlet이 작동하는 환경을 제공해줍니다.

![image.png](Chapter%204%20Spring%20Boot%E1%84%8B%E1%85%B4%20%E1%84%8F%E1%85%A9%E1%84%8B%E1%85%A5%20%E1%84%80%E1%85%A2%E1%84%82%E1%85%A7%E1%86%B7%20(1)%200cb534e0e42c4b689f0a6798370426e7/image%203.png)

- 앞서 클라이언트의 요청이 들어오면, 서블릿 컨테이너는 web.xml을 기반으로 사용자가 요청한 URL이 어느 서블릿에 대한 요청인지 찾습니다.
해당 서블릿이 메모리에 없을 경우 init()을 통해 생성하고, 서블릿이 변경되었을 경우 destroy() 후 init()을 통해 새로운 내용을 적재합니다.
- 서블릿이 있는 경우 service() 메소드를 통해 요청에 대한 응답이 doGet(), doPost() 등등으로 나뉘어 response가 생성됩니다.
- 컨테이너가 서블릿을 종료시킬 때에는 destroy()를 통해 종료됩니다. ⇒ 자바의 가비지 컬렉션을 통해 편의를 제공합니다.

원래 네트워크를 통한 소통을 위해서는 Socket을 만들고, listen(), accept(), connect() 등으로 구현해야 합니다. (자세한 내용은 워크북 부록인 서버란 무엇인가(소켓 & 멀티 프로세스)를 참고해주세요☺️

Servlet 컨테이너는 이런 기능을 API로 제공하여 간편화했습니다.

결과적으로 우리는 Servlet에 구현해야 할 비즈니스 로직에 대해서만 집중할 수 있겠죠‼️‼️

## Spring의 DispatcherServlet

Spring에서 가장 중요한 서블릿은 **`DispatcherServlet`**입니다. 이는 **Front Controller 패턴**을 구현한 서블릿으로, 모든 HTTP 요청을 받는 녀석이에요. 

이후, 요청을 적절한 Controller로 전달하고, 로직을 실행한 후 응답을 생성해줍니다.

앞의 서블릿 도식과 함께 프로세스를 이해하시면서, 밑의 코드를 봐주세요.

```java
@Controller
public class MyController {

    @GetMapping("/umc")
    public String hello(Model model) {
        model.addAttribute("message", "UMC Spring Fighting!");
        return "greeting";  
    }
}

```

1. **요청**: 클라이언트가 `/umc` URL을 통해 HTTP Request가 들어오면, 이 요청은 DispatcherServlet으로 전달됩니다.
2. **DispatcherServlet의 Handler Mapping**: DispatcherServlet은 해당 HTTP Request를 처리할 수 있는 적합한 Controller을 찾습니다.
3. Controller은 요청을 처리하기 위해 `hello()` 메서드를 호출하고, 맞는 비즈니스 로직을 실행한 후 결과를 반환합니다.
4. 반환된 결과는 `ViewResolver`을 통해 적절한 View로 렌더링해주어,  HTTP Response로 HTML이나 JSON의 형태로써 전달됩니다.

이번 주차에서는 Spring 이 무엇인지, 그리고 관련 주요 내용인 DI와 IoC컨테이너, 그리고 Servlet까지 해서 Spring 프레임워크에 대한 친숙도를 높이는 시간이었습니다.

 이론적인 내용을 좀 더 중심으로 설명하는 주차이기에, 반드시 이해하고 넘어가시길 바라요.

객체 지향의 핵심적인 내용들이 굉장히 많이 담겨 있는 주차라서, 모든 곳에 형광펜을 쳐도 무방하다고 생각됩니다.

# 실습 — Database와 Spring 프로젝트 연결하기

오늘 실습은 정말 간단합니다 ‼️

이제 저희의 Spring 프로젝트와 Database를 연결해보겠습니다.

지난 번 실습 시간에서 사용했던 데이터베이스를 이용할게요.

![image.png](Chapter%204%20Spring%20Boot%E1%84%8B%E1%85%B4%20%E1%84%8F%E1%85%A9%E1%84%8B%E1%85%A5%20%E1%84%80%E1%85%A2%E1%84%82%E1%85%A7%E1%86%B7%20(1)%200cb534e0e42c4b689f0a6798370426e7/image%204.png)

`application.yml` 파일에 다음과 같이 작성해주세요.

MySQL은 포트 번호가 3306입니다.

```
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/**umc7th**
    username: [ username ] 
    password: [ password ]
```

## 🎯핵심 키워드

---

<aside>
💡 주요 내용들에 대해 조사해보고, 자신만의 생각을 통해 정리해보세요!
레퍼런스를 참고하여 정의, 속성, 장단점 등을 적어주셔도 됩니다.
조사는 공식 홈페이지 **Best**, 블로그(최신 날짜) **Not Bad**

</aside>

- **DI**
    
    ### 의존성 주입
    
    - 특정 클라이언트가 의존하는 객체에 대해서 직접 객체를 생성하는 것이 아니라, 외부 주입자에 의해 의존하는 객체를 생성해서 주입해준다. 해당 클라이언트는 외부 주입자에 대해서 알 필요가 없고, 해당 객체를 사용하는 방법 즉, 인터페이스에 대해서만 알고 있으면 된다.
    
    ### 장점
    
    - 클라이언트는 해당 객체의 인터페이스에만 의존하고 있기 때문에, 객체의 형태가 변하더라도 클라이언트에 영향이 적다.
    - 이로 인해 유연한 설계가 가능해지고, DI를 사용하면 클래스가 필요로 하는 의존성을 외부에서 주입받기 때문에, 테스트 환경에서 실제 구현체 대신 Mock 객체나 Stub 객체를 쉽게 주입할 수 있습니다. 이를 통해 외부 의존성을 격리하여 테스트할 수 있다.
- **IoC**
    
    ### 제어
    
    - 개발자가 객체를 직접 생성하고 관리하는 것을 의미한다. 즉, 의존하는 객체를 코드 내에서 직접 생성하는 것이 "제어"에 해당한다.
    - 예를 들어, 클래스 내부에서 `new` 키워드를 통해 의존 객체를 직접 생성하는 방식이다.
    
    ### 역전
    
    - 객체 생성과 의존성 관리의 주체가 변경되는 것을 의미한다.
    - 즉, 객체의 생성 및 주입을 외부에서 관리하고, 클래스 자체는 이를 요청하지 않고 전달받아 사용하는 구조입니다.
    
    ### 제어의 역전(IoC)
    
    - 객체의 생성과 의존성 관리를 외부 컨테이너에서 처리하도록 하는 설계 원칙
    
    ### 스프링의 IoC
    
    - IoC 컨테이너, DI 컨테이너, 스프링 컨테이너와 같은 이름으로 불리고, 해당 컨테이너에는  스프링 빈을 관리되는 객체들이 일반적으로 싱글톤으로 관리된다.
    - 스프링 IoC 컨테이너는 객체의 생성과 의존성 주입을 담당하고, 생성된 객체들을 빈(Bean)으로 관리하며, 이를 싱글톤으로 관리하는 것이 일반적이다. (싱글톤으로 관리하기 때문에 해당 객체는 스레드 세이프하지 않으므르 상태값을 가지는 멤버 변수를 사용을 지양해야한다)
    - 개발자는 스프링 컨테이너에 객체 생성과 의존성 주입을 위임하고, 이를 통해 코드의 결합도를 낮추고 테스트 용이성을 높이는 장점이 있다.
- **프레임워크와 API의 차이**
    
    ### 프레임워크
    
    - 틀(규칙과 구조)를 만들어두고 개발자로 하여금 해당 규칙과 구조를 강제하는 것.
    
    ### API
    
    - 애플리케이션 간 서로 통신하는데 사용하는 약속.
- **AOP**
    
    ### AOP
    
    - Aspect Oriented Programming의 약자이다. 우리말로 `관점 지향 프로그래밍` 이다.
    - 어떤 기준에서 핵심적인 관점, 부가적인 관점으로 나누어 보고 그 관점을 기준으로 각각 모듈화 하겠다는 것이다.
    - 여기서 핵심적인 관점은 우리가 작성하는 비즈니스 로직(일반 적으로 xxService에서 작성하는 로직들)에 대한 것이고, 부가적인 관점(일반적으로 서비스 클래스의 클래스 단 또는 메서드 단에 @Transactional을 붙이는 것)은 비즈니스 로직에서 부가적으로 필요로하는 트랜잭션 처리, 예외처리, 로깅 등이 있다.
    - 코드를 작성하다보면 여러 군데에서 반복되는 코드들이 있다. 예를 들어 @Transactional 어노테이션을 서비스 단에서 많이 사용한다.
    - 이것을 `흩어진 관심사` 라고 부른다.
    - 이렇게 흩어진 관심사를 Aspect(관점)으로 모듈화하고 비즈니스 로직에서 분리하여 재사용하겠다는 것이 AOP의 취지이다.
    - 우리는 @Transactional  어노테이션만 선언해주면 트랜잭션을 얻어오고 시작 그리고 커밋과 종료하는 코드를 비즈니스 로직에서 제외하고 온전히 비즈니스 로직에만 집중할 수 있는 것이다.
    
    ### 스프링에서 AOP
    
    - 프록시 패턴을 사용해서 AOP를 해결한다.
    - 비즈니스 로직 호출 시 실제 객체가 아니라 부가 기능이 포함된 프록시 객체가 호출된다.
    - 호출된 프록시 객체가 부가 기능을 수행 후, 실제 객체를 호출해서 비즈니스 로직을 수행한다.
    - 그렇기 때문에 AOP가 적용된 메서드의 접근 제어자는 private일 수 없고 같은 객체 내에서의 호출 시 먜AOP가 적용되지 않는다.
- **서블릿**
    
    ### 서블릿
    
    - 자바 기반의 웹 컴포넌트
    - 정적 웹 페이지의 문제점을 보완하여 나온 것이 동적 웹페이지를 구현하는 것
    - 클라이언트의 HTTP 요청을 받고, 그에 대한 응답을 생성

## 📢 학습 후기

---

- 이번 주차 워크북을 해결해보면서 어땠는지 회고해봅시다.
- 핵심 키워드에 대해 완벽하게 이해했는지? 혹시 이해가 안 되는 부분은 뭐였는지?

<aside>
💡

</aside>

## ⚠️ 스터디 진행 방법

---

1. 스터디를 진행하기 전, 워크북 내용들을 모두 채우고 스터디에서는 서로 모르는 내용들을 공유해주세요.
2. 미션은 워크북 내용들을 모두 완료하고 나서 스터디 전/후로 진행해보세요.
3. 다음주 스터디를 진행하기 전, 지난주 미션을 서로 공유해서 상호 피드백을 진행하시면 됩니다.

## ✅ 실습 체크리스트

---

## ☑️ 실습 인증

---

## 🔥 미션

---

1. 
2. 

# 💪 미션 기록

---

<aside>
🍀 미션 기록의 경우, 아래 미션 기록 토글 속에 작성하시거나, 페이지를 새로 생성하여 해당 페이지에 기록하여도 좋습니다!

하지만, 결과물만 올리는 것이 아닌, **중간 과정 모두 기록하셔야 한다는 점!** 잊지 말아주세요.

</aside>

- **미션 기록**

## ⚡ 트러블 슈팅

---

<aside>
💡 실습하면서 생긴 문제들에 대해서, **이슈 - 문제 - 해결** 순서로 작성해주세요.

</aside>

<aside>
💡 스스로 해결하기 어렵다면? 스터디원들에게 도움을 요청하거나 **너디너리의 지식IN 채널에 질문**해보세요!

</aside>

- ⚡이슈 작성 예시 (이슈가 생기면 아래를 복사해서 No.1, No.2, No3 … 으로 작성해서 트러블 슈팅을 꼭 해보세요!)
    
    **`이슈`**
    
    👉 앱 실행 중에 노래 다음 버튼을 누르니까 앱이 종료되었다.
    
    **`문제`**
    
    👉 노래클래스의 데이터리스트의 Size를 넘어서 NullPointException이 발생하여 앱이 종료된 것이었다. 
    
    **`해결`**
    
    👉  노래 다음 버튼을 눌렀을 때 데이터리스트의 Size를 검사해 Size보다 넘어가려고 하면 다음으로 넘어가는 메서드를 실행시키지 않고, 첫 노래로 돌아가게끔 해결
    
    **`참고레퍼런스`**
    
    - 링크
- ⚡이슈 No.1
    
    **`이슈`**
    
    👉 [트러블이 생긴 상태 작성]
    
    **`문제`**
    
    👉 [어떤 이유로 해당 이슈가 일어났는지 작성]
    
    **`해결`**
    
    👉  [해결 방법 작성]
    
    **`참고레퍼런스`**
    
    - [문제 해결 시 참고한 링크]

## 🤔 참고 자료

---

Copyright © 2024 신수정(베뉴) All rights reserved.