package narif.poc.reactivebooks.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("books")
public record Book(
        @Id
        Long id,

        String isbn,

        String name,

        String authorName,

        Double price,

        Integer quantity,

        @CreatedDate
        Instant createdDate,

        @LastModifiedDate
        Instant lastModifiedDate,

        @Version
        int version
) {
    public static Book of(BookDTO dto){
        return new Book(null, dto.isbn(), dto.name(), dto.authorName(),
                dto.price(), dto.quantity(), null, null, 0);
    }
}
