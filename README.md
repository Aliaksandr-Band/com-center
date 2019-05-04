# Statemachine builder (under development)
TODOs:
1. Убрать зависимости от Google Guava
2. Перевести Javadoc
3. Дать общее описание библиотеки (отличия от Spring Statemachine)
4. Подготовить примеры использования

# Description (draft, not completed)
This tiny library provides a state machine builder. Word "builder" implies that it's possible to build state machine with any number of states and transitions between them. Concept of state machine perfectly suites for situations when there is a need to describe or implement complex behaviour of a subject when this behaviour depends on the subject's states.

Well known approach to use state machine in programming is defined by pattern State. (see GoF). This approach implies implementation of classes for each identified states and methods for each supported signals (or events).

State machine builder is alternative way for implementation such thing.
See code examle which builds simple state machine with three states and two events:

    ...
    builder.defineState(«Ready»).asInitial();
    builder.defineState(«Processing»);
    builder.defineState(«Stopped»).asFinal();
    builder.defineExternalTransitionFor(«Ready»).to(«Stopped»).by(«Process»);
    builder.defineExternalTransitionFor(«Processing»).to(«Stopped»).by(«Stop»);
    StateMachine sm = builder.build();
    ...

As you can see there is no need to implement a number of state classes and a set of onEvent methods.

The idea to write this library was enspired by Spring State Machine project which I've successfully used in real project as a part of backend subsystem. But at the same time I needed similar but simplier solution for frontend part which is written on GWT. So that's why I wrote this library.
