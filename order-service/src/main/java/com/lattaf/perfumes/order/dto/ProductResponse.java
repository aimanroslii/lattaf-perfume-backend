package com.lattaf.perfumes.order.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(    String id,
                                  String name,
                                  String description,
                                  List<String> images,
                                  BigDecimal price) {

}
