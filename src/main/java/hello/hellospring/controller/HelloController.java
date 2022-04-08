package hello.hellospring.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")    // 웹 어플리케이션에서 '/hello'로 들어오게 되면 해당 매서드를 호출한다.
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        // key : data / value : hello!!

        return "hello"; // resources 안쪽에 있는 hello 파일로 넘어가서 해당 데이터를 넘기기
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        // @ReqestParam(value = "name", required = true)
        model.addAttribute("name", name);

        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    // @ResponseBody : html이 아닌 http body부분에 데이터를 직접 넣어준다., html 파일을 거치지 않는다.(return 부분만 그대로 출력)
    // => json방식으로의 출력을 default로 정한다.
    public String helloString(@RequestParam("name") String name) { return "hello" + name; }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);

        return hello;
        // json방식 => 출력 : {"name" : name} = key : value
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
}
