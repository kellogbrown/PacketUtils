package com.piggyplugins.profiles.jagex.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SavedJagLoginToken {
    private String name;
    private JagLoginToken token;
}
