package net.arver.commerce.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * User.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_user", schema = "commerce")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "extra_info")
    private String extraInfo;

    @CreatedBy
    @Column(name = "created_by")
    private Long createdBy;

    @CreatedDate
    @Column(name = "created_date")
    private Timestamp createdDate;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private Long lastModifiedBy;

    @LastModifiedDate
    @Column(name = "lastModifiedDate")
    private Timestamp lastModifiedDate;

}
