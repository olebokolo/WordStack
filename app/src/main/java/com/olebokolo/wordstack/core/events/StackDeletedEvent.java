package com.olebokolo.wordstack.core.events;

import com.olebokolo.wordstack.core.model.Stack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(suppressConstructorProperties = true)
public class StackDeletedEvent extends BaseStackEvent {
    private Stack stack;
}
