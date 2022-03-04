package br.com.monitoratec.farol.graphql.customScalars;

import br.com.monitoratec.farol.graphql.customTypes.DateTime;
import graphql.language.StringValue;
import graphql.schema.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DateTimeScalarType extends BaseCustomScalar {

    @Bean
    public GraphQLScalarType createDateTimeScalar() {

        return new GraphQLScalarType.Builder()
                .name("DateTime")
                .description("DateTime: A string following the pattern: 'yyyyMMdd HH:mm:ss.SSS'")
                .coercing(new Coercing<DateTime, String>() {
                    @Override
                    public String serialize(Object o) throws CoercingSerializeException {
                        DateTime dateTime = (DateTime) o;
                        return dateTime.toString();
                    }

                    @Override
                    public DateTime parseValue(Object o) throws CoercingParseValueException {
                        String dateTimeAsString = ((String) o);
                        return DateTime.fromString(dateTimeAsString, true);
                    }

                    @Override
                    public DateTime parseLiteral(Object o) throws CoercingParseLiteralException {
                        String dateTimeAsString = ((StringValue) o).getValue();
                        return DateTime.fromString(dateTimeAsString, true);
                    }
                }).build();
    }
}
