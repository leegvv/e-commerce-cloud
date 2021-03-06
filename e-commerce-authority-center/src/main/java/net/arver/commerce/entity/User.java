package net.arver.commerce.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @Column(name = "creator")
    private Long creator;

    @CreatedDate
    @Column(name = "create_time")
    private Timestamp createTime;

    @LastModifiedBy
    @Column(name = "updater")
    private Long updater;

    @LastModifiedDate
    @Column(name = "update_time")
    private Timestamp updateTime;

}
