forEach: Aggregate
representativeFor: Command
fileName: {{namePascalCase}}Controller.java
path: {{boundedContext.name}}/{{{options.packagePath}}}/infra
---
package {{options.package}}.infra;
import java.util.Optional;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


import {{options.package}}.domain.*;
import {{options.package}}.service.*;

@RestController
// @RequestMapping(value="/{{namePlural}}")
public class {{namePascalCase}}Controller {

    @Resource(name = "{{nameCamelCase}}Service")
	private {{namePascalCase}}Service {{nameCamelCase}}Service;

    @GetMapping("/{{namePlural}}")
    public List<{{namePascalCase}}> getAll{{#changeFirstStr namePlural}}{{/changeFirstStr}}() throws Exception {
        // Get all {{namePlural}} via {{namePascalCase}}Service
        return {{nameCamelCase}}Service.getAll{{#changeFirstStr namePlural}}{{/changeFirstStr}}();
    }

    @GetMapping("/{{namePlural}}/{{#keyFieldDescriptor}}{{#wrapWithBracesKeyField nameCamelCase}}{{/wrapWithBracesKeyField}}{{/keyFieldDescriptor}}")
    public Optional<{{namePascalCase}}> get{{namePascalCase}}ById(@PathVariable {{#keyFieldDescriptor}}{{className}} {{nameCamelCase}}{{/keyFieldDescriptor}}) throws Exception {
        // Get a {{nameCamelCase}} by ID via {{namePascalCase}}Service
        return {{nameCamelCase}}Service.get{{namePascalCase}}ById({{#keyFieldDescriptor}}{{nameCamelCase}}{{/keyFieldDescriptor}});
    }

    @PostMapping("/{{namePlural}}")
    public {{namePascalCase}} create{{namePascalCase}}(@RequestBody {{namePascalCase}} {{nameCamelCase}}) throws Exception {
        // Create a new {{nameCamelCase}} via {{namePascalCase}}Service
        return {{nameCamelCase}}Service.create{{namePascalCase}}({{nameCamelCase}});
    }

    @PutMapping("/{{namePlural}}/{{#keyFieldDescriptor}}{{#wrapWithBracesKeyField nameCamelCase}}{{/wrapWithBracesKeyField}}{{/keyFieldDescriptor}}")
    public {{namePascalCase}} update{{namePascalCase}}(@PathVariable {{#keyFieldDescriptor}}{{className}} {{nameCamelCase}}{{/keyFieldDescriptor}}, @RequestBody {{namePascalCase}} {{nameCamelCase}}) throws Exception {
        // Update an existing {{nameCamelCase}} via {{namePascalCase}}Service
        return {{nameCamelCase}}Service.update{{namePascalCase}}({{nameCamelCase}});
    }

    @DeleteMapping("/{{namePlural}}/{{#keyFieldDescriptor}}{{#wrapWithBracesKeyField nameCamelCase}}{{/wrapWithBracesKeyField}}{{/keyFieldDescriptor}}")
    public void delete{{namePascalCase}}(@PathVariable {{#keyFieldDescriptor}}{{className}} {{nameCamelCase}}{{/keyFieldDescriptor}}) throws Exception {
        // Delete a {{nameCamelCase}} via {{namePascalCase}}Service
        {{nameCamelCase}}Service.delete{{namePascalCase}}({{#keyFieldDescriptor}}{{nameCamelCase}}{{/keyFieldDescriptor}});
    }

    {{#if commands}}
    {{#commands}}
    {{#if isExtendedVerb}}
    {{#if incomingRelations}}
    {{else}}
    {{#checkMethod controllerInfo.method}}
    @RequestMapping(value = "/{{#aggregate}}{{namePlural}}{{/aggregate}}/{{#aggregate}}{{#keyFieldDescriptor}}{{#wrapWithBracesKeyField nameCamelCase}}{{/wrapWithBracesKeyField}}{{/keyFieldDescriptor}}{{/aggregate}}/{{#if controllerInfo.apiPath}}{{controllerInfo.apiPath}}{{else}}{{#changeLowerCase nameCamelCase}}{{/changeLowerCase}}{{/if}}", method = RequestMethod.{{#controllerInfo}}{{method}}{{/controllerInfo}}, produces = "application/json;charset=UTF-8")
    public {{#aggregate}}{{namePascalCase}}{{/aggregate}} {{nameCamelCase}}(        
        @PathVariable(value = "id") {{../keyFieldDescriptor.className}} {{../keyFieldDescriptor.nameCamelCase}},
        @RequestBody {{namePascalCase}}Command {{nameCamelCase}}Command,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {

        return {{#aggregate}}{{nameCamelCase}}{{/aggregate}}Service.{{nameCamelCase}}({{nameCamelCase}}Command);

    }
    {{/checkMethod}}
    {{^checkMethod controllerInfo.method}}
    @RequestMapping(value = "{{#aggregate}}{{namePlural}}{{/aggregate}}", method = RequestMethod.{{#controllerInfo}}{{method}}{{/controllerInfo}}, produces = "application/json;charset=UTF-8")
    public {{#aggregate}}{{namePascalCase}}{{/aggregate}} {{nameCamelCase}}(        
        @RequestBody {{namePascalCase}}Command {{nameCamelCase}}Command,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {

        return {{#aggregate}}{{nameCamelCase}}{{/aggregate}}Service.{{nameCamelCase}}({{nameCamelCase}}Command);

    }
    {{/checkMethod}} 
    {{/if}}
    {{/if}}
    {{/commands}}
    {{/if}}
}

<function>
window.$HandleBars.registerHelper('changeLowerCase', function (str) {
    if (str) {
        return str.toLowerCase();
    }
    return str;
});

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

window.$HandleBars.registerHelper('isGeneralization', function (toName, name, type) {
    try {
        if(toName == null || name == null || type == null) {
            return false;
        } else {
            if(toName == name && type.includes("Generalization")) {
                return true;
            } else {
                return false;
            }
        }
    } catch(e) {
        console.log(e)
    }
});

window.$HandleBars.registerHelper('checkGeneralization', function (relations, name) {
    try {
        if (typeof relations == "undefined") {
            return 
        } else {
            for (var i = 0; i < relations.length; i ++ ) {
                if (relations[i] != null) {
                    if (relations[i].targetElement != "undefined") {
                        if(relations[i].targetElement.name.toLowerCase() == name && relations[i].relationType.includes("Generalization")) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    } catch(e) {
        console.log(e)
    }
});

window.$HandleBars.registerHelper('methodConvert', function (method) {
    if(method.endsWith("PUT")){
        return "save";
    } else {
        return "delete";
    }
});

window.$HandleBars.registerHelper('checkMethod', function (method, options) {
    if(method.endsWith("PUT") || method.endsWith("DELETE")){
        return options.fn(this);
    } else {
        return options.inverse(this);
    }
});

window.$HandleBars.registerHelper('hasFields', function (fieldDescriptors) {
    try {
        if(fieldDescriptors.length > 0) {
            return true;
        } else {
            return false;
        }
    } catch(e) {
        console.log(e)
    }
});

window.$HandleBars.registerHelper('isPut', function (method, options) {
    if(method.endsWith("PUT")){
        return options.fn(this);
    } else {
        return options.inverse(this);
    }
});

window.$HandleBars.registerHelper('isPOST', function (method, options) {
    if(method.endsWith("POST")){
        return options.fn(this);
    } else {
        return options.inverse(this);
    }
});
    
window.$HandleBars.registerHelper('isDelete', function (method, options) {
    if(method.endsWith("DELETE")){
        return options.fn(this);
    } else {
        return options.inverse(this);
    }
});

window.$HandleBars.registerHelper('toURL', function (className) {

    var pluralize = function(value, revert){

        var plural = {
            '(quiz)$'               : "$1zes",
            '^(ox)$'                : "$1en",
            '([m|l])ouse$'          : "$1ice",
            '(matr|vert|ind)ix|ex$' : "$1ices",
            '(x|ch|ss|sh)$'         : "$1es",
            '([^aeiouy]|qu)y$'      : "$1ies",
            '(hive)$'               : "$1s",
            '(?:([^f])fe|([lr])f)$' : "$1$2ves",
            '(shea|lea|loa|thie)f$' : "$1ves",
            'sis$'                  : "ses",
            '([ti])um$'             : "$1a",
            '(tomat|potat|ech|her|vet)o$': "$1oes",
            '(bu)s$'                : "$1ses",
            '(alias)$'              : "$1es",
            '(octop)us$'            : "$1i",
            '(ax|test)is$'          : "$1es",
            '(us)$'                 : "$1es",
            '([^s]+)$'              : "$1s"
        };

        var singular = {
            '(quiz)zes$'             : "$1",
            '(matr)ices$'            : "$1ix",
            '(vert|ind)ices$'        : "$1ex",
            '^(ox)en$'               : "$1",
            '(alias)es$'             : "$1",
            '(octop|vir)i$'          : "$1us",
            '(cris|ax|test)es$'      : "$1is",
            '(shoe)s$'               : "$1",
            '(o)es$'                 : "$1",
            '(bus)es$'               : "$1",
            '([m|l])ice$'            : "$1ouse",
            '(x|ch|ss|sh)es$'        : "$1",
            '(m)ovies$'              : "$1ovie",
            '(s)eries$'              : "$1eries",
            '([^aeiouy]|qu)ies$'     : "$1y",
            '([lr])ves$'             : "$1f",
            '(tive)s$'               : "$1",
            '(hive)s$'               : "$1",
            '(li|wi|kni)ves$'        : "$1fe",
            '(shea|loa|lea|thie)ves$': "$1f",
            '(^analy)ses$'           : "$1sis",
            '((a)naly|(b)a|(d)iagno|(p)arenthe|(p)rogno|(s)ynop|(t)he)ses$': "$1$2sis",
            '([ti])a$'               : "$1um",
            '(n)ews$'                : "$1ews",
            '(h|bl)ouses$'           : "$1ouse",
            '(corpse)s$'             : "$1",
            '(us)es$'                : "$1",
            's$'                     : ""
        };

        var irregular = {
            'move'   : 'moves',
            'foot'   : 'feet',
            'goose'  : 'geese',
            'sex'    : 'sexes',
            'child'  : 'children',
            'man'    : 'men',
            'tooth'  : 'teeth',
            'person' : 'people',
            'index'  : 'indexes'
        };

        var uncountable = [
            'sheep',
            'fish',
            'deer',
            'moose',
            'series',
            'species',
            'money',
            'rice',
            'information',
            'equipment'
        ];

        // save some time in the case that singular and plural are the same
        // console.log("value = " + value)
        if(uncountable.indexOf(value.toLowerCase()) >= 0)
        return this;

        // check for irregular forms
        for(var word in irregular){

            if(revert) {
                var pattern = new RegExp(irregular[word]+'$', 'i');
                var replace = word;
            } else { 
                var pattern = new RegExp(word+'$', 'i');
                var replace = irregular[word];
            }
            if(pattern.test(value))
                return value.replace(pattern, replace);
        }

        if(revert) var array = singular;
            else  var array = plural;

        // check for matches using regular expressions
        for(var reg in array) {

            var pattern = new RegExp(reg, 'i');

            if(pattern.test(value))
                return value.replace(pattern, array[reg]);
        }

        return value;
    }

    return pluralize(className.toLowerCase())
})
</function>
