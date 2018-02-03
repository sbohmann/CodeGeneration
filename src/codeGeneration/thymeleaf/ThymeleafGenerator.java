package codeGeneration.thymeleaf;

import codeGeneration.ClassDefinition;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.TemplateSpec;
import org.thymeleaf.context.Context;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.templatemode.TemplateMode;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ThymeleafGenerator
{
    private ClassDefinition classDefinition;

    private TemplateEngine engine;
    private Context context;

    public ThymeleafGenerator(ClassDefinition classDefinition)
    {
        this.classDefinition = classDefinition;
    }

    public void run() throws IOException
    {
        createEngine();
        createContext();

        processTemplate();
    }

    private void createEngine()
    {
        StandardDialect dialect = new StandardDialect();
        engine = new TemplateEngine();
        engine.setDialect(dialect);
    }

    private void createContext()
    {
        context = new Context();
        setVariables();
    }

    private void setVariables()
    {
        context.setVariable("class", classDefinition);
    }

    private void processTemplate() throws IOException
    {
        String templateText = loadTemplate();
        TemplateSpec spec = new TemplateSpec(templateText, TemplateMode.JAVASCRIPT);
        String result = engine.process(spec, context);
        System.out.println(result);
    }

    private String loadTemplate() throws IOException
    {
        InputStream stream = new FileInputStream("templates/thymeleaf/struct2.java");
        byte[] rawString = stream.readAllBytes();
        return new String(rawString, StandardCharsets.UTF_8);
    }
}
