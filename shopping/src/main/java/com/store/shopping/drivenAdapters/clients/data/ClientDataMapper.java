package com.store.shopping.drivenAdapters.clients.data;

import com.store.shopping.entities.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface ClientDataMapper {
    Client toModel(ClientData clientData);
    ClientData toRepository(Client client);
}
