package codeGeneration;

import java.util.List;

public class ClassDefinition
{
    private String packageName;
    private String className;
    private List<Field> fields;

    ClassDefinition(String packageName, String className, List<Field> fields)
    {
        this.packageName = packageName;
        this.className = className;
        this.fields = fields;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public String getClassName()
    {
        return className;
    }

    public List<Field> getFields()
    {
        return fields;
    }
}
