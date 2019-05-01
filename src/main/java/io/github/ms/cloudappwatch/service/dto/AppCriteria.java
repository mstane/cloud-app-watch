package io.github.ms.cloudappwatch.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.ms.cloudappwatch.domain.enumeration.AppStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the App entity. This class is used in AppResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /apps?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AppCriteria implements Serializable {
    /**
     * Class for filtering AppStatus
     */
    public static class AppStatusFilter extends Filter<AppStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter serviceFlag;

    private AppStatusFilter status;

    private LongFilter serverId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getServiceFlag() {
        return serviceFlag;
    }

    public void setServiceFlag(BooleanFilter serviceFlag) {
        this.serviceFlag = serviceFlag;
    }

    public AppStatusFilter getStatus() {
        return status;
    }

    public void setStatus(AppStatusFilter status) {
        this.status = status;
    }

    public LongFilter getServerId() {
        return serverId;
    }

    public void setServerId(LongFilter serverId) {
        this.serverId = serverId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AppCriteria that = (AppCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(serviceFlag, that.serviceFlag) &&
            Objects.equals(status, that.status) &&
            Objects.equals(serverId, that.serverId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        serviceFlag,
        status,
        serverId
        );
    }

    @Override
    public String toString() {
        return "AppCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (serviceFlag != null ? "serviceFlag=" + serviceFlag + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (serverId != null ? "serverId=" + serverId + ", " : "") +
            "}";
    }

}
