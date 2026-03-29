package io.github.cursodsousa.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "AUTHOR", schema = "public")
@Getter
@Setter
@ToString(exclude = "books")
@EntityListeners(AuditingEntityListener.class)
public class Author {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateBirth;

    @Column(name = "nationality", length = 50 ,nullable = false)
    private String nationality;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Book> books;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false) // Mudei de update_date para last_modified_date
    private LocalDateTime lastModifiedDate;

    @Column(name = "user_id")
    private UUID userId;
}
