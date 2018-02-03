package codeGeneration.velocity;

import codeGeneration.ClassDefinition;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;

public class VelocityGenerator
{
    private ClassDefinition classDefinition;

    private VelocityEngine engine;
    private VelocityContext context;

    public VelocityGenerator(ClassDefinition classDefinition)
    {
        this.classDefinition = classDefinition;
    }

    public void run()
    {
        createEngine();
        createContext();
        processTemplate();
    }

    private void createEngine()
    {
        engine = new VelocityEngine();
        engine.init();
    }

    private void createContext()
    {
        context = new VelocityContext();
        setVariables();
    }

    private void setVariables()
    {
        context.put("class", classDefinition);
    }

    private void processTemplate()
    {
        Template template = loadTemplate();
        StringWriter buffer = new StringWriter();
        template.merge(context, buffer);
        System.out.println(buffer.toString());
    }

    private Template loadTemplate()
    {
        return engine.getTemplate("templates/velocity/struct.vm");
    }
}
