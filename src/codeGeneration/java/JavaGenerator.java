package codeGeneration.java;

import at.yeoman.tools.generators.CodeWriter;
import codeGeneration.ClassDefinition;

import java.io.IOException;
import java.io.StringWriter;

public class JavaGenerator
{
    private ClassDefinition classDefinition;

    public JavaGenerator(ClassDefinition classDefinition)
    {
        this.classDefinition = classDefinition;
    }

    public void run() throws IOException
    {
        printCode();
    }

    private void printCode() throws IOException
    {
        StringWriter buffer = new StringWriter();
        CodeWriter out = new CodeWriter(buffer);
        writeCode(out);
        String code = buffer.toString();
        System.out.println(code);
    }

    private void writeCode(CodeWriter out)
    {
        new StructuredStructWriter(out, classDefinition).run();
        new DenseStructWriter(out, classDefinition).run();
    }
}
