package com.stok.stokapp.Usecases.Common.Mappers.MapperImplements;

import com.stok.stokapp.Usecases.Common.DTO.UserDTO;
import com.stok.stokapp.Usecases.Common.Entities.Product.Stock;
import com.stok.stokapp.Usecases.Common.Entities.User.User;
import com.stok.stokapp.Usecases.Common.Mappers.MapperInterfaces.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserDTOImplements implements IMapper<UserDTO, User> {

    @Autowired
    private StockDTOImplements stockDTOImplements;

    @Override
    public UserDTO mapToDTO(User object) {
        if(isObjectNull(object))
            return null;
        return new UserDTO(object.getName(), object.getSurname(),object.getUsername(),object.getEmail(),object.getCity().getCity(),stockDTOImplements.mapToDTOList(List.copyOf(object.getStocks())));
    }

    @Override
    public User mapToNotDTO(UserDTO objectDTO) {
        if(isObjectNull(objectDTO))
            return null;
        List<Stock> stockList=stockDTOImplements.mapToNotDTOList(objectDTO.getStocks());

        return new User(null,objectDTO.getName(),objectDTO.getSurname(),objectDTO.getUsername(),objectDTO.getEmail(),objectDTO.getCity(),null,Set.copyOf(stockList),null);
    }

    @Override
    public List<UserDTO> mapToDTOList(List<User> objectList) {
        if(isObjectNull(objectList))
            return null;
        return objectList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<User> mapToNotDTOList(List<UserDTO> objectDTOList) {
        if(isObjectNull(objectDTOList))
            return null;
        return objectDTOList.stream().map(this::mapToNotDTO).collect(Collectors.toList());
    }
}
