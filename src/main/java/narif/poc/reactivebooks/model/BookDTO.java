package narif.poc.reactivebooks.model;

import jakarta.validation.constraints.*;

public record BookDTO(
        @NotBlank(message = "The books ISBN must be defined.")
        String isbn,

        @NotBlank(message = "Every book must have a name/title")
        @Pattern(regexp = "^[a-zA-Z0-9_\\s]*$", message = "Should not contain special characters.")
        String name,

        String authorName,

        @PositiveOrZero(message = "Price can not be negative.")
        Double price,

        @NotNull(message = "You must provide a valid value for quantity")
        @Min(value = 1, message = "You must provide at least one 1")
        @Max(value = 10, message = "You must noy provide more than 10")
        Integer quantity
) {
        public static BookDTO of(Book book){
                return new BookDTO(book.isbn(), book.name(),
                        book.authorName(), book.price(), book.quantity());
        }
}
