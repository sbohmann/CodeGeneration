package codeGeneration;

import codeGeneration.java.JavaGenerator;
import codeGeneration.thymeleaf.ThymeleafGenerator;
import codeGeneration.velocity.VelocityGenerator;

import java.util.List;

class MainClass
{
    public static void main(String[] args) throws Exception
    {
        ClassDefinition classDefinition = createClassDefinition();

        new ThymeleafGenerator(classDefinition).run();
        new VelocityGenerator(classDefinition).run();
        new JavaGenerator(classDefinition).run();
    }

    private static ClassDefinition createClassDefinition()
    {
        return new ClassDefinition(
            "at.yeoman.example",
            "ExampleClass",
            List.of(
                new Field("int", "size"),
                new Field("String", "name")));
    }
}
