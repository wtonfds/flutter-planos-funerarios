package br.com.monitoratec.farol.graphql.customScalars;

import graphql.language.IntValue;
import graphql.schema.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;

@Component
public class ByteScalarType extends BaseCustomScalar {
    private static final BigInteger BYTE_MAX = BigInteger.valueOf(Byte.MAX_VALUE);
    private static final BigInteger BYTE_MIN = BigInteger.valueOf(Byte.MIN_VALUE);

    @Bean
    public GraphQLScalarType createByteScalar() {
        return new GraphQLScalarType.Builder()
                .name("Byte")
                .description("Built-in Byte as Int")
                .coercing(new Coercing<Byte, Byte>() {

                    private Byte convertImpl(Object input) {
                        if (input instanceof Byte) {
                            return (Byte) input;
                        } else if (isNumberIsh(input)) {
                            BigDecimal value;
                            try {
                                value = new BigDecimal(input.toString());
                            } catch (NumberFormatException e) {
                                return null;
                            }
                            try {
                                return value.byteValueExact();
                            } catch (ArithmeticException e) {
                                return null;
                            }
                        } else {
                            return null;
                        }

                    }

                    @Override
                    public Byte serialize(Object input) {
                        Byte result = convertImpl(input);
                        if (result == null) {
                            throw new CoercingSerializeException(
                                    "Expected type 'Byte' but was '" + typeName(input) + "'."
                            );
                        }
                        return result;
                    }

                    @Override
                    public Byte parseValue(Object input) {
                        Byte result = convertImpl(input);
                        if (result == null) {
                            throw new CoercingParseValueException(
                                    "Expected type 'Byte' but was '" + typeName(input) + "'."
                            );
                        }
                        return result;
                    }

                    @Override
                    public Byte parseLiteral(Object input) {
                        if (!(input instanceof IntValue)) {
                            throw new CoercingParseLiteralException(
                                    "Expected AST type 'IntValue' but was '" + typeName(input) + "'."
                            );
                        }
                        BigInteger value = ((IntValue) input).getValue();
                        if (value.compareTo(BYTE_MIN) < 0 || value.compareTo(BYTE_MAX) > 0) {
                            throw new CoercingParseLiteralException(
                                    "Expected value to be in the Byte range but it was '" + value.toString() + "'"
                            );
                        }
                        return value.byteValue();
                    }
                }).build();
    }


}
