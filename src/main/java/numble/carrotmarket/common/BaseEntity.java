package numble.carrotmarket.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import numble.carrotmarket.exception.CustomException;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Duration;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class BaseEntity {

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getformattedDate(LocalDateTime now){
        return TimeUtils.ofTimeFormat(createdAt, now);
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
