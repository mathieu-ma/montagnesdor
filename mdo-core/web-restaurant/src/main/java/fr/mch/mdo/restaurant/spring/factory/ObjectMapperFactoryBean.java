package fr.mch.mdo.restaurant.spring.factory;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import fr.mch.mdo.restaurant.dto.beans.TableHeader;

@Component
public class ObjectMapperFactoryBean implements FactoryBean<ObjectMapper> {

    private ObjectMapper objectMapper;

    public ObjectMapperFactoryBean() {
        objectMapper = new ObjectMapper(); 
        // Replace objectMapper.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false)
        // by objectMapper.setSerializationInclusion(Inclusion.NON_NULL) because of deprecated annotation
        objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
        objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public ObjectMapper getObject() throws Exception {
        return objectMapper;
    }

    @Override
    public Class<?> getObjectType() {
        return ObjectMapper.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public static void main(String[] args) throws Exception {
    	ObjectMapperFactoryBean factory = new ObjectMapperFactoryBean();
    	ObjectNode json = JsonNodeFactory.instance.objectNode();
    	json.put("id", 1L);
    	TableHeader header = factory.objectMapper.readValue(json, TableHeader.class);
    	System.out.println(header);
    	System.out.println("===============");
    	System.out.println(factory.objectMapper.writeValueAsString(header));
	}
}
