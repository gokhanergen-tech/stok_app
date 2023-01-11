package com.stok.stokapp.Usecases.Common.Mappers.MapperInterfaces;

import java.util.List;
import java.util.Optional;

public interface IMapper<S,R> {
  S mapToDTO(R object);
  R mapToNotDTO(S objectDTO);
  List<S> mapToDTOList(List<R> objectList);
  List<R> mapToNotDTOList(List<S> objectDTOList);

  default boolean isObjectNull(Object object){
     return object==null;
  }
}
