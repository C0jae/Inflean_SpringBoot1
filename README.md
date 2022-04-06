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
