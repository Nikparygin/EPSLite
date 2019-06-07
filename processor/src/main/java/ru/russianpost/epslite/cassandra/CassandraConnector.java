package ru.russianpost.epslite.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.*;

import java.util.Optional;

public final class CassandraConnector {

   private static final ConsistencyLevel[] CONSISTENCY_LEVELS = {
         ConsistencyLevel.ANY,
         ConsistencyLevel.ONE,
         ConsistencyLevel.TWO,
         ConsistencyLevel.THREE,
         ConsistencyLevel.QUORUM,
         ConsistencyLevel.ALL,
         ConsistencyLevel.LOCAL_QUORUM,
         ConsistencyLevel.EACH_QUORUM,
         ConsistencyLevel.SERIAL,
         ConsistencyLevel.LOCAL_SERIAL,
         ConsistencyLevel.LOCAL_ONE
   };

   private ConsistencyLevel defaultConsistencyLevel = ConsistencyLevel.LOCAL_QUORUM;

   private ConsistencyLevel defaultSerialConsistencyLevel = ConsistencyLevel.LOCAL_SERIAL;

   private static CassandraConnector instance;

   private Cluster cluster;

   private String[] hosts;

   private String defaultKeyspace;

   private RetryPolicy retryPolicy;

   public static CassandraConnector getInstance() {
      if (instance == null) {
         synchronized (CassandraConnector.class) {
            if (instance == null) {
               instance = new CassandraConnector();
            }
         }
      }
      return instance;
   }

   public void init(
         final String[] aHosts,
         final String aDefaultKeyspace,
         final int defaultConsistencyLevelCode,
         final Integer defaultSerialConsistencyLevelCode,
         final RetryPolicyType retryPolicyType
   ) {
      this.hosts = aHosts;
      this.defaultKeyspace = aDefaultKeyspace;
      this.retryPolicy = retryPolicyType != null ? makeRetryPolicy(retryPolicyType) : null;

      if (0 <= defaultConsistencyLevelCode && defaultConsistencyLevelCode < CONSISTENCY_LEVELS.length) {
         this.defaultConsistencyLevel = CONSISTENCY_LEVELS[defaultConsistencyLevelCode];
      }

      if (defaultSerialConsistencyLevelCode != null && 0 <= defaultSerialConsistencyLevelCode &&
            defaultSerialConsistencyLevelCode < CONSISTENCY_LEVELS.length) {
         this.defaultSerialConsistencyLevel = CONSISTENCY_LEVELS[defaultSerialConsistencyLevelCode];
      }

      closeSession();
   }

   public ConsistencyLevel getDefaultConsistencyLevel() {
      return defaultConsistencyLevel;
   }

   public ConsistencyLevel getDefaultSerialConsistencyLevel() {
      return defaultSerialConsistencyLevel;
   }

   public String getDefaultKeyspace() {
      return defaultKeyspace;
   }

   public Session getSession() {
      Session session = getCluster().connect(getDefaultKeyspace());
      return session;
   }

   public void  closeSession() {
      getSession().close();
   }

   private RetryPolicy makeRetryPolicy(final RetryPolicyType type) {
      final RetryPolicy policy;
      switch (type) {
         case Default:
            policy = DefaultRetryPolicy.INSTANCE;
            break;
         case DowngradingConsistency:
            policy = DowngradingConsistencyRetryPolicy.INSTANCE;
            break;
         case Fallthrough:
            policy = FallthroughRetryPolicy.INSTANCE;
            break;
         default: throw new RuntimeException("Unknown RetryPolicyType");
      }
      return policy;
   }

   private synchronized Cluster getCluster() {
      if (cluster == null || cluster.isClosed()) {
         final Cluster.Builder builder = Cluster.builder();

         builder.addContactPoints(hosts).withReconnectionPolicy(new ConstantReconnectionPolicy(100L));
         Optional.ofNullable(retryPolicy).ifPresent(builder::withRetryPolicy);
         cluster = builder.build();
         cluster.init();
      }
      return cluster;
   }

   private CassandraConnector() {

   }

}
