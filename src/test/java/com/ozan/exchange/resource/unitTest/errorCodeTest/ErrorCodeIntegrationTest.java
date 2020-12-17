package com.ozan.exchange.resource.unitTest.errorCodeTest;

import com.ozan.exchange.domain.OzanExChangeTransaction;
import com.ozan.exchange.repo.ExchangeConversionRepo;
import com.ozan.exchange.util.OzanDateUtils;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith( SpringRunner.class )
@DataJpaTest
public class ErrorCodeIntegrationTest
{

    @Autowired
    private ExchangeConversionRepo exchangeConversionRepo;

    @Test
    public void check_is_not_null()
    {
        Assertions.assertNotNull(this.exchangeConversionRepo);
    }

    @Test
    public void save_and_fetch_with_transaction()
    {
        OzanExChangeTransaction ozanExChangeTransaction =
                        OzanExChangeTransaction.builder().amount(10.0).base("EUR").conversion(95.2)
                                        .timestampCreated(OzanDateUtils.fromString("2020-12-17",
                                                        OzanDateUtils.YYYY_MM_DD))
                                        .dateCreated(OzanDateUtils.fromString("2020-12-17",
                                                        OzanDateUtils.YYYY_MM_DD)).rate(9.52)
                                        .symbol("TRY").build();

        OzanExChangeTransaction saved = this.exchangeConversionRepo.save(ozanExChangeTransaction);

        Assertions.assertEquals(saved.getBase(), ozanExChangeTransaction.getBase());
        Assertions.assertNotNull(saved.getTransaction());

        Optional<OzanExChangeTransaction> exChangeTransaction =
                        this.exchangeConversionRepo.findByTransaction(saved.getTransaction());

        Assertions.assertEquals(exChangeTransaction.get().getTransaction(), saved.getTransaction());
    }

    @Test
    public void save_and_fetch_with_date()
    {
        OzanExChangeTransaction ozanExChangeTransaction =
                        OzanExChangeTransaction.builder().amount(10.0).base("EUR").conversion(95.2)
                                        .timestampCreated(OzanDateUtils.fromString("2020-12-17",
                                                        OzanDateUtils.YYYY_MM_DD))
                                        .dateCreated(OzanDateUtils.fromString("2020-12-17",
                                                        OzanDateUtils.YYYY_MM_DD)).rate(9.52)
                                        .symbol("TRY").build();

        OzanExChangeTransaction saved = this.exchangeConversionRepo.save(ozanExChangeTransaction);

        Assertions.assertEquals(saved.getBase(), ozanExChangeTransaction.getBase());
        Assertions.assertNotNull(saved.getTransaction());

        Page<OzanExChangeTransaction> exChangeTransaction = this.exchangeConversionRepo
                        .findByDateCreated(saved.getDateCreated(), PageRequest.of(0, 10));

        Assertions.assertEquals(exChangeTransaction.getContent().size(), 1);
        Assertions.assertEquals(exChangeTransaction.getContent().get(0).getTransaction(),
                        saved.getTransaction());

    }

    @Test
    public void save_and_fetch_with_transaction_date()
    {
        OzanExChangeTransaction ozanExChangeTransaction =
                        OzanExChangeTransaction.builder().amount(10.0).base("EUR").conversion(95.2).
                                        dateCreated(OzanDateUtils.nowAsDate())
                                        .timestampCreated(OzanDateUtils.nowAsDate()).
                                        rate(9.52).symbol("TRY").build();

        OzanExChangeTransaction saved = this.exchangeConversionRepo.save(ozanExChangeTransaction);

        Assertions.assertEquals(saved.getBase(), ozanExChangeTransaction.getBase());
        Assertions.assertNotNull(saved.getTransaction());

        List<OzanExChangeTransaction> exChangeTransaction = this.exchangeConversionRepo
                        .exchangeHistory(saved.getTransaction().toString(),
                                        OzanDateUtils.YYYY_MM_DD.format(saved.getDateCreated()),
                                        PageRequest.of(0, 10));

        Assertions.assertEquals(exChangeTransaction.size(), 1);
        Assertions.assertEquals(exChangeTransaction.get(0).getTransaction(),
                        saved.getTransaction());

    }
}
