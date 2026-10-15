package kz.iitu.spring_lab_01.catalog;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findAll(String author) {
        return repository.findAll().stream()
                .filter(b -> author == null
                        || b.author().toLowerCase().contains(author.toLowerCase()))
                .sorted(Comparator.comparing(Book::id))
                .toList();
    }

    public Optional<Book> findById(long id) {
        return repository.findById(id);
    }

    public Book create(Book book) {
        return repository.save(new Book(null, book.title(), book.author(), book.year()));
    }

    public Optional<Book> update(long id, Book book) {
        return repository.findById(id)
                .map(old -> repository.save(new Book(id, book.title(), book.author(), book.year())));
    }

    public BookStats stats() {
        List<Book> all = repository.findAll();
        if (all.isEmpty()) {
            return new BookStats(0, null, null);
        }
        IntSummaryStatistics s = all.stream().mapToInt(Book::year).summaryStatistics();
        return new BookStats((int) s.getCount(), s.getMin(), s.getMax());
    }

    public boolean delete(long id) {
        return repository.deleteById(id);
    }
}