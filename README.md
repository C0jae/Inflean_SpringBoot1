# 스프링 입분 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근기술
## 기록
### View 환경설정 - 22.04.06.

``` java
@Controller
public class HelloController {

    @GetMapping("hello")    // 웹 어플리케이션에서 '/hello'로 들어오게 되면 해당 매서드를 호출한다.
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        // key : data / value : hello!!

        return "hello"; // resources 안쪽에 있는 hello 파일로 넘어가서 해당 데이터를 넘기기
    }
```
* 컨트롤러에서 리턴값으로 문자를 반환하면, 'viewREsolver'가 화면을 찾아서 처리한다.
  * 'resources:templates/' + {ViewName} + '.html'

***

### 정적 컨텐츠 - 22.04.06.
* 서버에서 데이터 전달 등의 과정없이 html파일 그대로 내려주는 것
``` html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    정적 컨텐츠 입니다.
</body>
</html>
```
* /'파일이름'.html 주소를 통해 접근을 하게되면 톰캣서버를 통해 스프링 컨테이너에서 우선적으로 관련 컨트롤러 등을 찾는다.
* 관련된 컨트롤러가 없다면 톰캣서버는 resources: static폴더 해당 파일이 있는지 찾는다.
* 이후 해당 파일을 발견한다면 html파일 그대로 나타낸다.(데이더 전달 등의 과정 X)

***

### MVC와 템플릿 엔진 - 22.04.06.
* MVC : Model, View, Controller의 조합
* 컨트롤러에서 찾게된 데이터의 값을 html 화면을 출력할때 해당 데이터를 변환하여 출력한다.(정적과 다른점)
``` java
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        // @ReqestParam(value = "name", required = true)
        model.addAttribute("name", name);

        return "hello-template";
    }
```
* addAttribute로 넘겨준 name의 값을 html에서 찾아( ${name} ) 변환하여 화면에 출력해준다.
* @RequestParam 을 통해 name의 값을 지정해 넘겨주어야 한다.
  * ex) localhost:8080/hello-template?name=spring
  * hello-template.html에 있는 ${name}을 찾아 spring으로 변환하여 화면에 출력한다.
``` html
<html xmlns:th="http://www.thymeleaf.org">
<body>
<p th:text="'hello ' + ${name}">hello! empty</p>
</body>
```
* 화면출력 : hello spring
  * 참고 : 컨트롤러를 거치지 않고 hello-template.html의 주소 그대로 접속하게되면 hello! empty가 출력된다.(thymeleaf 기능)
***
### API - 22.04.07.
``` java
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello" + name;
    }
```
* @RequestBody : html이 아닌 http body부분에 데이터를 직접 넣어준다.
    * html파일을 거치지 않는다.(return 부분 그대로 출력) => view를 거치지 않는다.
* 화면 출력 : localhost:8080/hello-String?name=spring => hello spring   

Json방식 출력
``` java
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);

        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
```
* hello-string의 경우 데이터 그대로 리턴하여 화면에 출력해주면 되지만 해당 메소드의 경우는 객체가 존재하기 때문에 Json타입으로 변환하여 http에 반환하게 된다.
* 화면 출력 : localhost:8080/hello-String?name=spring => {"name" : "spring"}   

@ResponseBody 원리
* http의 body에 문자 내용을 직접 반환
* 'viewResolver' 대신에 'HttpMessageConverter'가 동작
* 기본 문자처리 : 'StringHttpMessageConverter'
* 기본 객체처리 : 'MappingJackson2HttpMessageConverter'

***

### 의존성 주입(Dependency Injection, DI) - 22.04.15.
``` java
public class MemberService {
    private final MemberRepository memberRepository;

    // DI(Dependency Injection : 의존성 주입(MemberRepository를 외부에서 주입한다.)
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
```
* DI를 통해 서로 강하게 결합되어 있는 두 클래스를 분리하고 두 객체간의 관계를 정리해줌으로써 결합도를 낮추고 유연성을 확보하게 된다.

***

### 컴포넌트 스캔과 자동 의존관계 설정 - 22.04.15.
``` java
@Controller
public class MemberController {

    private final MemberService memberService;

    // 생성자
    @Autowired // @Autowired : 만들어진 생성자를 스프링 컨테이너와 연결을 시켜준다.
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
```
* new를 통해 매번 새로운 객체를 만드는 것이 아닌 생성자 생성과 @Autowired를 통해 스프링 컨테이너와 연결을 시켜준다. -> 스프링빈 등록
* 참고 : 스프링은 스프링 컨테이너에 스프링 빈을 등록할 때, 기본으로 싱글톤으로 등록한다.(유일하게 하나만 등록해서 공유)
* 즉, 같은 스프링 빈이면 모두 같은 인스턴스이다.
* 설정으로 싱글톤이 아니게 설정할 수 있지만, 특별한 경우를 제외하면 대부분 싱글톤을 사용한다.
``` java
package hello.hellospring.service;

@Service
public class MemberService { 
}
```
* @Service : 스프링이 해당 서비스클래스를 인식하여 스프링 컨테이너에 연결시켜준다.(DI, 의존관계 주입)
* 레퍼지토리 클래스의 경우도 @Repository 어노테이션을 이용하여 연결(@~ : 컴포넌트 스캔방식)

구조
* Controller : 외부에서 요청(데이터) 받기
* Service : 비즈니스 로직 만들기
* Repository : 데이터 저장

***

### 
