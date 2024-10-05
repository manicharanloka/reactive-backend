package com.example.reactivebackend.respository;

import com.example.reactivebackend.entity.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface QuoteMongoBlockingRepository extends PagingAndSortingRepository<Quote, String> {
    List<Quote> findAllByIdNotNullOrderByIdAsc(final Pageable page);
}
