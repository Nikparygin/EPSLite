package ru.russianpost.epslite.cassandra;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;

import java.util.function.Supplier;

public class CassandraQuery {

   private static final int RETRIES_COUNT = 1;

   private PreparedStatement preparedStatement;

   public static CassandraQuery query(
         final String cql,
         final String keyspace,
         final ConsistencyLevel consistencyLevel,
         final ConsistencyLevel serialConsistencyLevel
   ) {
      CassandraQuery cassandraQuery = new CassandraQuery();
         cassandraQuery.preparedStatement = prepareStatement(cql, consistencyLevel, serialConsistencyLevel);
      return cassandraQuery;
   }

   public static CassandraQuery query (String cql) {
      final CassandraConnector connector = CassandraConnector.getInstance();
      return query(cql, connector.getDefaultKeyspace(), connector.getDefaultConsistencyLevel(), connector.getDefaultSerialConsistencyLevel());
   }

   public ResultSet execute(final Object... values) {
      try {
         return runWithReconnect(() -> CassandraConnector.getInstance().getSession().execute(preparedStatement.bind(values)));
      } catch (final Exception e) {
         throw e;
      }
   }

   private static PreparedStatement prepareStatement(
         final String cql,
         final ConsistencyLevel consistencyLevel,
         final ConsistencyLevel serialConsistencyLevel
   ) {
      try {
         return runWithReconnect(
               () -> CassandraConnector.getInstance().getSession().prepare(cql)
                     .setConsistencyLevel(consistencyLevel).setSerialConsistencyLevel(serialConsistencyLevel));
      } catch (final Exception e) {
         throw e;
      }
   }

   private static <T> T runWithRetries(final Supplier<T> supplier) {
      for (int i = 0; i < RETRIES_COUNT - 1; i++) {
         try {
            return supplier.get();
         } catch (final Exception e) {
         }
      }

      return supplier.get();
   }

   private static <T> T runWithReconnect(final Supplier<T> supplier) {
      try {
         return runWithRetries(supplier);
      } catch (final Exception e) {
      }

      CassandraConnector.getInstance().closeSession();

      return runWithRetries(supplier);
   }
}