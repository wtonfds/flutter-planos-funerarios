package br.com.monitoratec.farol.graphql.customScalars;

import br.com.monitoratec.farol.graphql.customTypes.Date;
import graphql.language.StringValue;
import graphql.schema.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DateScalarType extends BaseCustomScalar {

    public DateScalarType() {}

    @Bean
    public GraphQLScalarType createDateScalar() {
        return new GraphQLScalarType.Builder()
                .name("Date")
                .description("Date: A string following the pattern: 'yyyyMMdd'")
                .coercing(new Coercing<Date, String>() {
                    @Override
                    public String serialize(Object o) throws CoercingSerializeException {
                        Date date = (Date) o;
                        return date.toString();
                    }

                    @Override
                    public Date parseValue(Object o) throws CoercingParseValueException {
                            String dateAsString = ((String) o);
                            return Date.fromString(dateAsString, true);
                    }

                    @Override
                    public Date parseLiteral(Object o) throws CoercingParseLiteralException {
                            String dateAsString = ((StringValue) o).getValue();
                            return Date.fromString(dateAsString, true);
                    }
                }).build();
    }
}
