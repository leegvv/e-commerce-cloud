package net.arver.commerce.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * User.
 *
 * @author leegvv
 * @version 1.0.0.0
 * Description:
 **/
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

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
    @Column(name = "last_modified_date")
    private Timestamp lastModifiedDate;
}
