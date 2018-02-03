package codeGeneration.java;

import at.yeoman.tools.generators.CodeWriter;
import codeGeneration.ClassDefinition;
import codeGeneration.Field;

public class StructuredStructWriter
{
    private CodeWriter out;
    private ClassDefinition def;

    StructuredStructWriter(CodeWriter out, ClassDefinition def)
    {
        this.out = out;
        this.def = def;
    }

    public void run()
    {
        writeHeader();
        writeClass();
        out.println();
    }

    private void writeHeader()
    {
        out.println("// java");
        out.println();
        out.println("package " + def.getPackageName() + ";");
    }

    private void writeClass()
    {
        out.println();
        writeClassHeader();
        writeBody();
    }

    private void writeClassHeader()
    {
        out.print("class " + def.getClassName() + " ");
    }

    private void writeBody()
    {
        out.beginBlock();
        writeBodyContent();
        out.endBlock();
    }

    private void writeBodyContent()
    {
        writeFields();
    }

    private void writeFields()
    {
        for (Field field : def.getFields())
        {
            out.println(field.getType() + " " + field.getName() + ";");
        }
    }
}
