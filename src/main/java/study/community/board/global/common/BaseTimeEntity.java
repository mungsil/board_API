package study.community.board.global.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 해당 기능 사용 시 application 클래스에 @EnableJpaAuditing 달아주기
 * -
 * Audit: 감시하다
 * Auditing은 JPA에서 제공하는 기능이다. 공통된 기능(ex. 생성시간, 생성자, 업데이트시간)을 제공한다.
 *
 */

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseTimeEntity {
    @CreatedDate
    @Column(updatable = false) //최초 저장 후 수정 불가
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}