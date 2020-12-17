package com.ozan.exchange.repo;

import com.ozan.exchange.domain.OzanExChangeTransaction;
import com.ozan.exchange.util.OzanDateUtils;
import com.ozan.exchange.util.OzanStringUtils;
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
    public List<OzanExChangeTransaction> exchangeHistory( final String transaction,
                    final String createdDate, Pageable pageable )
    {
        List<OzanExChangeTransaction> ozanExChangeTransactions =
                        getExchangeConversionHistory(transaction, createdDate, pageable);
        return ozanExChangeTransactions;
    }

    private List<OzanExChangeTransaction> getExchangeConversionHistory( String transaction,
                    String date, Pageable pageable )
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<OzanExChangeTransaction> cq = cb.createQuery(OzanExChangeTransaction.class);

        Root<OzanExChangeTransaction> exchangeConversionRoot =
                        cq.from(OzanExChangeTransaction.class);

        Predicate transactionPredicate = null;
        Predicate datePredicate = null;

        List<Predicate> predicates = new ArrayList<>();
        if(null != transaction && OzanStringUtils.isNotBlank(transaction))
        {
            transactionPredicate = cb.equal(exchangeConversionRoot.get("transaction"),
                            UUID.fromString(transaction));
            predicates.add(transactionPredicate);
        }

        if(null != date && OzanStringUtils.isNotBlank(date))
        {
            datePredicate = cb.equal(exchangeConversionRoot.get("dateCreated"),
                            OzanDateUtils.fromString(date, OzanDateUtils.YYYY_MM_DD));
            predicates.add(datePredicate);
        }

        cq.where(predicates.toArray(new Predicate[predicates.size()]));

        cq.orderBy(cb.desc(exchangeConversionRoot.get("timestampCreated")));

        TypedQuery<OzanExChangeTransaction> query = entityManager.createQuery(cq);

        if(pageable.getPageSize() != 0)
        {
            query.setFirstResult(Long.valueOf(pageable.getOffset()).intValue())
                            .setMaxResults(pageable.getPageSize());
        }

        return query.getResultList();
    }
}
