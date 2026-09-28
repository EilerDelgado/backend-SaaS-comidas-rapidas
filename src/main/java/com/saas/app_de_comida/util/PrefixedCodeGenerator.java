package com.saas.app_de_comida.util;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.hibernate.query.Query;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PrefixedCodeGenerator implements IdentifierGenerator, Configurable {

    private String prefix;
    private static final ConcurrentHashMap<String, AtomicInteger> counters = new ConcurrentHashMap<>();

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        this.prefix = params.getProperty("prefix", "PRD");
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        AtomicInteger counter = counters.computeIfAbsent(prefix, k -> {
            String entityName = object.getClass().getSimpleName();
            // Buscar el máximo código que empieza con este prefijo
            String queryStr = String.format("SELECT MAX(CAST(SUBSTRING(e.codigo, %d) AS int)) FROM %s e WHERE e.codigo LIKE :prefix", prefix.length() + 1, entityName);
            
            try {
                Query<Integer> query = session.createQuery(queryStr, Integer.class);
                query.setParameter("prefix", prefix + "%");
                Integer max = query.uniqueResult();
                return new AtomicInteger(max != null ? max : 0);
            } catch (Exception e) {
                return new AtomicInteger(0);
            }
        });

        int next = counter.incrementAndGet();
        return String.format("%s%03d", prefix, next);
    }
}
