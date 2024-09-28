package com.store.shopping.drivenAdapters.salesOrder.data;

import com.store.shopping.entities.Sales;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SalesDataMapper {
    Sales toModel(SalesData salesData);
    SalesData toRepository(Sales sales);
}
