package com.ozan.exchange.repo;

import com.ozan.exchange.domain.ExchangeConversion;
import com.ozan.exchange.util.DateUtils;
import com.ozan.exchange.util.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * We can use Criteria to make dynamic here
 */
@Repository
public class ExchangeConversionRepoImpl implements DefaultOzanExchangeRepo
{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ExchangeConversion> exchangeHistory( final String transaction,
                    final String createdDate, Pageable pageable )
    {
        List<ExchangeConversion> exchangeConversions =
                        getExchangeConversionHistory(transaction, createdDate, pageable);
        return exchangeConversions;
    }

    private List<ExchangeConversion> getExchangeConversionHistory( String transaction, String date,
                    Pageable pageable )
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<ExchangeConversion> cq = cb.createQuery(ExchangeConversion.class);

        Root<ExchangeConversion> exchangeConversionRoot = cq.from(ExchangeConversion.class);

        Predicate authorNamePredicate = null;
        Predicate datePredicate = null;

        List<Predicate> predicates = new ArrayList<>();
        if(null != transaction && StringUtils.isNotBlank(transaction))
        {
            authorNamePredicate = cb.equal(exchangeConversionRoot.get("transaction"),
                            UUID.fromString(transaction));
            predicates.add(authorNamePredicate);
        }

        if(null != date && StringUtils.isNotBlank(date))
        {
            datePredicate = cb.equal(exchangeConversionRoot.get("dateCreated"),
                            DateUtils.fromString(date, DateUtils.YYYY_MM_DD));
            predicates.add(datePredicate);
        }

        cq.where(predicates.toArray(new Predicate[predicates.size()]));

        cq.orderBy(cb.desc(exchangeConversionRoot.get("timestampCreated")));

        TypedQuery<ExchangeConversion> query = entityManager.createQuery(cq);

        if(pageable.getPageSize() != 0)
        {
            query.setFirstResult(Long.valueOf(pageable.getOffset()).intValue())
                            .setMaxResults(pageable.getPageSize());
        }

        return query.getResultList();
    }
}
