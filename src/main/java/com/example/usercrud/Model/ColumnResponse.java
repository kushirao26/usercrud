package com.example.usercrud.Model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ColumnResponse {
private String columnName;
private String PrimaryDataType;
private List<String> allDataTypes;
}
