forEach: Aggregate
fileName: {{namePascalCase}}ServiceImpl.java
path: {{boundedContext.name}}/{{{options.packagePath}}}/service/impl
---
package {{options.package}}.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
{{#if commands}}
{{#commands}}
{{#if isExtendedVerb}}
import {{../options.package}}.domain.{{namePascalCase}}Command;
{{/if}}
{{/commands}}
{{/if}}
import {{options.package}}.domain.{{namePascalCase}};
import {{options.package}}.domain.{{namePascalCase}}Repository;
import {{options.package}}.service.{{namePascalCase}}Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("{{nameCamelCase}}Service")
@Transactional
public class {{namePascalCase}}ServiceImpl extends EgovAbstractServiceImpl implements {{namePascalCase}}Service {

     private static final Logger LOGGER = LoggerFactory.getLogger({{namePascalCase}}ServiceImpl.class);

    @Autowired
    {{namePascalCase}}Repository {{nameCamelCase}}Repository;

    @Override
    public List<{{namePascalCase}}> getAll{{#changeFirstStr namePlural}}{{/changeFirstStr}}() throws Exception {
        // Get all {{namePlural}}
        return StreamSupport.stream({{nameCamelCase}}Repository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<{{namePascalCase}}> get{{namePascalCase}}ById({{#keyFieldDescriptor}}{{className}} {{nameCamelCase}}{{/keyFieldDescriptor}}) throws Exception {
        // Get a {{nameCamelCase}} by ID
        return {{nameCamelCase}}Repository.findById({{#keyFieldDescriptor}}{{nameCamelCase}}{{/keyFieldDescriptor}});
    }

    @Override
    public {{namePascalCase}} create{{namePascalCase}}({{namePascalCase}} {{nameCamelCase}}) throws Exception {
        // Create a new {{nameCamelCase}}
        return {{nameCamelCase}}Repository.save({{nameCamelCase}});
    }    

    @Override
    public {{namePascalCase}} update{{namePascalCase}}({{namePascalCase}} {{nameCamelCase}})  throws Exception {
        // Update an existing {{nameCamelCase}} via {{namePascalCase}}Service
        if ({{nameCamelCase}}Repository.existsById({{nameCamelCase}}.get{{#keyFieldDescriptor}}{{namePascalCase}}{{/keyFieldDescriptor}}())) {
            return {{nameCamelCase}}Repository.save({{nameCamelCase}});
        } else {
            throw processException("info.nodata.msg");
        }
    }

    @Override
    public void delete{{namePascalCase}}({{#keyFieldDescriptor}}{{className}} {{nameCamelCase}}{{/keyFieldDescriptor}}) throws Exception {
        // Delete a {{nameCamelCase}}
        {{nameCamelCase}}Repository.deleteById({{#keyFieldDescriptor}}{{nameCamelCase}}{{/keyFieldDescriptor}});
    }

    {{#if commands}}
    {{#commands}}
    {{#if isExtendedVerb}}
    @Override
	public {{../namePascalCase}} {{nameCamelCase}}({{namePascalCase}}Command {{nameCamelCase}}Command) throws Exception {        

        // You can implement logic here, or call the domain method of the {{#aggregate}}{{namePascalCase}}{{/aggregate}}.
        
        /** Option 1-1:  implement logic here     
            {{../namePascalCase}} {{../nameCamelCase}} = new {{../namePascalCase}}();
            {{../nameCamelCase}}.setUserId(event.getUserId());

            {{../nameCamelCase}}Repository.save({{../nameCamelCase}});   
        */
        
        Optional<{{../namePascalCase}}> optional{{../namePascalCase}} = {{../nameCamelCase}}Repository.findById({{nameCamelCase}}Command.get{{../namePascalCase}}Id());

        if (optional{{../namePascalCase}}.isPresent()) {
            {{../namePascalCase}} {{../nameCamelCase}} = optional{{../namePascalCase}}.get();
            
            // business Logic....
            {{../namePascalCase}}.{{nameCamelCase}}({{nameCamelCase}}Command);
            {{../nameCamelCase}}Repository.save({{../nameCamelCase}});

            return {{../nameCamelCase}};
        } else {
            throw processException("info.nodata.msg");
        }
    }
    {{/if}}
    {{/commands}}
    {{/if}}
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