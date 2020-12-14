package com.lx.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OneChapter {

    private String id;
    private String title;
    private List<TwoChapter> children = new ArrayList<>();

}
