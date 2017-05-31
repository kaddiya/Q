package org.kaddiya.QClient.consumer.internal.example

import com.google.gson.Gson
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.kaddiya.QClient.common.BrokerConfig
import org.kaddiya.QClient.common.Message
import org.kaddiya.QClient.consumer.internal.SimpleConsumer

//@CompileStatic
@Slf4j
class Main {

    public static void main(String[] arggs) {


         //due to the type erasure we to tell our abstract class on how to unmarshall the content to our type and then pass in a
        //a closure that will do the transformation and perform the sideeffect of when we receive a message
        //In this example we will just log out the value of the `SomePayload`
        Closure<SomePayload> marshallingAndCallBackClosure = { Message m ->
            try {
                SomePayload p = new SomePayload("hallo!")
                def a =new JsonSlurper().parse(m.content.bytes)
                //log.info(new Gson().toJson(p))

                SomePayload rsult = new Gson().fromJson(m.content, SomePayload.class)
                log.info(m.uuid.toString() + "--" + m.content)

            }catch (Exception e){
                log.error("could not marshall the json"+e.getMessage())
                return  null
            }
        }


        SimpleConsumer bc = new SimpleConsumer<SomePayload>("1", new BrokerConfig("http", "localhost", 8080), Arrays.asList(""), marshallingAndCallBackClosure)
        bc.consumeMesage();
        }


    }




