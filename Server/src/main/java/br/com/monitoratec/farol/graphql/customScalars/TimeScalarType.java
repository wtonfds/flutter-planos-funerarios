package br.com.monitoratec.farol.graphql.customScalars;

import br.com.monitoratec.farol.graphql.customTypes.Time;
import graphql.language.StringValue;
import graphql.schema.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class TimeScalarType extends BaseCustomScalar {

    @Bean
    public GraphQLScalarType createTimeScalar() {
        return new GraphQLScalarType.Builder()
                .name("Time")
                .description("Time: A string following the pattern: 'HH:mm:ss.SSS'")
                .coercing(new Coercing<Time, String>() {
                    @Override
                    public String serialize(Object o) throws CoercingSerializeException {
                        Time time = (Time) o;
                        return time.toString();
                    }

                    @Override
                    public Time parseValue(Object o) throws CoercingParseValueException {
                        String timeAsString = ((String) o);
                        return Time.fromString(timeAsString, true);
                    }

                    @Override
                    public Time parseLiteral(Object o) throws CoercingParseLiteralException {
                        String timeAsString = ((StringValue) o).getValue();
                        return Time.fromString(timeAsString, true);
                    }
                }).build();
    }
}
