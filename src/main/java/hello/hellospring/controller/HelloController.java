package hello.hellospring.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
