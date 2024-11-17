# Chapter 7. API 응답 통일 & 에러 핸들러 (1)

# 📝 학습 목표

---

1. Springboot에서 API 응답의 통일을 적용한다.
2. Springboot에서 에러 핸들러를 구축한다.

# 📸 잠깐 ! 스터디 인증샷은 찍으셨나요?📸

---

* 스터디 리더께서 대표로 매 주차마다 한 장 남겨주시면 좋겠습니다!🙆💗
 (사진을 저장해서 이미지 임베드를 하셔도 좋고, 복사+붙여넣기 해서 넣어주셔도 좋습니다!)

[https://www.notion.so](https://www.notion.so)

# 📑 7주차 주제

---

이번 주차는 데이터 쪽이 아닌, 프로젝트를 위한 여러가지 설정을 해봅시다. 

우선, API 응답의 통일이 필요하고

다음으로 에러 핸들링(서버 터진것도 있지만, 클라이언트가 잘못 한 경우도 포함)을 합니다.

# 🔖 7주차 본문

---

API 응답 통일은 왜 필요할까요?

서버 측에서도 통일된 양식이 있으면 코딩이 쉽지만,
프론트엔드 개발자 입장에서는 통일이 되어있지 않으면 매우 화가 납니다.

무엇보다 가장 화나는 상황은 아래와 같습니다.

🤬 ***하나의 API의 응답이 성공인 경우와 실패인 경우 양식이 다름*** 🤬

보통 API 자체도 함수처럼 호출해서 하나의 변수에 그 응답을 받는데

그 응답의 형태가 제각각이면 매우 어지럽겠죠?

따라서 API의 응답을 통일 하는 것은 프로젝트 진행에 있어 매우 필요한 작업입니다.

API 응답의 경우 프로젝트마다 다르지만 보통 아래의 형태를 가집니다.

```json
{
	"isSuccess" : Boolean 타입
	"code" : String
	"message" : String
	"result" : {응답으로 필요한 또 다른 json}
}
```

- **code**: HTTP 상태 코드 외에 더 세부적인 결과를 알려주기 위해 사용
- **message**: code에 추가적으로 어떤 결과인지 알려주기 위해 사용
- **result:** 응답에 필요한 json (DTO와 같은 것)

## API 응답 통일

이제 API 응답 통일을 해봅시다.

응답의 경우 **enum**으로 그 형태를 관리합니다.

이 때, 성공 응답과 실패 응답을 하나의 enum으로 관리할 수도 있고, 분리할 수도 있습니다.

저는 분리해서 관리하는 것을 보여드릴 것입니다.

패키지 이름 정하는 것부터 매우 화가 나는데, 저는 그냥 apiPayload로 할게요.
API의 응답 정보를 정한다! 이런 이름으로 지었습니다.

base 아래에 API 응답 통일을 위한 ApiResponse 클래스를 만들어주세요.

![~~이름 짓기 더럽게 힘드네~~](Chapter%207%20API%20%E1%84%8B%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A1%E1%86%B8%20%E1%84%90%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%AF%20&%20%E1%84%8B%E1%85%A6%E1%84%85%E1%85%A5%20%E1%84%92%E1%85%A2%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A5%20(1)%20c4134760be1e4c669caadbb805ca7863/Untitled.png)

~~이름 짓기 더럽게 힘드네~~

다음으로 code라는 패키지를 만들어주세요.

그렇게 한다면, 아래와 같은 디렉토리 구조를 가지게 됩니다.

![Untitled](Chapter%207%20API%20%E1%84%8B%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A1%E1%86%B8%20%E1%84%90%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%AF%20&%20%E1%84%8B%E1%85%A6%E1%84%85%E1%85%A5%20%E1%84%92%E1%85%A2%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A5%20(1)%20c4134760be1e4c669caadbb805ca7863/Untitled%201.png)

🤔 ***code가 뭐지….? 이후 설명을 보면 이해가 될 테니 차근차근 따라와 주세요!***

이제 다음으로 ApiResponse를 만들어봅시다.

```java
@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

		@JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 성공한 경우 응답 생성

//    public static <T> ApiResponse<T> onSuccess(T result){
//        return new ApiResponse<>(true, SuccessStatus._OK.getCode() , SuccessStatus._OK.getMessage(), result);
//    }
//
//    public static <T> ApiResponse<T> of(BaseCode code, T result){
	//        return new ApiResponse<>(true, code.getReasonHttpStatus().getCode() , code.getReasonHttpStatus().getMessage(), result);
//    }

    // 실패한 경우 응답 생성
    public static <T> ApiResponse<T> onFailure(String code, String message, T data){
        return new ApiResponse<>(false, code, message, data);
    }
}
```

이렇게 통일된 API 응답을 위한 class를 만들어줍니다.

result는 어떤 형태의 값이 올지 모르기에 **Generic**으로 만들어 줍니다.

보면 함수가 2개가 있죠.

***onSuccess, onFailure***

어떻게 쓰는지는 뒤에 나옵니다.
그리고 onSuccess의 경우, 주석 처리가 되어있습니다.

응답 내부에 들어갈 code를 아직 만들지 않아서 그렇다는 점 말씀드려요!

우선 api 응답 형태에 대해 알아봅시다.
아래와 같은 형태의 응답을 줄 예정입니다.

```java
{
	"isSuccess ": true,
	"code" : "2000",
	"message" : "OK",
	"result" : 
		{
			"testString" : "This is test!"
		}
}
```

<aside>
🌟 ***isSuccess*** : **성공인지 아닌지 알려주는 필드**입니다.

***code*** : HTTP 상태코드로는 너무 제한적인 정보만 줄 수 있어서 **조금 더 세부적인 응답 상황을 알려주기 위한 필드**입니다.

***message*** : **code에 추가적으로 우리에게 익숙한 문자로 상황을 알려주는 필드**입니다.

***result*** : **실제로 클라이언트에게 필요한 데이터**가 담깁니다. 보통 에러 상황에는 null을 담지만, 아닌 경우도 있습니다.

</aside>

### 🪄 HTTP 상태 코드?

HTTP 상태 코드는 여러가지가 있지만 200번 대, 400번 대, 500번 대만 알아봅시다.
아래에 작성한 상태 코드 정도만 알아도 충분합니다만, 궁금하시면 더 찾아보세요!

1. **200번 대** : 문제 없음
    1. **200** : OK 성공임
    2. **201** : Created: 네가 준 데이터를 가지고 적절한 과정을 거쳐 새로운 리소스를 만들었어
2. **400번 대** : 클라이언트 측 잘못으로 인한 에러
    1. **400** : Bad Request : 요청 이상하게 함, 필요한 정보 누락됨..
    2. **401** : Unauthorized : 인증이 안됨 (로그인이 되어야 하는데 안된 상황)
    3. **403** : Forbidden : 권한 없음 (로그인은 되었으나 접근이 안됨, 관리자 페이지 등등)
    4. **404** : NotFound :  요청한 정보가 그냥 없음
3. **500번 대** : 서버 측 잘못으로 인한 에러(~~안돼…….😱~~)
    1. **500** : Internal Server Error : 서버 터짐…….
    2. **504** : Gateway Timeout : 서버가 응답을 안 줌 (~~그냥 터진거긴 함..~~)

**사실 HTTP 상태 코드는 더욱 많지만 403인 상황에서 왜 권한이 없는지 더 자세히 알려주면
클라이언트 입장에서도 더 빠르게 문제를 찾아낼 수 있겠죠!**

**그래서 API 응답에 자체적인 우리만의 code와 message를 도입하는 것입니다.**

이제 API 응답에 들어갈 code와 message의 형식을 만들어 봅시다!

![Untitled](Chapter%207%20API%20%E1%84%8B%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A1%E1%86%B8%20%E1%84%90%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%AF%20&%20%E1%84%8B%E1%85%A6%E1%84%85%E1%85%A5%20%E1%84%92%E1%85%A2%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A5%20(1)%20c4134760be1e4c669caadbb805ca7863/Untitled%202.png)

위 사진처럼 파일을 만들어주세용

그 다음 응답을 해주는 **ErrorReasonDTO**와 **ReasonDTO**의 코드를 적어줍니다.

```java
@Getter
@Builder
public class ErrorReasonDTO {

    private HttpStatus httpStatus;

    private final boolean isSuccess;
    private final String code;
    private final String message;

    public boolean getIsSuccess(){return isSuccess;}
}
```

```java
@Getter
@Builder
public class ReasonDTO {

    private HttpStatus httpStatus;

    private final boolean isSuccess;
    private final String code;
    private final String message;

    public boolean getIsSuccess(){return isSuccess;}
}
```

그 다음 **BaseCode**와 **BaseErrorCode**을 적어주는 데 둘의 구체적인 역할은

**구체화 하는 Status에서 두 개의 메소드를 반드시 Override할 것을 강제하는 역할을 합니다!**

```java
public interface BaseCode {

    Reason getReason();

    Reason getReasonHttpStatus();
}
```

```java
public interface BaseErrorCode {

    ErrorReason getReason();

    ErrorReason getReasonHttpStatus();
}
```

위의 것들을 구현해보았으니 이제 성공 응답을 나타내는 **SuccessStatus**를 써보겠습니다.

```java
@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    // 일반적인 응답
    _OK(HttpStatus.OK, "COMMON200", "성공입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
```

보면, enum 자신의 값으로 가지고 있던 message, code, httpStatus값을

interface의 메소드 오버라이딩을 통하여 DTO를 만드는 것을 확인할 수 있습니다.

(현재는  _OK 만 있는 데 성공 응답을 추가하고 싶으면 ENUM 형식으로 계속 밑에 추가하면 됩니다!)

우선 위의 code를 어떻게 적용해야하는 감을 익혀야겠죠??

### 임시 API 만들어보기

이제 한번 간단하게 API 하나를 만들어 봅시다.

임시 API는 아래와 같습니다.

<aside>
🤝 **GET /temp/test**

- query String : X
- request body: X
- request Header: X
- **response**
    
    ```json
    {
    	"isSuccess ": true,
    	"code" : "2000",
    	"message" : "OK",
    	"result" : 
    		{
    			"testString" : "This is test!"
    		}
    }
    ```
    
</aside>

1. **먼저 DTO를 만들어 줍니다.**
아래와 같이 클래스를 만들어 주세요.
    
    ![Untitled](Chapter%207%20API%20%E1%84%8B%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A1%E1%86%B8%20%E1%84%90%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%AF%20&%20%E1%84%8B%E1%85%A6%E1%84%85%E1%85%A5%20%E1%84%92%E1%85%A2%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A5%20(1)%20c4134760be1e4c669caadbb805ca7863/Untitled%203.png)
    
    RequestBody에 담겨오는 값은 없으므로, TempResponse만 작성하도록 하겠습니다.
    
    ```java
    public class TempResponse {
    
        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class TempTestDTO{
            String testString;
        }
    }
    ```
    

### 그런데… public static class가 뭐지? 🤔

DTO들은 저렇게 큰 묶음으로 (멤버 관련 DTO 등등…) 클래스를 만들고,
내부적으로 static 클래스를 만드는 것이 좋습니다.

DTO 자체는 수많은 곳에서 사용이 될 수 있기에 static class로 만들게 되면, 매번 class 파일을 만들 필요도 없고, 범용적으로 DTO를 사용 할 수 있습니다.

### DTO에도 빌더 패턴을 쓴다! 🤓

그냥 우리가 만드는 인스턴스들은 모두 빌더 패턴을 사용한다고 생각하시면 됩니다!

참고로, RequestDTO는 우리가 만드는 것이 아닌, 프론트엔드에서 만든 객체를 그저 받기에 RequestDTO는 빌더 패턴을 적용할 필요가 없습니다.

우린 그저 받기만 하면 되니까요.

1. 다음으로 **converter를 만들어 줍시다.**
    
    컨버터 패키지에 TempConverter 클래스를 만들어 줍니다. (저희 메소드 형식은 to[생성되는 것] 이렇게 진행하겠습니다!)
    
    ![Untitled](Chapter%207%20API%20%E1%84%8B%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A1%E1%86%B8%20%E1%84%90%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%AF%20&%20%E1%84%8B%E1%85%A6%E1%84%85%E1%85%A5%20%E1%84%92%E1%85%A2%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A5%20(1)%20c4134760be1e4c669caadbb805ca7863/Untitled%204.png)
    
    ```java
    public class TempConverter {
    
        public static TempResponse.TempTestDTO toTempTestDTO(){
            return TempResponse.TempTestDTO.builder()
                    .testString("This is Test!")
                    .build();
        }
    }
    ```
    
2. 이제 **Controller를 완성**해줍시다!
    
    딱히 비즈니스 로직이 들어가거나 Database와 상호작용을 하는 것이 아니기에 Service, Repository는 작성하지 않을게요.
    
    **🤚 컨트롤러를 작성하기 전에!**
    
    ApiResponse를 보면 onSuccess 함수가 주석 처리가 되어있습니다.
    
    이를 주석 해제 해줍시다!
    
    ![Untitled](Chapter%207%20API%20%E1%84%8B%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A1%E1%86%B8%20%E1%84%90%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%AF%20&%20%E1%84%8B%E1%85%A6%E1%84%85%E1%85%A5%20%E1%84%92%E1%85%A2%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A5%20(1)%20c4134760be1e4c669caadbb805ca7863/Untitled%205.png)
    
    아래와 같이 컨트롤러를 작성합니다.
    
    ```java
    @RestController
    @RequestMapping("/temp")
    @RequiredArgsConstructor
    public class TempRestController {
    
        @GetMapping("/test")
        public ApiResponse<TempResponse.TempTestDTO> testAPI(){
    
            return ApiResponse.onSuccess(TempConverter.toTempTestDTO());
        }
    }
    ```
    
      
    
3. API가 동작하는 것을 확인합니다.
    
    ![Untitled](Chapter%207%20API%20%E1%84%8B%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A1%E1%86%B8%20%E1%84%90%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%AF%20&%20%E1%84%8B%E1%85%A6%E1%84%85%E1%85%A5%20%E1%84%92%E1%85%A2%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A5%20(1)%20c4134760be1e4c669caadbb805ca7863/Untitled%206.png)
    
    지금은 로컬호스트지만,
    
    만약 원격 서버에 nginx와 같이 Springboot 인프라를 구축했다면,
    
    <aside>
    🌟 **먼저 nginx에 도달 후, 내부적인 리버스 프록시를 통해 Springboot로 요청이 간다는 것**
    
    </aside>
    

## 에러 핸들러

에러 핸들러는 API 응답 통일에 비해 훨씬 난이도가 높지만…

한번 잘 만들어 두면 매우 편해요!

### 에러 핸들러 만들기

우선 에러 핸들러를 만들어 봅시다.

![Untitled](Chapter%207%20API%20%E1%84%8B%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A1%E1%86%B8%20%E1%84%90%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%AF%20&%20%E1%84%8B%E1%85%A6%E1%84%85%E1%85%A5%20%E1%84%92%E1%85%A2%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A5%20(1)%20c4134760be1e4c669caadbb805ca7863/Untitled%207.png)

저 같은 경우는 저렇게 base 패키지 안에 exception 패키지를 만들었는데, exception 패키지를 밖으로 빼도 됩니다.

🤔 **handler 패키지는 뭐고 ExceptionAdvice는 뭐지?**
→ 내용 쭉 따라가다 보면 아 이거구나! 하고 이해가 될 겁니다.

이제 아까 만들었던 **ErrorStatus**를 만들고 사용해볼 시간입니다!

하지만 사용하기 전에,

### 🔎 ***Enum 관리에 대한 고찰*** 🔎

Code를 아무 생각 없이 ~~개판으로~~ 관리를 할 수도 있지만,
Code도 **규칙을 정해두면** 프론트엔드 개발자와 소통이 용이해집니다. 👍

무엇보다, **Springboot 자체가 대규모 프로젝트를 위한 프레임워크임을 고려**한다면,
Code 자체도 체계적으로 관리하는 것이 대규모 프로젝트에서 변경에 대해 빠르게 대처할 수 있을 것입니다.

이는 Springboot의 목적에도 부합하겠죠?

우선 Error Code 하나만 봐봅시다.

```java
_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON000", "서버 에러, 관리자에게 문의 바랍니다."),
```

보면, ***HttpStatus.INTERNAL_SERVER_ERROR*** 라고 작성되어있습니다.

이것 자체로 프론트엔드 개발자가 어떤 상황인지는 **대략적으로 파악이 가능**합니다.
HTTP 프로토콜의 Status 자체로도 의미가 있으니까요.

그러나 저렇게 대략적인 상황만 파악이 가능하면 좋지 않습니다.

예를 들어 응답의 HTTP Status가 400번대라고 합시다. 그러면 아래의 생각이 들 거에요.

<aside>
😱 ***대충 내가 잘못한 건 알겠는데…. 정확히 어떤 잘못이지???***

</aside>

프론트엔드 개발자는 저렇게 생각할 것이고, 정확히 무엇 때문에 API 응답이 저런 건지 모를 거에요.

그렇다면, 매번 백엔드 개발자에게 **직접** 물어봐야겠죠? 이건 서로에게 너무나도 번거로운 일이니 좋지 않습니다.

따라서, 우리는 두 번째 인자로 자체적인 String으로 code를 제공함으로써 프론트엔드 개발자가 더 정확히 API 응답의 원인을 알 수 있도록 합니다.

그렇다면, 아래처럼 하는 것이 좋을까요?

```java
"4002" -> 회원 정보 누락
"4003" -> JWT 토큰 안줌
"4004" -> 회원가입 닉네임 누락
"4005" -> 게시글 없음
.....
```

우선 4000번대를 쓰는 이유는 **400번대 에러임**을 나타내는 것입니다.

만약 400번대를 사용한다면 400~499까지 99개의 에러 코드만 만들 수 있어,
이는 너무 제한적이기에 4000번대를 쓰는 것입니다. 

프로젝트 규모가 커질 수도 있기에 400~499 이렇게 너무 적은 경우만 가능하도록 설계를 하는 것은 매우 좋지 않습니다!

아무튼….. 위의 경우를 보면 어떠세요.

그냥 일관성이 없죠?? 개판입니다.

4002는 회원 관련이었다가, 갑자기 4003은 토큰 관련이고 아주 난리에요.

이러면 프론트엔드 개발자 입장에서 헷갈리고 ~~개빡침~~ 짜증납니다.

따라서 이러한 에러에 대한 code도 체계적인 규칙을 두는 것이 좋습니다.

제가 여러분들께 추천을 드리는 것은 아래와 같은 규칙입니다.

```java
1. common 에러는 **COMMON000** 으로 둔다. <- 잘 안쓰지만 마땅하지 않을 때 사용
2. **관련된 경우마다 code에 명시적으로 표현**한다.
	- 예를 들어 멤버 관련이면 **MEMBER001** 이런 식으로
3. 2번에 이어서 4000번대를 붙인다. 서버측 잘못은 그냥 COMMON 에러의 서버 에러를 쓰면 됨.
	- **MEMBER400_1 아니면 MEMBER4001** 이런 식으로
```

실제로 멤버 관련 에러에 대한 핸들링을 하는 것은 8주차에서 보여드리겠지만,

위와 같은 규칙으로 Code enum을 관리하는 것이 **확장성 측면에서도 매우 좋습니다.**

이제 위의 규칙을 적용해서 예시로 Code enum을 몇개만 더 보여드릴 께요.

```java
// Member Error
MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),
NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임은 필수 입니다."),

// Article Error
ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4001", "게시글이 없습니다.");
```

이런 느낌으로 여러분들이 프로젝트 하다가 생기는 예외 상황에 대해 추가를 하면 됩니다.

1. **ErrorStatus 작성하기**
    
    아까 처음에 보았던 SuccessStatus와 사용법, 원리는 같습니다! (방금 보여드렸던 예시도 추가해서 작성하였습니다!)
    
    ```java
    @Getter
    @AllArgsConstructor
    public enum ErrorStatus implements BaseErrorCode {
    
        // 가장 일반적인 응답
        _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
        _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
        _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
        _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    
        // 멤버 관려 에러
        MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),
        NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임은 필수 입니다."),
    
        // 예시,,,
        ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4001", "게시글이 없습니다.");
    
        private final HttpStatus httpStatus;
        private final String code;
        private final String message;
    
        @Override
        public ErrorReasonDTO getReason() {
            return ErrorReasonDTO.builder()
                    .message(message)
                    .code(code)
                    .isSuccess(false)
                    .build();
        }
    
        @Override
        public ErrorReasonDTO getReasonHttpStatus() {
            return ErrorReasonDTO.builder()
                    .message(message)
                    .code(code)
                    .isSuccess(false)
                    .httpStatus(httpStatus)
                    .build()
                    ;
        }
    }
    ```
    

1. **GeneralException 추가**하기
    
    ```java
    @Getter
    @AllArgsConstructor
    public class GeneralException extends RuntimeException {
    
        private BaseErrorCode code;
    
        public ErrorReasonDTO getErrorReason() {
            return this.code.getReason();
        }
    
        public ErrorReasonDTO getErrorReasonHttpStatus(){
            return this.code.getReasonHttpStatus();
        }
    }
    ```
    
    일단 저 코드를 보고 뭔 소리지? 하는 생각이 들겠지만,
    에러 핸들러와 status enum과 함께 계속 여러 번 보면 익숙해 질 것입니다. 
    
2. **에러 핸들러 만들기**
    
    우선 에러 핸들러를 만들기 전에 spring에서 제공해주는 validation과 관련된 기능을 사용하기 위한 의존성 추가가 필요합니다.
    
    ![Untitled](Chapter%207%20API%20%E1%84%8B%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A1%E1%86%B8%20%E1%84%90%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%AF%20&%20%E1%84%8B%E1%85%A6%E1%84%85%E1%85%A5%20%E1%84%92%E1%85%A2%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A5%20(1)%20c4134760be1e4c669caadbb805ca7863/Untitled%208.png)
    
    위의 의존성을 build.gradle에 추가 해주세요 (하고 우측 상단에 코끼리 표시🐘 눌러주세요)
    
    이제 **ExceptionAdvice**를 작성해봅시다.
    
    ```java
    @Slf4j
    @RestControllerAdvice(annotations = {RestController.class})
    public class ExceptionAdvice extends ResponseEntityExceptionHandler {
    
        @ExceptionHandler
        public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
            String errorMessage = e.getConstraintViolations().stream()
                    .map(constraintViolation -> constraintViolation.getMessage())
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("ConstraintViolationException 추출 도중 에러 발생"));
    
            return handleExceptionInternalConstraint(e, ErrorStatus.valueOf(errorMessage), HttpHeaders.EMPTY,request);
        }
    
        @Override
        public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    
            Map<String, String> errors = new LinkedHashMap<>();
    
            e.getBindingResult().getFieldErrors().stream()
                    .forEach(fieldError -> {
                        String fieldName = fieldError.getField();
                        String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
                        errors.merge(fieldName, errorMessage, (existingErrorMessage, newErrorMessage) -> existingErrorMessage + ", " + newErrorMessage);
                    });
    
            return handleExceptionInternalArgs(e,HttpHeaders.EMPTY,ErrorStatus.valueOf("_BAD_REQUEST"),request,errors);
        }
    
        @ExceptionHandler
        public ResponseEntity<Object> exception(Exception e, WebRequest request) {
            e.printStackTrace();
    
            return handleExceptionInternalFalse(e, ErrorStatus._INTERNAL_SERVER_ERROR, HttpHeaders.EMPTY, ErrorStatus._INTERNAL_SERVER_ERROR.getHttpStatus(),request, e.getMessage());
        }
    
        @ExceptionHandler(value = GeneralException.class)
        public ResponseEntity onThrowException(GeneralException generalException, HttpServletRequest request) {
            ErrorReasonDTO errorReasonHttpStatus = generalException.getErrorReasonHttpStatus();
            return handleExceptionInternal(generalException,errorReasonHttpStatus,null,request);
        }
    
        private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorReasonDTO reason,
                                                               HttpHeaders headers, HttpServletRequest request) {
    
            ApiResponse<Object> body = ApiResponse.onFailure(reason.getCode(),reason.getMessage(),null);
    //        e.printStackTrace();
    
            WebRequest webRequest = new ServletWebRequest(request);
            return super.handleExceptionInternal(
                    e,
                    body,
                    headers,
                    reason.getHttpStatus(),
                    webRequest
            );
        }
    
        private ResponseEntity<Object> handleExceptionInternalFalse(Exception e, ErrorStatus errorCommonStatus,
                                                                    HttpHeaders headers, HttpStatus status, WebRequest request, String errorPoint) {
            ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(),errorCommonStatus.getMessage(),errorPoint);
            return super.handleExceptionInternal(
                    e,
                    body,
                    headers,
                    status,
                    request
            );
        }
    
        private ResponseEntity<Object> handleExceptionInternalArgs(Exception e, HttpHeaders headers, ErrorStatus errorCommonStatus,
                                                                   WebRequest request, Map<String, String> errorArgs) {
            ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(),errorCommonStatus.getMessage(),errorArgs);
            return super.handleExceptionInternal(
                    e,
                    body,
                    headers,
                    errorCommonStatus.getHttpStatus(),
                    request
            );
        }
    
        private ResponseEntity<Object> handleExceptionInternalConstraint(Exception e, ErrorStatus errorCommonStatus,
                                                                         HttpHeaders headers, WebRequest request) {
            ApiResponse<Object> body = ApiResponse.onFailure(errorCommonStatus.getCode(), errorCommonStatus.getMessage(), null);
            return super.handleExceptionInternal(
                    e,
                    body,
                    headers,
                    errorCommonStatus.getHttpStatus(),
                    request
            );
        }
    }
    ```
    
    ⚠️위의 코드를 복붙하면 빨간 줄이 많을 텐데, 우선 `ConstraintViolationException` 이 친구는 아래의 jakarta.validation에서 제공하는 것을 import 해줘야 합니다.
    
    ![스크린샷 2024-09-27 오후 3.04.46.png](Chapter%207%20API%20%E1%84%8B%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A1%E1%86%B8%20%E1%84%90%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%AF%20&%20%E1%84%8B%E1%85%A6%E1%84%85%E1%85%A5%20%E1%84%92%E1%85%A2%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A5%20(1)%20c4134760be1e4c669caadbb805ca7863/%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA_2024-09-27_%25E1%2584%258B%25E1%2585%25A9%25E1%2584%2592%25E1%2585%25AE_3.04.46.png)
    
    사실 **RestControllerAdvice를 이용한 예외 처리**는 실제로 사용을 해봐야,
    이게 왜 편한지 비로소 체감이 됩니다.
    
    위의 방식을 사용하지 않으면 예외가 발생 한 상황에서 코드가 매우 지저분해집니다.
    
    여러분들은 이번주 워크북을 읽고, RestControllerAdvice를 사용하면 왜 편한지 **자세히 조사**를 하는 것이 미션 중 하나입니다. >.0
    

그러면 이제, 임시로 예외(에러)처리를 하는 것을 보여드리겠습니다.

### Exception 핸들링 하기

이번 상황은 **GET /temp/exception**에 대하여

Query String에 flag를 받아오는데, 해당 flag가 2인 경우 exception을 만드는 경우입니다.

1. ErrorStatus에 **새로운 경우 추가**하기
    
    ```java
    // For test
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "이거는 테스트");
    ```
    

1. handler 패키지에 **Temp에 대한 핸들러 추가**하기
    
    ![Untitled](Chapter%207%20API%20%E1%84%8B%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A1%E1%86%B8%20%E1%84%90%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%AF%20&%20%E1%84%8B%E1%85%A6%E1%84%85%E1%85%A5%20%E1%84%92%E1%85%A2%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A5%20(1)%20c4134760be1e4c669caadbb805ca7863/Untitled%209.png)
    
    ```java
    public class TempHandler extends GeneralException {
    
        public TempHandler(BaseErrorCode errorCode) {
            super(errorCode);
        }
    }
    ```
    

1. **TempResponse DTO** **추가**하기
    
    ```java
    public class TempResponse {
    
        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class TempTestDTO{
            String testString;
        }
    
        @Builder
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class TempExceptionDTO{
            Integer flag;
        }
    }
    ```
    
2. **Converter** **작성**하기
    
    toTempTestDTO 이런 식으로!
    
    ```java
    public class TempConverter {
    
        public static TempResponse.TempTestDTO toTempTestDTO(){
            return TempResponse.TempTestDTO.builder()
                    .testString("This is Test!")
                    .build();
        }
        
        public static TempResponse.TempExceptionDTO toTempExceptionDTO(Integer flag){
            return TempResponse.TempExceptionDTO.builder()
                    .flag(flag)
                    .build();
        }
    }
    ```
    
3. **Controller 작성**하기
    
    우선 이번 경우는 Service를 사용 할 것이기에 Controller는 우선 return null만 해둡시다.
    
    Springboot의 **RestController에서 queryString을 받아오는 방법**은 **@RequestParam** 입니다!
    
    ```java
    @RestController
    @RequestMapping("/temp")
    @RequiredArgsConstructor
    public class TempRestController {
    
        @GetMapping("/test")
        public ApiResponse<TempResponse.TempTestDTO> testAPI(){
    
            return ApiResponse.onSuccess(TempConverter.toTempTestDTO());
        }
    
        @GetMapping("/exception")
        public ApiResponse<TempResponse.TempExceptionDTO> exceptionAPI(@RequestParam Integer flag){
    
            return null;
        }
    }
    ```
    
4. **Service 작성**하기
    
    Service를 작성 할 때, 아래와 같은 규칙을 따릅니다.
    
    ```java
    1. GET 요청과 나머지 요청에 대해 아래와 같이 분리한다.
    
    	a. GET 요청에 대한 비즈니스 로직을 처리할 경우
    			TempQueryService 이렇게 만든다.
    	b. 나머지 요청에 대한 비즈니스 로직을 처리할 경우
    			TempCommandService 이렇게 만든다.
    
    2. 서비스를 만들 경우 인터페이스를 먼저 두고 이를 구체화 한다.
    	 TempQueryService 인터페이스, TempCommandService 인터페이스를 만들고,
    	 이에 대한 Impl 구체화 클래스를 만든다.
    
    3. 컨트롤러는 인터페이스를 의존하며 실제 인터페이스에 대한 구체화 클래스는
    	 Springboot의 의존성 주입을 이용한다!
    ```
    
    무슨 소리인지 감이 안 오겠지만, 아래를 보면 대충 이해가 갈 것입니다.
    
    ![Untitled](Chapter%207%20API%20%E1%84%8B%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A1%E1%86%B8%20%E1%84%90%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%AF%20&%20%E1%84%8B%E1%85%A6%E1%84%85%E1%85%A5%20%E1%84%92%E1%85%A2%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A5%20(1)%20c4134760be1e4c669caadbb805ca7863/Untitled%2010.png)
    
    ```java
    public interface TempQueryService {
    
        void CheckFlag(Integer flag);
    }
    ```
    
    ```java
    @Service
    @RequiredArgsConstructor
    public class TempCommandQueryImpl implements TempQueryService{
    
        @Override
        public void CheckFlag(Integer flag) {
    
        }
    }
    ```
    
    이제 **TempQueryServiceImpl의 CheckFlag를 작성**해봅시다!
    
    ```java
    @Service
    @RequiredArgsConstructor
    public class TempQueryServiceImpl implements TempQueryService{
    
        @Override
        public void CheckFlag(Integer flag) {
            if (flag == 1)
                throw new TempHandler(ErrorStatus.TEMP_EXCEPTION);
        }
    }
    ```
    
5. Controller 완성하기
    
    ```java
    @RestController
    @RequestMapping("/temp")
    @RequiredArgsConstructor
    public class TempRestController {
    
        private final TempQueryService tempQueryService;
    
        @GetMapping("/test")
        public ResponseDto<TempResponse.TempTestDTO> testAPI(){
    
            return ResponseDto.onSuccess(TempConverter.toTempTestDTO(), Code.OK);
        }
    
    		@GetMapping("/exception")
        public ApiResponse<TempResponse.TempExceptionDTO> exceptionAPI(@RequestParam Integer flag){
            tempQueryService.CheckFlag(flag);
            return ApiResponse.onSuccess(TempConverter.toTempExceptionDTO(flag));
        }
    }
    ```
    
6. 테스트하기
    
    ![Untitled](Chapter%207%20API%20%E1%84%8B%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A1%E1%86%B8%20%E1%84%90%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%AF%20&%20%E1%84%8B%E1%85%A6%E1%84%85%E1%85%A5%20%E1%84%92%E1%85%A2%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A5%20(1)%20c4134760be1e4c669caadbb805ca7863/Untitled%2011.png)
    
    ![Untitled](Chapter%207%20API%20%E1%84%8B%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A1%E1%86%B8%20%E1%84%90%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%AF%20&%20%E1%84%8B%E1%85%A6%E1%84%85%E1%85%A5%20%E1%84%92%E1%85%A2%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A5%20(1)%20c4134760be1e4c669caadbb805ca7863/Untitled%2012.png)
    
    보면, 에러 상황과 에러가 아닌 상황마다 응답이 다른 것을 확인 할 수 있습니다.
    
    💫 신기하죠? 💫 
    
    Service에서는 단 하나만 했습니다.
    
    ```java
    @Override
        public void CheckFlag(Integer flag) {
            if (flag == 1)
                throw new TempHandler(ErrorStatus.TEMP_EXCEPTION);
        }
    ```
    
    네, **에러를 만들어서 던지기만 했지**, 응답을 만드는 코드는 없습니다.
    
    ***그러면 응답은 어디에 있을까요?***
    
    이는 **에러 핸들러 코드**를 잘 봐야 합니다.
    
    우선, **TempHandler**를 봅시다.
    
    ![Untitled](Chapter%207%20API%20%E1%84%8B%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A1%E1%86%B8%20%E1%84%90%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%AF%20&%20%E1%84%8B%E1%85%A6%E1%84%85%E1%85%A5%20%E1%84%92%E1%85%A2%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A5%20(1)%20c4134760be1e4c669caadbb805ca7863/Untitled%2013.png)
    
    Service에서 해당 핸들러를 만들면 GeneralException을 만듭니다.
    
    (super가 부모 클래스의 생성자를 호출하는 건 다 알죠? ^__^)
    
    ![Untitled](Chapter%207%20API%20%E1%84%8B%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A1%E1%86%B8%20%E1%84%90%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%AF%20&%20%E1%84%8B%E1%85%A6%E1%84%85%E1%85%A5%20%E1%84%92%E1%85%A2%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A5%20(1)%20c4134760be1e4c669caadbb805ca7863/Untitled%2014.png)
    
    그러면 GeneralException 클래스에서 **@AllArgsConstructor**를 통해
    
    만들어진 생성자가 호출이 됩니다!
    
    ***이 내용이 궁금하다면 lombok에 대해 찾아보세요!***
    
    GeneralException은 다시, RuntimeException을 상속 받았기에,
    이는 런타임에 발생한 Exception 으로써 MasterExceptionHandler가 감지하게 됩니다.
    
    왜냐?
    
    ![Untitled](Chapter%207%20API%20%E1%84%8B%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A1%E1%86%B8%20%E1%84%90%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%AF%20&%20%E1%84%8B%E1%85%A6%E1%84%85%E1%85%A5%20%E1%84%92%E1%85%A2%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A5%20(1)%20c4134760be1e4c669caadbb805ca7863/Untitled%2015.png)
    
    ExceptionAdvice가 @RestControllerAdvice를 가지고 있기 때문이죠.
    
    ![Untitled](Chapter%207%20API%20%E1%84%8B%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A1%E1%86%B8%20%E1%84%90%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%AF%20&%20%E1%84%8B%E1%85%A6%E1%84%85%E1%85%A5%20%E1%84%92%E1%85%A2%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A5%20(1)%20c4134760be1e4c669caadbb805ca7863/Untitled%2016.png)
    
    ExceptionAdvice의 위의 코드에서 GeneralException에 대해 다시 한번 오버로딩 된 함수를 호출하고 있습니다.
    
    ![Untitled](Chapter%207%20API%20%E1%84%8B%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A1%E1%86%B8%20%E1%84%90%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%AF%20&%20%E1%84%8B%E1%85%A6%E1%84%85%E1%85%A5%20%E1%84%92%E1%85%A2%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A5%20(1)%20c4134760be1e4c669caadbb805ca7863/Untitled%2017.png)
    
    최종적으로 다시 한번 호출된 함수에서는 상속 받은 부모 클래스의 생성자를 호출하고 있죠.
    
    ![Untitled](Chapter%207%20API%20%E1%84%8B%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A1%E1%86%B8%20%E1%84%90%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%AF%20&%20%E1%84%8B%E1%85%A6%E1%84%85%E1%85%A5%20%E1%84%92%E1%85%A2%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A5%20(1)%20c4134760be1e4c669caadbb805ca7863/Untitled%2018.png)
    
    그리고 코드를 까보면 여기서 **응답을 보내는 것을 확인 할 수 있습니다.**
    
    다시 한번 말하자면
    
    ![Untitled](Chapter%207%20API%20%E1%84%8B%E1%85%B3%E1%86%BC%E1%84%83%E1%85%A1%E1%86%B8%20%E1%84%90%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5%E1%86%AF%20&%20%E1%84%8B%E1%85%A6%E1%84%85%E1%85%A5%20%E1%84%92%E1%85%A2%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%85%E1%85%A5%20(1)%20c4134760be1e4c669caadbb805ca7863/Untitled%2019.png)
    
    저 코드에서 if 내부로 들어가게 되면
    
    **Service 이후 controller로 가지 않고, 바로 Exception handler에 의해 응답이 보내집니다.**
    

### RestControllerAdvice

**RestControllerAdvice는 @RestController가 붙은 대상에서 Exception이 발생하는 것을 감지하는 역할을 합니다.**

위의 코드들을 보면 Service의 메소드를 @RestController가 붙은 메소드에서 호출을 했기에, 결국 컨트롤러에서 Exception이 발생 한 것으로 판단이 되는 것이죠.

마찬가지로 Converter에서 Exception을 발생시켜도

결국 컨트롤러에서 Converter를 호출하니 Exception Handler에 잡힙니다.

# 🎯 핵심 키워드

---

<aside>
💡 주요 내용들에 대해 조사해보고, 자신만의 생각을 통해 정리해보세요!
레퍼런스를 참고하여 정의, 속성, 장단점 등을 적어주셔도 됩니다.
조사는 공식 홈페이지 **Best**, 블로그(최신 날짜) **Not Bad**

</aside>

<aside>
🤗 여러분들이 **직접** 중요하다고 생각하는 키워드를 조사해보세요!

</aside>

- RestContollerAdvice
    
    `@RestController`에서 발생한 예외를 전역적으로 처리하고, 처리된 예외를 JSON 형식으로 응답
    
- lombok
    
    **getter, setter, `toString()`, `equals()`, `hashCode()`** 등을 자동으로 생성해주는 기능을 제공
    

# 📢 학습 후기

---

- 이번 주차 워크북을 해결해보면서 어땠는지 회고해봅시다.
- 핵심 키워드에 대해 완벽하게 이해했는지? 혹시 이해가 안 되는 부분은 뭐였는지?

<aside>
💡

</aside>

# ⚠️ 스터디 진행 방법

---

1. 스터디를 진행하기 전, 워크북 내용들을 모두 채우고 스터디에서는 서로 모르는 내용들을 공유해주세요.
2. 미션은 워크북 내용들을 모두 완료하고 나서 스터디 전/후로 진행해보세요.
3. 다음주 스터디를 진행하기 전, 지난주 미션을 서로 공유해서 상호 피드백을 진행하시면 됩니다.

# 🔥 미션

---

### [UMC 서버 워크북 참고 자료](https://github.com/CYY1007/UMC_SERVER_WORKBOOK.git)

[GitHub - chock-cho/UMC-7th-spring-workbook at feature-week7-workbook](https://github.com/chock-cho/UMC-7th-spring-workbook/tree/feature-week7-workbook)

---

1. 위의 링크, 그리고 워크북을 보며 API 응답 통일과 에러 핸들러를 숙지하기.
2. **반드시** 본인 손으로 처음부터 끝까지 다 해보고 새 리포지토리 혹은 7주차 리포지토리에 새 브랜치에 push 후 해당 링크를 미션 기록지에 제출할 것.
3. 미션 진행 시 반드시 중간 중간 **과정 인증샷**을 남길 것.
4. ❗**필수**❗ ****RestControllerAdvice의 장점, 그리고 없을 경우 어떤 점이 불편한지도 조사하여 **미션 기록란**에 수록할 것.
5. ❗**필수**❗ **미션 목록 조회(진행중, 진행 완료) API 명세서** 작성하기 (이미 작성되어 있으면 상관 없음!)

# 💪 미션 기록

---

<aside>
🍀 미션 기록의 경우, 아래 미션 기록 토글 속에 작성하시거나, 페이지를 새로 생성하여 해당 페이지에 기록하여도 좋습니다!

하지만, 결과물만 올리는 것이 아닌, **중간 과정 모두 기록하셔야 한다는 점!** 잊지 말아주세요.

</aside>

- **미션 기록**

> **github 링크**
> 
> 
> 

# ⚡ 트러블 슈팅

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

---

Copyright © 2023 최용욱(똘이) All rights reserved.

Copyright © 2024 제이미(김준환) All rights reserved.