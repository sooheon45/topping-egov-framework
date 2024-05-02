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

    {{#aggregates}}
    @Resource(name = "{{nameCamelCase}}Service")
    private {{namePascalCase}}Service {{nameCamelCase}}Service;
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

        {{aggregate.nameCamelCase}}Repository.findById(
                // implement: Set the {{aggregate.namePascalCase}} Id from one of {{../namePascalCase}} event's corresponding property
                
            ).ifPresent({{aggregate.nameCamelCase}}->{
             {{aggregate.nameCamelCase}}.{{nameCamelCase}}({{nameCamelCase}}Command); 
        });
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



//     {{#policies}}
//     {{#if outgoingCommandInfo}}
//     @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='{{#relationEventInfo}}{{eventValue.namePascalCase}}{{/relationEventInfo}}'")
//     public void whenever{{#relationEventInfo}}{{eventValue.namePascalCase}}{{/relationEventInfo}}_{{namePascalCase}} (@Payload {{#relationEventInfo}}{{eventValue.namePascalCase}}{{/relationEventInfo}} {{#relationEventInfo}}{{eventValue.nameCamelCase}}{{/relationEventInfo}}) throws Exception {
//         {{#outgoingCommandInfo}}{{commandValue.namePascalCase}}Command{{/outgoingCommandInfo}} {{#outgoingCommandInfo}}{{commandValue.nameCamelCase}}Command{{/outgoingCommandInfo}} = new {{#outgoingCommandInfo}}{{commandValue.namePascalCase}}Command{{/outgoingCommandInfo}};
//         /** complete {{#outgoingCommandInfo}}{{commandValue.nameCamelCase}}Command{{/outgoingCommandInfo}}
//         {{#outgoingCommandInfo}}{{commandValue.nameCamelCase}}Command{{/outgoingCommandInfo}}.set???({{#relationEventInfo}}{{eventValue.nameCamelCase}}{{/relationEventInfo}}.get??());
//         */
            
//         // call Service Logic //
//         {{#../aggregates}}{{nameCamelCase}}{{/../aggregates}}Service.{{#outgoingCommandInfo}}{{commandValue.nameCamelCase}}{{/outgoingCommandInfo}}({{#outgoingCommandInfo}}{{commandValue.nameCamelCase}}Command{{/outgoingCommandInfo}});
//     }
//     {{else}}

//     {{#relationEventInfo}}
//     @StreamListener(value=KafkaProcessor.INPUT, condition="headers['type']=='{{eventValue.namePascalCase}}'")
//     public void whenever{{eventValue.namePascalCase}}_{{../namePascalCase}}(@Payload {{eventValue.namePascalCase}} {{eventValue.nameCamelCase}}){
//         {{eventValue.namePascalCase}} event = {{eventValue.nameCamelCase}};

//         {{#../relationAggregateInfo}}
//         // REST Request Sample
        
//         // {{aggregateValue.nameCamelCase}}Service.get{{aggregateValue.namePascalCase}}(/** mapping value needed */);
//         {{/../relationAggregateInfo}}
//         {{#todo ../description}}{{/todo}}
//         // Sample Logic //
//         {{#../aggregateList}}
//         {{namePascalCase}}.{{../../nameCamelCase}}(event);
//         {{/../aggregateList}}
//     }
//     {{/relationEventInfo}}
//     {{/if}}
//     {{/policies}}

// //>>> Clean Arch / Inbound Adaptor


// <function>
// window.$HandleBars.registerHelper('todo', function (description) {

//     if(description){
//         description = description.replaceAll('\n','\n\t\t// ')
//         return description = '// Comments // \n\t\t//' + description;
//     }
//      return null;
// });
// </function>