package br.com.monitoratec.farol.sql.config;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

//Handler for PostgreSQL enum types - see https://vladmihalcea.com/the-best-way-to-map-an-enum-type-with-jpa-and-hibernate/
public class PostgreSQLEnumType extends EnumType<Enum<?>> {
    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        st.setObject(index, value != null ? ((Enum<?>) value).name() : null, Types.OTHER);
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.OTHER};
    }
}
