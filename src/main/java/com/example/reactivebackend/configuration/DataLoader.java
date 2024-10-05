package com.example.reactivebackend.configuration;

import com.example.reactivebackend.entity.Quote;
import com.example.reactivebackend.respository.QuoteMongoReactiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Supplier;

@Component
public class DataLoader implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);
    private final QuoteMongoReactiveRepository quoteMongoReactiveRepository;

    public DataLoader(final QuoteMongoReactiveRepository quoteMongoReactiveRepository) {
        this.quoteMongoReactiveRepository = quoteMongoReactiveRepository;
    }

    @Override
    public void run(final ApplicationArguments args) {
        if(quoteMongoReactiveRepository.count().block() == 0L) {
            var idSupplier = getIdSequenceSupplier();
            var bufferedReader = new BufferedReader(
                    new InputStreamReader(getClass()
                            .getClassLoader()
                            .getResourceAsStream("pg2000.txt"))
            );
            Flux.fromStream(
                    bufferedReader.lines()
                            .filter(l->!l.trim().isEmpty())
                            .map(l->quoteMongoReactiveRepository.save(
                                    new Quote(idSupplier.get(),
                                            "El Quijjote",l))
                            )
            ).subscribe(m->log.info("New quote loaded: {}", m.block()));
            log.info("Repository now contains {} entries",
                    quoteMongoReactiveRepository.count().block());
        }
    }

    private Supplier<String> getIdSequenceSupplier() {
        return new Supplier<>() {
            Long l=0L;
            @Override
            public String get() {
                return String.format("%05d", l++);
            }
        };
    }
}
