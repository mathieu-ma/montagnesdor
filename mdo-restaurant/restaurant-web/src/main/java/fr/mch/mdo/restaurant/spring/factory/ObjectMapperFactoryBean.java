package fr.mch.mdo.restaurant.spring.factory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class ObjectMapperFactoryBean implements FactoryBean<ObjectMapper> {

    private ObjectMapper objectMapper;

    public ObjectMapperFactoryBean() {
        objectMapper = new ObjectMapper(); 
        // Replace objectMapper.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false)
        // by objectMapper.setSerializationInclusion(Inclusion.NON_NULL) because of deprecated annotation
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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
}
