package com.olebokolo.wordstack.core.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class BaseStackEvent {
    protected String message;
}
