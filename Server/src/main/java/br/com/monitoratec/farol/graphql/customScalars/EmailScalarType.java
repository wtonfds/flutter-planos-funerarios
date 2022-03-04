package br.com.monitoratec.farol.graphql.customScalars;

import br.com.monitoratec.farol.graphql.customTypes.Email;
import graphql.language.StringValue;
import graphql.schema.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class EmailScalarType extends BaseCustomScalar {

    @Bean
    public GraphQLScalarType createEmailScalar() {

        return new GraphQLScalarType.Builder()
                .name("Email")
                .description("Email (string with pattern validation)")
                .coercing(new Coercing<Email, String>() {
                    @Override
                    public String serialize(Object o) throws CoercingSerializeException {
                        Email email = (Email) o;
                        return email.stringValue();
                    }

                    @Override
                    public Email parseValue(Object o) throws CoercingParseValueException {
                        return Email.fromString((String) o);
                    }

                    @Override
                    public Email parseLiteral(Object o) throws CoercingParseLiteralException {
                        return Email.fromString(((StringValue) o).getValue());
                    }
                }).build();
    }
}
