// thymeleaf

package [(${packageName})];

public class [# th:utext='${className}' /]() {
[# th:each="field : ${fields}"]
    [(${field.type})] [(${field.name})];
[/]
}
