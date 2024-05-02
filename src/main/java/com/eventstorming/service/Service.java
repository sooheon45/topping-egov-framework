forEach: Aggregate
fileName: {{namePascalCase}}Service.java
path: {{boundedContext.name}}/{{{options.packagePath}}}/service
mergeType: template
---
package {{options.package}}.service;

import {{options.package}}.domain.*;
import java.util.List;
import java.util.Optional;

public interface {{namePascalCase}}Service {    

    List<{{namePascalCase}}> getAll{{#changeFirstStr namePlural}}{{/changeFirstStr}}() throws Exception;
    Optional<{{namePascalCase}}> get{{namePascalCase}}ById({{#keyFieldDescriptor}}{{className}} {{nameCamelCase}}{{/keyFieldDescriptor}}) throws Exception;
    {{namePascalCase}} create{{namePascalCase}}({{namePascalCase}} {{nameCamelCase}}) throws Exception;
    {{namePascalCase}} update{{namePascalCase}}({{namePascalCase}} {{nameCamelCase}}) throws Exception;
    void delete{{namePascalCase}}({{#keyFieldDescriptor}}{{className}} {{nameCamelCase}}{{/keyFieldDescriptor}}) throws Exception;

    {{#if commands}}
    {{#commands}}
    {{#if isExtendedVerb}}
    {{#aggregate}}{{namePascalCase}}{{/aggregate}} {{nameCamelCase}}({{namePascalCase}}Command {{nameCamelCase}}Command) throws Exception;
    {{/if}}
    {{/commands}}
    {{/if}}
    
    {{#boundedContext}}
    {{#policies}}
    {{#if outgoingCommandInfo}}
    {{#../aggregates}}{{namePascalCase}}{{/../aggregates}} {{#outgoingCommandInfo}}{{commandValue.nameCamelCase}}({{commandValue.namePascalCase}}Command {{commandValue.nameCamelCase}}Command){{/outgoingCommandInfo}} throws Exception;
    {{/if}}
    {{/policies}}
    {{/boundedContext}}
}

<function>
    window.$HandleBars.registerHelper('wrapWithBracesKeyField', function (keyField) {
        if (keyField) {
            return `{${keyField}}`;
        }
        return keyField;
    });

    window.$HandleBars.registerHelper('changeFirstStr', function (name) {
        if (name && typeof name === 'string') {
            return name.charAt(0).toUpperCase() + name.slice(1);
        }
        return name;
    });

    window.$HandleBars.registerHelper('checkMethod', function (method, options) {
        if(method.endsWith("PUT") || method.endsWith("DELETE")){
            return options.fn(this);
        } else {
            return options.inverse(this);
        }
    });
</function>