package com.example.petproject.dto.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RequestInfo {
//    String userId = .getUserId();
    Integer page;
    Integer size;
    @Builder.Default
    List<SortInfo> sortInfo = new ArrayList<>();
}
