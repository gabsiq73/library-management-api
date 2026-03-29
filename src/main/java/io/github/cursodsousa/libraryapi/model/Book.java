package io.github.cursodsousa.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "BOOK")
@Data
@ToString(exclude = "author")
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false, unique = true)
    private String isbn;

    @Column(name = "title", length = 150, nullable = false)
    private String title;

    @Column(name = "publication_date")
    private LocalDate publication_date;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", length = 50, nullable = false)
    private BookGenre genre;

    @Column(name = "price", precision = 18, scale = 2)
    private BigDecimal price;

    @ManyToOne(
    //      cascade = CascadeType.ALL
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "id_author")
    private Author author;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private LocalDateTime lastModifiedDate;

    @Column(name = "user_id")
    private UUID userId;

}
