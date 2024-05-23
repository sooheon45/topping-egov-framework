representativeFor: Policy
path: {{name}}/{{{options.packagePath}}}/infra
---
package {{options.package}}.infra;

import javax.naming.NameParser;

import javax.naming.NameParser;
import javax.transaction.Transactional;

import {{options.package}}.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import {{options.package}}.domain.*;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler{
    {{#aggregates}}
    @Autowired {{namePascalCase}}Repository {{nameCamelCase}}Repository;
    {{/aggregates}}

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}

    {{#policies}}
    {{#outgoing "ReadModel" .}}
    @Autowired
    {{../../options.package}}.external.{{aggregate.namePascalCase}}Service {{aggregate.nameCamelCase}}Service;

    {{/outgoing}}

    {{#incoming "Event" .}}
    @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='{{namePascalCase}}'")
    public void whenever{{namePascalCase}}_{{../namePascalCase}}(@Payload {{namePascalCase}} {{nameCamelCase}}){

        {{namePascalCase}} event = {{nameCamelCase}};
        System.out.println("\n\n##### listener {{../namePascalCase}} : " + {{nameCamelCase}} + "\n\n");

        {{#../aggregateList}}
        {{namePascalCase}}.{{../../nameCamelCase}}(event);        
        {{/../aggregateList}}

        {{#outgoing "Command" ..}}
        {{#isExtendedVerb}}
        {{namePascalCase}}Command {{nameCamelCase}}Command = new {{namePascalCase}}Command();
        // implement:  Map command properties from event
        // {{nameCamelCase}}Command.set??(event.get??());

        // {{aggregate.nameCamelCase}}Repository.findById(
                // implement: Set the {{aggregate.namePascalCase}} Id from one of {{../namePascalCase}} event's corresponding property
                
            // ).ifPresent({{aggregate.nameCamelCase}}->{
            //  {{aggregate.nameCamelCase}}.{{nameCamelCase}}({{nameCamelCase}}Command); 
        // });
        {{else}}
        {{aggregate.namePascalCase}} {{aggregate.nameCamelCase}} = new {{aggregate.namePascalCase}}();
        {{aggregate.nameCamelCase}}Repository.save({{aggregate.nameCamelCase}});
        {{/isExtendedVerb}}
        {{/outgoing}}


        {{#todo ../description}}{{/todo}}

    }
    {{/incoming}}

    {{/policies}}
}

//>>> Clean Arch / Inbound Adaptor


<function>
    window.$HandleBars.registerHelper('todo', function (description) {

        if(description){
            description = description.replaceAll('\n','\n\t\t// ')
            return description = '// Comments // \n\t\t//' + description;
        }
        return null;
    });
</function>
