// thymeleaf

package [(${class.packageName})];

public class [# th:utext='${class.className}' /]() {
[# th:each="field : ${class.fields}"]
    [(${field.type})] [(${field.name})];
[/]
}
