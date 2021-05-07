package com.ccm.user.user.domain.interfaces;

public interface SimpleConverter<fromType, toType> {

    public toType convert(fromType from);
}
