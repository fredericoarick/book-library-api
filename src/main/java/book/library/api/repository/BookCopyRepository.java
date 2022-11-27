package book.library.api.repository;

import book.library.api.entity.BookCopy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCopyRepository extends CrudRepository<BookCopy, Long> {

}
